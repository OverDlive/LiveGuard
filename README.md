<img src="https://capsule-render.vercel.app/api?type=waving&color=BDBDC8&height=150&section=header&text=LiveGuard&fontSize=50" />

GPS 기반 군중 제어 및 재난 대응 서비스

## 소개

LiveGuard는 GPS와 TensorFlow를 활용하여 **군중 밀집도를 실시간으로 파악**하고 **재난 상황에서 안전한 대피 경로를 제공**하는 서비스입니다. 이 프로젝트는 대규모 행사나 재난 발생 시 군중의 안전을 보장하고 신속한 대응을 지원하기 위해 개발되었습니다.

## 주요 기능

- **실시간 위치 추적 및 군중 밀집도 분석**
  - GPS 데이터를 수집하여 군중의 위치와 이동 패턴을 실시간으로 분석합니다.
  - TensorFlow 기반 머신러닝 모델을 통해 군중 밀집도를 예측하고 시각화합니다.

- **재난 발생 시 경고 및 대피 경로 제공**
  - 재난 상황 발생 시 사용자에게 즉각적인 경고 알림을 전송합니다.
  - 사용자의 현재 위치를 기반으로 가장 안전한 대피 경로를 제공합니다.

- **사용자 인터페이스(UI/UX)**
  - 직관적이고 간결한 모바일 및 웹 인터페이스를 제공합니다.
  - 실시간 군중 밀집도 지도, 위험 지역 표시, 대피 경로 안내 등을 지원합니다.

- **실시간 데이터 처리 및 서버 아키텍처**
  - 확장 가능한 클라우드 기반 서버 인프라를 구축하였습니다.
  - WebSocket 및 MQTT 프로토콜을 활용하여 안정적인 실시간 통신을 제공합니다.

## 프로젝트 구조

- **프론트엔드**
  - **모바일 애플리케이션**: Android Studio 사용

- **백엔드**
  - **서버 프레임워크**: Django 사용
  - **통신 방식**: RESTful API 및 WebSocket

- **머신러닝**
  - **모델 개발**: TensorFlow 활용
  - **기능**: 군중 밀집도 예측 및 실시간 데이터 분석

- **데이터베이스**
  - **주 데이터베이스**: PostgreSQL
  - **캐시 및 세션 관리**: Redis

- **DevOps**
  - **컨테이너화**: Docker 사용
  - **CI/CD**: Jenkins 및 GitLab CI/CD
  - **클라우드 인프라**: AWS 활용

## 파일 구조
```
├── LiveGuard_backend
│   ├── LiveGuard_backend
│   │   ├── __init__.py
│   │   ├── __pycache__
│   │   │   ├── __init__.cpython-312.pyc
│   │   │   ├── settings.cpython-312.pyc
│   │   │   ├── urls.cpython-312.pyc
│   │   │   └── wsgi.cpython-312.pyc
│   │   ├── asgi.py
│   │   ├── settings.py
│   │   ├── urls.py
│   │   └── wsgi.py
│   ├── api
│   │   ├── __init__.py
│   │   ├── urls.py
│   │   └── views.py
│   ├── db.sqlite3
│   ├── density
│   │   ├── __init__.py
│   │   ├── __pycache__
│   │   │   ├── __init__.cpython-312.pyc
│   │   │   └── models.cpython-312.pyc
│   │   ├── models.py
│   │   ├── serializers.py
│   │   ├── tasks.py
│   │   ├── urls.py
│   │   └── views.py
│   ├── disaster_response
│   │   ├── __init__.py
│   │   ├── serializers.py
│   │   ├── urls.py
│   │   └── views.py
│   ├── manage.py
│   ├── notifications
│   │   ├── __init__.py
│   │   ├── models.py
│   │   ├── push_service.py
│   │   ├── urls.py
│   │   └── views.py
│   ├── profile
│   │   ├── __init__.py
│   │   ├── models.py
│   │   ├── serializers.py
│   │   ├── urls.py
│   │   └── views.py
│   ├── reports
│   │   ├── __init__.py
│   │   ├── models.py
│   │   ├── serializers.py
│   │   ├── urls.py
│   │   └── views.py
│   ├── requirements.txt
│   ├── settings
│   │   ├── __init__.py
│   │   ├── serializers.py
│   │   ├── urls.py
│   │   └── views.py
│   ├── splash
│   │   ├── __init__.py
│   │   ├── serializers.py
│   │   ├── urls.py
│   │   └── views.py
│   ├── static
│   └── weather_news
│       ├── __init__.py
│       ├── serializers.py
│       ├── urls.py
│       └── views.py
└── README.md
```

