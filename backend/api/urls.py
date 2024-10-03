# 각 앱의 api 라우팅
from django.urls import path, include

urlpatterns = [
    path('api/', include('density.urls')),
    # 다른 앱의 URL도 필요 시 포함
]