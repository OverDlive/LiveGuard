# YOLO 실시간 분석 웹 스트리밍

이 프로젝트는 YOLO 모델을 사용해 실시간으로 유튜브 또는 IP 카메라/스트림 영상을 분석하고, Flask 웹 서버를 통해 분석 결과를 웹 브라우저에 스트리밍하는 예제입니다.

## 파일 구조

project/ 
├── app.py # Flask 및 YOLO 분석 코드 
├── requirements.txt # 필요한 파이썬 패키지 목록 
├── README.md # 프로젝트 설명 및 실행 방법 
└── templates/ 
└── index.html # 웹 스트리밍용 HTML 템플릿



## 환경 설정

1. **Anaconda 가상환경 생성 및 활성화**

   ```bash
   conda create -n density_env python=3.9
   conda activate density_env

2. 필수 패키지 설치

가상환경 내에서 프로젝트 폴더에 있는 requirements.txt 파일을 이용해 패키지를 설치합니다.

pip install -r requirements.txt

3. 모델 파일 준비

YOLO 모델 파일 (yolov8n.pt)을 프로젝트 루트에 위치시키거나, 코드 내 MODEL_PATH 변수를 해당 파일 경로로 수정합니다.

실행 방법

1. 프로젝트 루트 디렉토리로 이동합니다.

cd project

2. Flask 서버 실행

python app.py

3. 웹 브라우저에서 http://localhost:5000 에 접속하면 실시간 분석 영상이 표시됩니다.

참고 사항
VIDEO_SOURCE 변수에 YouTube URL 또는 IP 카메라/스트림 URL을 입력할 수 있습니다.

코드에서 detection_scale 및 confidence_threshold 값을 조절하여 성능을 최적화할 수 있습니다.

GitHub에 올릴 경우, 모델 파일은 용량이 크므로 별도 업로드하거나 다운로드 링크를 제공하는 것이 좋습니다.


---

## 3. GitHub에 올리기

1. GitHub에서 새 저장소를 생성합니다.
2. 위의 파일들을 로컬 프로젝트 폴더에 저장한 후, Git 명령어를 사용하여 커밋하고 푸시합니다.

```bash
git init
git add .
git commit -m "Initial commit: YOLO 실시간 분석 웹 스트리밍 프로젝트"
git branch -M main
git remote add origin https://github.com/your_username/your_repository.git
git push -u origin main


이제 GitHub에 업로드된 프로젝트를 통해 다른 사람도 환경 설정 및 실행 방법을 참고하여 코드를 실행할 수 있습니다.


