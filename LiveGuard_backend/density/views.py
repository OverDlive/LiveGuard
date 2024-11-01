from rest_framework import generics
from django.contrib.auth.models import User
from .serializers import TimelineSerializer

class TimelineView(generics.RetrieveAPIView):
    queryset = User.objects.all()
    serializer_class = TimelineSerializer
    lookup_field = 'username'
