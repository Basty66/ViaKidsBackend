from django.urls import path, include
from rest_framework.routers import DefaultRouter
from . import views

router = DefaultRouter()
router.register(r'students', views.StudentViewSet)
router.register(r'buses', views.BusViewSet)
router.register(r'routes', views.RouteViewSet)
router.register(r'attendance', views.AttendanceViewSet)
router.register(r'notifications', views.NotificationViewSet, basename='notification')
router.register(r'incidents', views.IncidentViewSet)

urlpatterns = [
    path('auth/login/', views.login, name='login'),
    path('auth/register/', views.register, name='register'),
    path('reports/student/<int:student_id>/', views.student_report, name='student-report'),
    path('reports/bus/<int:bus_id>/', views.bus_report, name='bus-report'),
    path('', include(router.urls)),
]
