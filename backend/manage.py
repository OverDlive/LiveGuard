#!/usr/bin/env python
import os
import sys

def main():
    """Django의 명령 줄 유틸리티를 실행합니다."""
    os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'main_backend.settings')
    try:
        from django.core.management import execute_from_command_line
    except ImportError as exc:
        raise ImportError(
            "Django를 가져올 수 없습니다. 환경에 설치되어 있는지 확인하세요."
        ) from exc
    execute_from_command_line(sys.argv)

if __name__ == '__main__':
    main()