import os
from pathlib import Path
from datetime import timedelta

BASE_DIR = Path(__file__).resolve().parent.parent

SECRET_KEY = os.environ.get('DJANGO_SECRET_KEY', 'django-insecure-change-me-in-production')

DEBUG = os.environ.get('DJANGO_DEBUG', 'False').lower() == 'true'

ALLOWED_HOSTS = os.environ.get('DJANGO_ALLOWED_HOSTS', 'localhost,127.0.0.1,viakids-backend.onrender.com').split(',')

INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'rest_framework',
    'rest_framework_simplejwt',
    'corsheaders',
    'api',
]

MIDDLEWARE = [
    'corsheaders.middleware.CorsMiddleware',
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

ROOT_URLCONF = 'viakids_backend.urls'

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [],
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

WSGI_APPLICATION = 'viakids_backend.wsgi.application'

# Database - handles both JDBC URLs (Spring Boot format) and standard Postgres URLs
DATABASE_URL = os.environ.get('DATABASE_URL', '')
if DATABASE_URL and DATABASE_URL.startswith('jdbc:'):
    jdbc_url = DATABASE_URL[5:]  # remove 'jdbc:'
    url_no_params = jdbc_url.split('?')[0]  # remove query params
    import re
    match = re.match(r'postgresql://(?:([^:]+):([^@]+)@)?(.+):(\d+)/(.+)', url_no_params)
    if match:
        db_user, db_pass, db_host, db_port, db_name = match.groups()
        DATABASES = {
            'default': {
                'ENGINE': 'django.db.backends.postgresql',
                'NAME': db_name,
                'USER': db_user or os.environ.get('DATABASE_USERNAME', 'postgres'),
                'PASSWORD': db_pass or os.environ.get('DATABASE_PASSWORD', 'postgres'),
                'HOST': db_host,
                'PORT': int(db_port),
                'OPTIONS': {'sslmode': 'require'},
            }
        }
    else:
        DATABASES = {
            'default': {
                'ENGINE': 'django.db.backends.postgresql',
                'NAME': os.environ.get('DATABASE_NAME', 'viakids'),
                'USER': os.environ.get('DATABASE_USERNAME', 'postgres'),
                'PASSWORD': os.environ.get('DATABASE_PASSWORD', 'postgres'),
                'HOST': os.environ.get('DATABASE_HOST', 'localhost'),
                'PORT': os.environ.get('DATABASE_PORT', '5432'),
            }
        }
elif DATABASE_URL and DATABASE_URL.startswith('postgresql://'):
    import re
    match = re.match(r'postgresql://([^:]+):([^@]+)@(.+):(\d+)/([^?]+)', DATABASE_URL.split('?')[0])
    if match:
        db_user, db_pass, db_host, db_port, db_name = match.groups()
        DATABASES = {
            'default': {
                'ENGINE': 'django.db.backends.postgresql',
                'NAME': db_name,
                'USER': db_user,
                'PASSWORD': db_pass,
                'HOST': db_host,
                'PORT': int(db_port),
                'OPTIONS': {'sslmode': 'require'},
            }
        }
else:
    DATABASES = {
        'default': {
            'ENGINE': 'django.db.backends.postgresql',
            'NAME': os.environ.get('DATABASE_NAME', 'viakids'),
            'USER': os.environ.get('DATABASE_USERNAME', 'postgres'),
            'PASSWORD': os.environ.get('DATABASE_PASSWORD', 'postgres'),
            'HOST': os.environ.get('DATABASE_HOST', 'localhost'),
            'PORT': os.environ.get('DATABASE_PORT', '5432'),
        }
    }

AUTH_USER_MODEL = 'api.User'

REST_FRAMEWORK = {
    'DEFAULT_AUTHENTICATION_CLASSES': (
        'rest_framework_simplejwt.authentication.JWTAuthentication',
    ),
    'DEFAULT_PERMISSION_CLASSES': (
        'rest_framework.permissions.IsAuthenticated',
    ),
    'DEFAULT_PAGINATION_CLASS': 'rest_framework.pagination.PageNumberPagination',
    'PAGE_SIZE': 20,
}

SIMPLE_JWT = {
    'ACCESS_TOKEN_LIFETIME': timedelta(hours=24),
    'REFRESH_TOKEN_LIFETIME': timedelta(days=7),
    'AUTH_HEADER_TYPES': ('Bearer',),
}

CORS_ALLOWED_ORIGINS = os.environ.get(
    'CORS_ORIGINS',
    'http://localhost:5173,https://via-kids-capstone.vercel.app'
).split(',')

CORS_ALLOW_CREDENTIALS = True

STATIC_URL = '/static/'
STATIC_ROOT = BASE_DIR / 'staticfiles'

DEFAULT_AUTO_FIELD = 'django.db.models.BigAutoField'
