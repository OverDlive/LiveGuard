package com.example.liveguard_app_010.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "TbVwAttractions", strict = false)
public class TouristAttractionData {

    private String name;

    @Element(name = "ADDRESS", required = false)
    @Path("row")
    private String address;

    @Element(name = "MESSAGE", required = false)
    @Path("RESULT")
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}