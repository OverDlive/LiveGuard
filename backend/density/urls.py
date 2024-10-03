from django.urls import path
from .views import DensityCreateView

urlpatterns = [
    path('density/', DensityCreateView.as_view(), name='density_create'),
]