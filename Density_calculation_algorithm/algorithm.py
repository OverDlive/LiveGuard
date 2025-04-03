import cv2
import time
import os
from ultralytics import YOLO
from dataclasses import dataclass
import yt_dlp  # yt-dlp 직접 사용
from flask import Flask, Response, render_template

# OpenCV 멀티스레딩 비활성화
os.environ["OMP_NUM_THREADS"] = "1"
os.environ["OPENBLAS_NUM_THREADS"] = "1"


# YouTube 스트림 URL을 가져오는 함수
def get_youtube_stream_url(url):
    ydl_opts = {'format': 'best[ext=mp4]'}
    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        info_dict = ydl.extract_info(url, download=False)
        return info_dict.get("url")


@dataclass
class DetectionResult:
    boxes: list
    scores: list
    num_people: int
    density: float
    stage: str
    fps: float


class YOLOPeopleDetector:
    def __init__(self, model_path: str, confidence_threshold: float = 0.3, detection_scale: float = 1.0):
        self.model = YOLO(model_path)
        self.confidence_threshold = confidence_threshold
        self.detection_scale = detection_scale  # 업스케일 비율 (멀리 있는 작은 객체 인식 개선)

    def process_frame(self, frame) -> DetectionResult:
        start_time = time.time()
        # detection_scale > 1이면 프레임 업스케일
        if self.detection_scale != 1.0:
            scaled_frame = cv2.resize(
                frame,
                (int(frame.shape[1] * self.detection_scale), int(frame.shape[0] * self.detection_scale))
            )
        else:
            scaled_frame = frame

        results = self.model(scaled_frame, conf=self.confidence_threshold)

        if not results[0].boxes:
            return DetectionResult([], [], 0, 0.0, "SMOOTH", 1.0 / (time.time() - start_time))

        # 박스, 신뢰도, 클래스 정보 추출 (COCO 기준 person 클래스: 0)
        boxes = results[0].boxes.xyxy
        scores = results[0].boxes.conf
        classes = results[0].boxes.cls

        boxes, scores, num_people, density = self._postprocess_detections(
            boxes, scores, classes, frame.shape, self.detection_scale
        )
        stage = self._define_stage(density)
        fps = 1.0 / max(time.time() - start_time, 1e-6)
        return DetectionResult(boxes, scores, num_people, density, stage, fps)

    def _postprocess_detections(self, boxes, scores, classes, original_shape, scale_factor):
        filtered_boxes = []
        filtered_scores = []
        # 업스케일된 프레임 좌표를 원본 크기로 환산
        for box, score, cls in zip(boxes.tolist(), scores.tolist(), classes.tolist()):
            if score >= self.confidence_threshold and int(cls) == 0:
                adjusted_box = [coord / scale_factor for coord in box]
                filtered_boxes.append(adjusted_box)
                filtered_scores.append(score)
        num_people = len(filtered_boxes)
        frame_area = original_shape[0] * original_shape[1]
        density = num_people / frame_area if frame_area > 0 else 0
        return filtered_boxes, filtered_scores, num_people, density

    def _define_stage(self, density):
        if density < 0.00005:
            return "SMOOTH"
        elif density < 0.0001:
            return "CAREFUL"
        else:
            return "CROWDED"


# Flask 앱 생성
app = Flask(__name__)

# 모델과 비디오 소스 (YouTube 라이브 스트림 URL) 설정
MODEL_PATH = "yolov8n.pt"
VIDEO_SOURCE = "http://10.32.22.81:8080/stream"

# YOLO 검출기 생성 (confidence_threshold와 detection_scale은 필요에 맞게 조정)
detector = YOLOPeopleDetector(MODEL_PATH, confidence_threshold=0.3, detection_scale=1.5)


def generate_frames():
    # YouTube 주소면 get_youtube_stream_url() 사용, 아니면 직접 사용
    if "youtube" in VIDEO_SOURCE.lower():
        stream_url = get_youtube_stream_url(VIDEO_SOURCE)
    else:
        stream_url = VIDEO_SOURCE

    cap = cv2.VideoCapture(stream_url)
    cap.set(cv2.CAP_PROP_FRAME_WIDTH, 1280)
    cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 720)

    while True:
        ret, frame = cap.read()
        if not ret:
            continue

        result = detector.process_frame(frame)

        # YOLO 분석 결과 시각화
        for box, score in zip(result.boxes, result.scores):
            xmin, ymin, xmax, ymax = map(int, box)
            cv2.rectangle(frame, (xmin, ymin), (xmax, ymax), (0, 255, 0), 2)
            cv2.putText(frame, f'{score:.2f}', (xmin, ymin - 5),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2)
        cv2.putText(frame, f'People: {result.num_people}', (10, 30),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)
        cv2.putText(frame, f'Density: {result.density:.6f}', (10, 70),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 0), 2)
        cv2.putText(frame, f'Stage: {result.stage}', (10, 110),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
        cv2.putText(frame, f'FPS: {result.fps:.1f}', (10, 150),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)

        # 프레임을 JPEG로 인코딩
        ret, buffer = cv2.imencode('.jpg', frame)
        if not ret:
            continue
        frame_bytes = buffer.tobytes()

        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame_bytes + b'\r\n')

@app.route('/')

def index():
    # templates/index.html을 렌더링하여 영상 스트리밍 페이지 표시
    return render_template('index.html')


@app.route('/video_feed')
def video_feed():
    # 실시간 스트리밍을 위한 Response 반환
    return Response(generate_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')


if __name__ == '__main__':
    # 모든 호스트에서 접속 가능하도록 0.0.0.0에서 실행 (포트 5000)
    app.run(host="0.0.0.0", port=5000, debug=True)
