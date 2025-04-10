package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.network.model.MuseumData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;

public class MuseumDataXmlParser {

    // XML 데이터를 파싱하여 MuseumData 객체로 반환
    public MuseumData parse(String xmlData) {
        MuseumData museumData = new MuseumData();
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
                    if ("BPLCNM".equalsIgnoreCase(tagName)) {
                        museumData.setBplcNm(text);
                    } else if ("SITEWHLADDR".equalsIgnoreCase(tagName)) {
                        museumData.setSiteWhlAddr(text);
                    } else if ("RDNWHLADDR".equalsIgnoreCase(tagName)) {
                        museumData.setRdnWhlAddr(text);
                    } else if ("CULPHYEDCOBNM".equalsIgnoreCase(tagName)) {
                        museumData.setCulphyEdcObNm(text);
                    } else if ("OPENYMD".equalsIgnoreCase(tagName)) {
                        museumData.setOpenYmd(text);
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return museumData;
    }

    // 주어진 문자열에 한글 문자가 포함되어 있는지 검사
    private boolean containsHangul(String text) {
        return text != null && text.matches(".*[ㄱ-ㅎ가-힣]+.*");
    }
}