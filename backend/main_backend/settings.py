# 설정 파일

INSTALLED_APPS = [
    # ...
    'rest_framework',
    'density',
    # 다른 앱들...
]

REST_FRAMEWORK = {
    'DEFAULT_AUTHENTICATION_CLASSES': [
        'rest_framework.authentication.TokenAuthentication',
        # 필요에 따라 다른 인증 클래스 추가
    ],
    'DEFAULT_PERMISSION_CLASSES': [
        'rest_framework.permissions.IsAuthenticated',
    ],
}