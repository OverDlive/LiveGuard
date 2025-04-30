package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.network.model.TouristAttraction;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * 서울시 관광지 API 응답 모델
 */
@Root(name = "TbVwAttractions", strict = false)
public class TouristAttractionData {

    @Element(name = "list_total_count", required = false)
    private int listTotalCount;

    @Element(name = "RESULT", required = false)
    private Result result;

    @ElementList(inline = true, entry = "row", required = false)
    private List<TouristAttraction> row;

    public int getListTotalCount() {
        return listTotalCount;
    }

    public void setListTotalCount(int listTotalCount) {
        this.listTotalCount = listTotalCount;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<TouristAttraction> getRow() {
        return row;
    }

    public void setRow(List<TouristAttraction> row) {
        this.row = row;
    }

    /**
     * RESULT 블록용 모델 클래스
     */
    public static class Result {
        @Element(name = "CODE", required = false)
        private String code;

        @Element(name = "MESSAGE", required = false)
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}