from rest_framework import generics
from django.contrib.auth.models import User
from .serializers import TimelineSerializer

class TimelineView(generics.RetrieveAPIView):
    queryset = User.objects.all()
    serializer_class = TimelineSerializer
    lookup_field = 'username'  # 또는 'id' 등을 사용할 수 있습니다.
