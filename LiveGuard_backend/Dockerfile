FROM python:3.11-slim

RUN groupadd -r appuser && useradd -r -g appuser appuser

WORKDIR /app

COPY requirements.txt /app/
RUN pip install --upgrade pip
RUN pip install --no-cache-dir -r requirements.txt

COPY . /app/
RUN chown -R appuser:appuser /app

USER appuser

ENV DJANGO_SETTINGS_MODULE=LiveGuard_backend.settings
ENV PYTHONUNBUFFERED=1

RUN python manage.py collectstatic --noinput

EXPOSE 8000

CMD ["gunicorn", "--bind", "0.0.0.0:8000", "LiveGuard_backend.wsgi:application"]
