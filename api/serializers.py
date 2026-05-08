from rest_framework import serializers
from django.contrib.auth import get_user_model
from .models import Student, Bus, Route, AttendanceRecord, Notification, Incident

User = get_user_model()

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'email', 'first_name', 'last_name', 'role', 'active', 'created_at']
        read_only_fields = ['created_at']

class LoginSerializer(serializers.Serializer):
    email = serializers.EmailField()
    password = serializers.CharField()

class StudentSerializer(serializers.ModelSerializer):
    parent_email = serializers.EmailField(source='parent.email', read_only=True)
    bus_number = serializers.CharField(source='bus.bus_number', read_only=True)

    class Meta:
        model = Student
        fields = ['id', 'first_name', 'last_name', 'date_of_birth', 'grade',
                  'parent', 'parent_email', 'bus', 'bus_number', 'active']

class BusSerializer(serializers.ModelSerializer):
    driver_name = serializers.SerializerMethodField()

    class Meta:
        model = Bus
        fields = ['id', 'bus_number', 'capacity', 'driver', 'driver_name', 'status']

    def get_driver_name(self, obj):
        if obj.driver:
            return f"{obj.driver.first_name} {obj.driver.last_name}"
        return None

class RouteSerializer(serializers.ModelSerializer):
    bus_number = serializers.CharField(source='bus.bus_number', read_only=True)

    class Meta:
        model = Route
        fields = ['id', 'name', 'description', 'start_point', 'end_point', 'bus', 'bus_number']

class AttendanceRecordSerializer(serializers.ModelSerializer):
    student_name = serializers.SerializerMethodField()

    class Meta:
        model = AttendanceRecord
        fields = ['id', 'student', 'student_name', 'date', 'status', 'check_in_time', 'check_out_time']

    def get_student_name(self, obj):
        return f"{obj.student.first_name} {obj.student.last_name}"

class NotificationSerializer(serializers.ModelSerializer):
    class Meta:
        model = Notification
        fields = ['id', 'title', 'message', 'type', 'is_read', 'created_at']
        read_only_fields = ['created_at']

class NotificationReadSerializer(serializers.Serializer):
    is_read = serializers.BooleanField()

class IncidentSerializer(serializers.ModelSerializer):
    bus_number = serializers.CharField(source='bus.bus_number', read_only=True)
    reported_by_name = serializers.SerializerMethodField()

    class Meta:
        model = Incident
        fields = ['id', 'bus', 'bus_number', 'reported_by', 'reported_by_name',
                  'description', 'severity', 'status', 'created_at', 'resolved_at']
        read_only_fields = ['created_at', 'resolved_at']

    def get_reported_by_name(self, obj):
        return f"{obj.reported_by.first_name} {obj.reported_by.last_name}"
