package com.mrmrmr7.task.service.builder;

public class InvalidClientException extends Exception {
    InvalidClientException(String invalidClient) {
        super(invalidClient);
    }
}