## 설치 및 실행 방법

### 전제 조건

- **Python** 3.8 이상
- **Docker** 및 **Docker Compose**
- **AWS 계정** (또는 다른 클라우드 서비스)

### 백엔드 설정

1. **저장소 클론**

   ```bash
   git clone https://github.com/OverDlive/LiveGuard.git
   cd LiveGuard/LiveGuard_backend.git
   ```

2. **가상환경 생성 및 활성화**

   ```bash
   python -m venv venv
   source venv/bin/activate  # Windows의 경우 'venv\Scripts\activate'
   ```

3. **종속성 설치**

   ```bash
   pip install -r requirements.txt
   ```

4. **환경 변수 설정**

   - `.env` 파일을 생성하고 필요한 환경 변수를 설정합니다.

5. **데이터베이스 마이그레이션**

   ```bash
   alembic upgrade head
   ```

6. **서버 실행**

   ```bash
   python3 manage.py runserver
   ```

### 프론트엔드 설정

1. **저장소 클론** (이미 클론한 경우 생략)

   ```bash
   cd ../frontend
   ```

2. **종속성 설치**

   ```bash
   npm install
   ```

3. **환경 변수 설정**

   - `.env` 파일을 생성하고 필요한 환경 변수를 설정합니다.

4. **애플리케이션 실행**

   ```bash
   npm start
   ```

### Docker를 이용한 전체 서비스 실행

1. **루트 디렉토리에서 Docker Compose 실행**

   ```bash
   docker-compose up --build
   ```

## 사용 방법

1. **앱 접속**

   - 모바일 앱 또는 웹 애플리케이션에 접속합니다.

2. **초기 설정**

   - 위치 정보 제공 및 알림 수신에 동의합니다.

3. **홈 화면**

   - 현재 위치와 주변의 군중 밀집도를 확인합니다.

4. **경로 안내**

   - 목적지를 설정하면 안전한 경로 안내를 받습니다.

5. **재난 알림 수신**

   - 재난 상황 발생 시 실시간 경고 알림을 수신하고 대피 경로를 제공합니다.

6. **제보 및 공유**

   - 위험 요소나 재난 상황을 제보하여 다른 사용자들과 정보를 공유합니다.

## 기여 방법

1. **저장소 포크**

   - 이 저장소를 포크합니다.

2. **브랜치 생성**

   - 새로운 기능이나 버그 수정을 위한 브랜치를 생성합니다.

3. **변경 사항 커밋**

   - 기능을 추가하거나 버그를 수정한 후 커밋합니다.

4. **푸시 및 Pull Request 생성**

   - 변경 사항을 푸시하고 Pull Request를 생성합니다.

5. **리뷰 및 병합**

   - 리뷰 후 프로젝트에 병합됩니다.

## 라이선스

이 프로젝트는 **MIT 라이선스** 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 팀 구성

- **프로젝트 매니저 및 백엔드 개발자**
  - **이름**: 한동혁
  - **역할**: 프로젝트 관리, 백엔드 개발, API 개발

- **백엔드 개발자 및 서버 아키텍트**
  - **이름**: 진건호
  - **역할**: 백엔드 개발, 서버 구축, API 개발

- **머신러닝 엔지니어 및 데이터 사이언티스트**
  - **이름**: 김지훈
  - **역할**: 데이터 수집 및 분석, 머신러닝 모델 개발

- **QA 엔지니어 및 DevOps 전문가**
  - **이름**: 박민호
  - **역할**: 품질 보증, CI/CD 파이프라인 구축, 시스템 모니터링
 
- **프론트 엔드 및 UI/UX 개발**
  - **이름**: 김동건
  - **역할**: 프론트 엔드 개발, UI/UX 개발

- **프론트 엔드 개발**
   - **이름**: 김서범
   - **역할**: 안드로이드 스튜디오 프론트엔드 앱 개발

## 연락처

- **이메일**: donghyeok7312@naver.com
- **이슈 트래커**: [GitHub Issues](https://github.com/yourusername/LiveGuard/issues)

## 참고 자료

- **TensorFlow 공식 문서**: [https://www.tensorflow.org/](https://www.tensorflow.org/)
- **FastAPI 공식 문서**: [https://fastapi.tiangolo.com/](https://fastapi.tiangolo.com/)
- **Docker 공식 문서**: [https://docs.docker.com/](https://docs.docker.com/)

---

프로젝트에 대한 자세한 내용은 위의 정보를 참고하시고, 추가적인 질문이나 제안 사항이 있으시면 언제든지 **연락처**로 문의해주시기 바랍니다.
