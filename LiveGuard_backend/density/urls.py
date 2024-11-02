from django.urls import path
from .views import TimelineView

urlpatterns = [
    path('api/timeline/<str:username>/', TimelineView.as_view(), name='timeline'),
]