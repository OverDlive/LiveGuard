# 군중 밀집도 데이터

from django.db import models

class Activity(models.Model):
    title = models.CharField(max_length=255)
    timestamp = models.DateTimeField(auto_now_add=True)
    user = models.ForeignKey('auth.User', related_name='activities', on_delete=models.CASCADE)

    def __str__(self):
        return self.title
