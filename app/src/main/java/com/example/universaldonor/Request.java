package com.example.universaldonor;


import java.util.Date;

public class Request {
    static long curRequestId = 0;
    private String requestedBy;
    private String requestedTo;
    private final long requestId;
    private Date dateRequested;
    //private String message;
    private boolean isSettled = false;

    public Request(String requestedBy, String requestedTo) {
        this.requestedBy = requestedBy;
        this.requestedTo = requestedTo;
        Date date = new Date();
        this.dateRequested = date;
        requestId = curRequestId++;
    }

    public Request() {
        requestId = curRequestId++;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public String getRequestedTo() {
        return requestedTo;
    }

    public long getRequestId() {
        return requestId;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public boolean isSettled() {
        return isSettled;
    }
}
