from django.urls import path
from .views import UserTimelineView

urlpatterns = [
    path('timeline/<str:username>/', UserTimelineView.as_view(), name='user-timeline'),
]