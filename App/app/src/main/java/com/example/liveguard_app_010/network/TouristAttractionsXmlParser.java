package com.example.liveguard_app_010.network;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;

// XML 파싱 클래스: 전달받은 XML 문자열에서 관광지 정보를 추출 (한글 데이터만 파싱)
public class TouristAttractionsXmlParser {

    // XML 데이터를 파싱하여 TouristAttractionData 객체로 반환
    public TouristAttractionData parse(String xmlData) {
        TouristAttractionData data = new TouristAttractionData();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    String text = parser.nextText().trim();
                    if (containsHangul(text)) {
                        if ("ADDRESS".equalsIgnoreCase(tagName)) {
                            data.setAddress(text);
                        } else if ("MESSAGE".equalsIgnoreCase(tagName)) {
                            data.setMessage(text);
                        }
                        // 추가로 한글 데이터가 포함된 태그가 있으면 여기에 else if 문을 추가
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // 주어진 문자열에 한글 문자가 포함되어 있는지 검사
    private boolean containsHangul(String text) {
        return text != null && text.matches(".*[ㄱ-ㅎ가-힣]+.*");
    }
}