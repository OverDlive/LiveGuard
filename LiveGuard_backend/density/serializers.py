from rest_framework import serializers
from .models import Activity
from django.contrib.auth.models import User

class ActivitySerializer(serializers.ModelSerializer):
    class Meta:
        model = Activity
        fields = ['title', 'timestamp']

class TimelineSerializer(serializers.ModelSerializer):
    activities = ActivitySerializer(many=True, read_only=True)
    user_id = serializers.CharField(source='id')

    class Meta:
        model = User
        fields = ['user_id', 'activities']
