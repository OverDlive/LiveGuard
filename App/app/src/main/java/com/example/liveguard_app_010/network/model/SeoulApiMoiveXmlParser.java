package com.example.liveguard_app_010.network.model;

import android.util.Xml;
import com.example.liveguard_app_010.network.MultiPerformanceMovieRequester;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 서울시 API를 호출하여 XML 데이터를 받아오고, 각 XML 태그별로 파싱하는 클래스
 * (API 호출은 기존 MultiPerformanceMovieRequester.java를 활용하고,
 *  XML 파싱 로직만 별도로 분리하여 이 클래스에서 처리할 수 있습니다.)
 *
 * XML 예시:
 * <?xml version="1.0" encoding="UTF-8"?>
 * <LOCALDATA_031302_GN>
 *     <list_total_count>112</list_total_count>
 *     <RESULT>
 *         <CODE>INFO-000</CODE>
 *         <MESSAGE>정상 처리되었습니다</MESSAGE>
 *     </RESULT>
 *     <row>
 *         <OPNSFTEAMCODE>3220000</OPNSFTEAMCODE>
 *         <MGTNO>CDFF4220001983000001</MGTNO>
 *         <APVPERMYMD>1983-06-04</APVPERMYMD>
 *         <!-- ... 기타 태그들 ... -->
 *         <PERPLAFORMSENM>영화관</PERPLAFORMSENM>
 *         <ACTLNM/>
 *         <FRSTREGTS>19830604</FRSTREGTS>
 *         <REGNSENM/>
 *     </row>
 *     <!-- ... 추가 row들 ... -->
 * </LOCALDATA_031302_GN>
 */
public class SeoulApiMoiveXmlParser {

    public interface RequestCallback {
        void onSuccess(SeoulApiResponse response);
        void onError(Exception e);
    }

    // 데이터 모델 클래스
    public static class SeoulApiResponse {
        public int listTotalCount;
        public Result result;
        public List<Row> rows = new ArrayList<>();

        public static class Result {
            public String code;
            public String message;

            @Override
            public String toString() {
                return "Result{" +
                        "code='" + code + '\'' +
                        ", message='" + message + '\'' +
                        '}';
            }
        }

        public static class Row {
            public String opnsfteamcode;
            public String mgtno;
            public String apvpermymd;
            public String apvcancelymd;
            public String trdstategbn;
            public String trdstatenm;
            public String perplaforsenm;
            public String actlnm;
            public String frstregts;
            public String regnsenm;

            @Override
            public String toString() {
                return "Row{" +
                        "opnsfteamcode='" + opnsfteamcode + '\'' +
                        ", mgtno='" + mgtno + '\'' +
                        ", apvpermymd='" + apvpermymd + '\'' +
                        ", trdstategbn='" + trdstategbn + '\'' +
                        ", trdstatenm='" + trdstatenm + '\'' +
                        ", perplaforsenm='" + perplaforsenm + '\'' +
                        ", frstregts='" + frstregts + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "SeoulApiResponse{" +
                    "listTotalCount=" + listTotalCount +
                    ", result=" + result +
                    ", rows=" + rows +
                    '}';
        }
    }

    /**
     * API를 호출하고 XML 데이터를 파싱하여 callback으로 전달
     * @param requestUrl API 요청 URL (서울시 API URL)
     * @param callback 결과 callback
     */
    public void requestPerformanceData(final String requestUrl, final RequestCallback callback) {
        MultiPerformanceMovieRequester requester = new MultiPerformanceMovieRequester();
        requester.request(requestUrl, new MultiPerformanceMovieRequester.RequestCallback() {
            @Override
            public void onResponse(String xml) {
                try {
                    SeoulApiResponse response = parseXml(xml);
                    callback.onSuccess(response);
                } catch (Exception e) {
                    callback.onError(e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                callback.onError(e);
            }
        });
    }

    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

    private SeoulApiResponse parseXml(String xml) throws XmlPullParserException, IOException {
        SeoulApiResponse response = new SeoulApiResponse();
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new java.io.StringReader(xml));

        int eventType = parser.getEventType();
        SeoulApiResponse.Row currentRow = null;
        String currentTag = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    tagName = parser.getName();
                    if ("list_total_count".equals(tagName)) {
                        currentTag = tagName;
                    } else if ("RESULT".equals(tagName)) {
                        response.result = new SeoulApiResponse.Result();
                    } else if ("row".equals(tagName)) {
                        currentRow = new SeoulApiResponse.Row();
                    } else {
                        currentTag = tagName;
                    }
                    break;
                case XmlPullParser.TEXT:
                    String text = parser.getText();
                    if (currentTag != null) {
                        if ("list_total_count".equals(currentTag)) {
                            try {
                                response.listTotalCount = Integer.parseInt(text.trim());
                            } catch (NumberFormatException e) {
                                response.listTotalCount = 0;
                            }
                        } else if (response.result != null) {
                            if ("CODE".equals(currentTag)) {
                                response.result.code = text.trim();
                            } else if ("MESSAGE".equals(currentTag)) {
                                response.result.message = text.trim();
                            }
                        } else if (currentRow != null) {
                            switch (currentTag) {
                                case "OPNSFTEAMCODE":
                                    currentRow.opnsfteamcode = text.trim();
                                    break;
                                case "MGTNO":
                                    currentRow.mgtno = text.trim();
                                    break;
                                case "APVPERMYMD":
                                    currentRow.apvpermymd = text.trim();
                                    break;
                                case "APVCANCELYMD":
                                    currentRow.apvcancelymd = text.trim();
                                    break;
                                case "TRDSTATEGBN":
                                    currentRow.trdstategbn = text.trim();
                                    break;
                                case "TRDSTATENM":
                                    currentRow.trdstatenm = text.trim();
                                    break;
                                case "PERPLAFORMSENM":
                                    currentRow.perplaforsenm = text.trim();
                                    break;
                                case "ACTLNM":
                                    currentRow.actlnm = text.trim();
                                    break;
                                case "FRSTREGTS":
                                    currentRow.frstregts = text.trim();
                                    break;
                                case "REGNSENM":
                                    currentRow.regnsenm = text.trim();
                                    break;
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    if ("row".equals(tagName) && currentRow != null) {
                        response.rows.add(currentRow);
                        currentRow = null;
                    }
                    currentTag = null;
                    break;
            }
            eventType = parser.next();
        }
        return response;
    }
}