from django.db import models
from django.contrib.auth.models import User

class Timeline(models.Model):
    user = models.ForeignKey(User, related_name='timelines', on_delete=models.CASCADE)
    content = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"{self.user.username} - {self.created_at}"