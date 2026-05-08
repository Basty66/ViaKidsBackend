from django.contrib import admin
from .models import User, Student, Bus, Route, AttendanceRecord, Notification, Incident

admin.site.register(User)
admin.site.register(Student)
admin.site.register(Bus)
admin.site.register(Route)
admin.site.register(AttendanceRecord)
admin.site.register(Notification)
admin.site.register(Incident)
