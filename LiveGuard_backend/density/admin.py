from django.db import models
from django.contrib.auth.models import User

class DensityData(models.Model):
    user = models.ForeignKey(User, related_name='density_data', on_delete=models.CASCADE)
    location = models.CharField(max_length=255)
    density = models.FloatField()
    timestamp = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"{self.user.username} - {self.location} - {self.density}"