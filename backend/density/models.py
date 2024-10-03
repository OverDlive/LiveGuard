# 군중 밀집도 데이터

from django.db import models

class Density(models.Model):
    location = models.CharField(max_length=255)
    timestamp = models.DateTimeField(auto_now_add=True)
    density = models.FloatFiled()
    # 추가 필드 필요 시 여기에 추가