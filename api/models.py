from django.contrib.auth.models import AbstractUser
from django.db import models

class User(AbstractUser):
    username = None
    email = models.EmailField(unique=True)
    first_name = models.CharField(max_length=255, blank=True)
    last_name = models.CharField(max_length=255, blank=True)
    role = models.CharField(max_length=50, choices=[
        ('ADMIN', 'Admin'),
        ('DRIVER', 'Driver'),
        ('PARENT', 'Parent'),
    ], default='PARENT')
    active = models.BooleanField(default=True)
    created_at = models.DateTimeField(auto_now_add=True)

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []

    def __str__(self):
        return self.email

class Bus(models.Model):
    bus_number = models.CharField(max_length=50, unique=True)
    capacity = models.IntegerField()
    driver = models.ForeignKey(User, on_delete=models.SET_NULL, null=True, related_name='buses')
    status = models.CharField(max_length=50, default='ACTIVE')

    def __str__(self):
        return self.bus_number

class Route(models.Model):
    name = models.CharField(max_length=255)
    description = models.TextField(blank=True)
    start_point = models.CharField(max_length=255, blank=True)
    end_point = models.CharField(max_length=255, blank=True)
    bus = models.ForeignKey(Bus, on_delete=models.SET_NULL, null=True, related_name='routes')

    def __str__(self):
        return self.name

class Student(models.Model):
    first_name = models.CharField(max_length=255)
    last_name = models.CharField(max_length=255)
    date_of_birth = models.DateField(null=True, blank=True)
    grade = models.CharField(max_length=50, blank=True)
    parent = models.ForeignKey(User, on_delete=models.CASCADE, null=True, related_name='students')
    bus = models.ForeignKey(Bus, on_delete=models.SET_NULL, null=True, related_name='students')
    active = models.BooleanField(default=True)

    def __str__(self):
        return f"{self.first_name} {self.last_name}"

class AttendanceRecord(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE, related_name='attendance_records')
    date = models.DateField()
    status = models.CharField(max_length=50, choices=[
        ('PRESENT', 'Present'),
        ('ABSENT', 'Absent'),
        ('LATE', 'Late'),
        ('EXCUSED', 'Excused'),
    ])
    check_in_time = models.TimeField(null=True, blank=True)
    check_out_time = models.TimeField(null=True, blank=True)

    class Meta:
        unique_together = ('student', 'date')

    def __str__(self):
        return f"{self.student} - {self.date} - {self.status}"

class Notification(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE, related_name='notifications')
    title = models.CharField(max_length=255)
    message = models.TextField()
    type = models.CharField(max_length=50, blank=True)
    is_read = models.BooleanField(default=False)
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.title

class Incident(models.Model):
    bus = models.ForeignKey(Bus, on_delete=models.CASCADE, related_name='incidents')
    reported_by = models.ForeignKey(User, on_delete=models.CASCADE, related_name='reported_incidents')
    description = models.TextField()
    severity = models.CharField(max_length=50, choices=[
        ('LOW', 'Low'),
        ('MEDIUM', 'Medium'),
        ('HIGH', 'High'),
        ('CRITICAL', 'Critical'),
    ])
    status = models.CharField(max_length=50, choices=[
        ('OPEN', 'Open'),
        ('IN_PROGRESS', 'In Progress'),
        ('RESOLVED', 'Resolved'),
        ('CLOSED', 'Closed'),
    ], default='OPEN')
    created_at = models.DateTimeField(auto_now_add=True)
    resolved_at = models.DateTimeField(null=True, blank=True)

    def __str__(self):
        return f"Incident #{self.id} - {self.severity}"
