package com.boldyrev.billmakertelegrambot.exceptions;

public class UserNotRegisteredException extends RuntimeException {

    public UserNotRegisteredException(String message) {
        super(message);
    }
}
