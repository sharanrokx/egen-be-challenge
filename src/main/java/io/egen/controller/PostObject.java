package io.egen.controller;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostObject implements Serializable{
    @JsonProperty("timeStamp")
	String timeStamp;
    @JsonProperty("value")
    String value;
    public String getTimeStamp() {
		return timeStamp;
	}
    public String getValue() {
		return value;
	}
    public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
    public void setValue(String value) {
		this.value = value;
	}
}
