from rest_framework import generics
from django.contrib.auth.models import User
from .models import Timeline
from .serializers import TimelineSerializer
from rest_framework.permissions import AllowAny
from rest_framework.exceptions import NotFound

class UserTimelineView(generics.ListAPIView):
    serializer_class = TimelineSerializer
    permission_classes = [AllowAny]

    def get_queryset(self):
        username = self.kwargs.get('username')
        try:
            user = User.objects.get(username=username)
        except User.DoesNotExist:
            raise NotFound(detail="User not found.")

        return Timeline.objects.filter(user=user)