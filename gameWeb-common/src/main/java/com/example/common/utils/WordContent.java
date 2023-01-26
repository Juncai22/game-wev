package com.example.common.utils;

public enum WordContent {

    LOGIN_USER("LoginUser"),
    LOGIN("Login");

    private final String word;

    WordContent(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
