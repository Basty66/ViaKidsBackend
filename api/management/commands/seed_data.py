from django.core.management.base import BaseCommand
from django.contrib.auth import get_user_model

User = get_user_model()

class Command(BaseCommand):
    help = 'Seed default admin user and test data'

    def handle(self, *args, **options):
        if not User.objects.filter(email='admin@viakids.cl').exists():
            User.objects.create_superuser(
                email='admin@viakids.cl',
                password='admin123',
                first_name='Admin',
                last_name='ViaKids',
                role='ADMIN'
            )
            self.stdout.write(self.style.SUCCESS('Admin user created: admin@viakids.cl / admin123'))
        else:
            self.stdout.write('Admin user already exists')

        if not User.objects.filter(email='driver@viakids.cl').exists():
            User.objects.create_user(
                email='driver@viakids.cl',
                password='driver123',
                first_name='Carlos',
                last_name='Perez',
                role='DRIVER'
            )
            self.stdout.write(self.style.SUCCESS('Driver user created: driver@viakids.cl / driver123'))

        if not User.objects.filter(email='apoderado@viakids.cl').exists():
            User.objects.create_user(
                email='apoderado@viakids.cl',
                password='apoderado123',
                first_name='Maria',
                last_name='Lopez',
                role='PARENT'
            )
            self.stdout.write(self.style.SUCCESS('Parent user created: apoderado@viakids.cl / apoderado123'))
