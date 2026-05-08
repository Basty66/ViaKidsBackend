from django.contrib.auth import get_user_model
from django.db.models import Q
from rest_framework import viewsets, status, generics, permissions
from rest_framework.decorators import api_view, permission_classes, action
from rest_framework.response import Response
from rest_framework.permissions import AllowAny, IsAuthenticated
from rest_framework_simplejwt.tokens import RefreshToken
from .models import Student, Bus, Route, AttendanceRecord, Notification, Incident
from .serializers import (
    UserSerializer, LoginSerializer, StudentSerializer, BusSerializer,
    RouteSerializer, AttendanceRecordSerializer, NotificationSerializer,
    NotificationReadSerializer, IncidentSerializer
)

User = get_user_model()

@api_view(['POST'])
@permission_classes([AllowAny])
def login(request):
    serializer = LoginSerializer(data=request.data)
    if not serializer.is_valid():
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    email = serializer.validated_data['email']
    password = serializer.validated_data['password']

    try:
        user = User.objects.get(email=email)
    except User.DoesNotExist:
        return Response({'error': 'Invalid credentials'}, status=status.HTTP_401_UNAUTHORIZED)

    if not user.check_password(password):
        return Response({'error': 'Invalid credentials'}, status=status.HTTP_401_UNAUTHORIZED)

    if not user.active:
        return Response({'error': 'User is deactivated'}, status=status.HTTP_403_FORBIDDEN)

    refresh = RefreshToken.for_user(user)
    return Response({
        'token': str(refresh.access_token),
        'refreshToken': str(refresh),
        'email': user.email,
        'firstName': user.first_name,
        'lastName': user.last_name,
        'role': user.role,
        'id': user.id,
    })

@api_view(['POST'])
@permission_classes([AllowAny])
def register(request):
    email = request.data.get('email')
    password = request.data.get('password')
    first_name = request.data.get('firstName', '')
    last_name = request.data.get('lastName', '')
    role = request.data.get('role', 'PARENT')

    if not email or not password:
        return Response({'error': 'Email and password required'}, status=status.HTTP_400_BAD_REQUEST)

    if User.objects.filter(email=email).exists():
        return Response({'error': 'Email already registered'}, status=status.HTTP_400_BAD_REQUEST)

    user = User.objects.create_user(
        email=email,
        password=password,
        first_name=first_name,
        last_name=last_name,
        role=role
    )

    refresh = RefreshToken.for_user(user)
    return Response({
        'token': str(refresh.access_token),
        'refreshToken': str(refresh),
        'email': user.email,
        'firstName': user.first_name,
        'lastName': user.last_name,
        'role': user.role,
        'id': user.id,
    }, status=status.HTTP_201_CREATED)

# ─── Students ───────────────────────────────────────────────────────────────

class StudentViewSet(viewsets.ModelViewSet):
    queryset = Student.objects.all()
    serializer_class = StudentSerializer

    def get_queryset(self):
        qs = Student.objects.all()
        bus_id = self.request.query_params.get('bus')
        if bus_id:
            qs = qs.filter(bus_id=bus_id)
        if self.request.user.role == 'PARENT':
            qs = qs.filter(parent=self.request.user)
        return qs

# ─── Buses ──────────────────────────────────────────────────────────────────

class BusViewSet(viewsets.ModelViewSet):
    queryset = Bus.objects.all()
    serializer_class = BusSerializer

# ─── Routes ─────────────────────────────────────────────────────────────────

class RouteViewSet(viewsets.ModelViewSet):
    queryset = Route.objects.all()
    serializer_class = RouteSerializer

# ─── Attendance ─────────────────────────────────────────────────────────────

class AttendanceViewSet(viewsets.ModelViewSet):
    queryset = AttendanceRecord.objects.all()
    serializer_class = AttendanceRecordSerializer

    def get_queryset(self):
        qs = AttendanceRecord.objects.all()
        student_id = self.request.query_params.get('student')
        date = self.request.query_params.get('date')
        if student_id:
            qs = qs.filter(student_id=student_id)
        if date:
            qs = qs.filter(date=date)
        return qs

# ─── Notifications ─────────────────────────────────────────────────────────

class NotificationViewSet(viewsets.ModelViewSet):
    serializer_class = NotificationSerializer

    def get_queryset(self):
        return Notification.objects.filter(user=self.request.user).order_by('-created_at')

    def perform_create(self, serializer):
        serializer.save(user=self.request.user)

    @action(detail=True, methods=['put'])
    def read(self, request, pk=None):
        notification = self.get_object()
        serializer = NotificationReadSerializer(data=request.data)
        if serializer.is_valid():
            notification.is_read = serializer.validated_data['is_read']
            notification.save()
            return Response(NotificationSerializer(notification).data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @action(detail=False, methods=['get'])
    def unread_count(self, request):
        count = Notification.objects.filter(user=request.user, is_read=False).count()
        return Response({'count': count})

# ─── Incidents ──────────────────────────────────────────────────────────────

class IncidentViewSet(viewsets.ModelViewSet):
    queryset = Incident.objects.all()
    serializer_class = IncidentSerializer

# ─── Reports ────────────────────────────────────────────────────────────────

@api_view(['GET'])
def student_report(request, student_id):
    try:
        student = Student.objects.get(id=student_id)
    except Student.DoesNotExist:
        return Response({'error': 'Student not found'}, status=404)

    attendance = AttendanceRecord.objects.filter(student=student)
    return Response({
        'student': StudentSerializer(student).data,
        'attendance': AttendanceRecordSerializer(attendance, many=True).data,
        'total_records': attendance.count(),
        'present': attendance.filter(status='PRESENT').count(),
        'absent': attendance.filter(status='ABSENT').count(),
    })

@api_view(['GET'])
def bus_report(request, bus_id):
    try:
        bus = Bus.objects.get(id=bus_id)
    except Bus.DoesNotExist:
        return Response({'error': 'Bus not found'}, status=404)

    students = Student.objects.filter(bus=bus)
    incidents = Incident.objects.filter(bus=bus)
    return Response({
        'bus': BusSerializer(bus).data,
        'students': StudentSerializer(students, many=True).data,
        'student_count': students.count(),
        'incidents': IncidentSerializer(incidents, many=True).data,
        'incident_count': incidents.count(),
    })
