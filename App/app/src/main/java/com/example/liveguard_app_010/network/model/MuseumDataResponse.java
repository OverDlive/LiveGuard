package com.example.liveguard_app_010.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "LOCALDATA_030705", strict = false)
public class MuseumDataResponse {

    @Element(name = "list_total_count", required = false)
    private int listTotalCount;

    @Element(name = "RESULT", required = false)
    private Result result;

    // row 태그들이 반복되므로 inline=true로 매핑
    @ElementList(name = "row", inline = true, required = false)
    private List<MuseumData> row;

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

    public List<MuseumData> getRow() {
        return row;
    }

    public void setRow(List<MuseumData> row) {
        this.row = row;
    }

    @Root(name = "RESULT", strict = false)
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

    @Override
    public String toString() {
        return "MuseumDataResponse{" +
                "listTotalCount=" + listTotalCount +
                ", result=" + result +
                ", row=" + row +
                '}';
    }
}