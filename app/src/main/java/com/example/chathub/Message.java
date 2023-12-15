package com.example.chathub;

public class Message {
    String message;
    String senderId;

    Message(){

    }

    public Message(String message, String senderId){
        this.message = message;
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
