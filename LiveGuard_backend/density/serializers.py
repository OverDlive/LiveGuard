from rest_framework import serializers
from .models import Density

class DensitySerializer(serializers.ModelSerializer):
    class Meta:
        model = Density
        fields = '__all__'