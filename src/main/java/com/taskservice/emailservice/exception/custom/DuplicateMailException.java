package com.taskservice.emailservice.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateMailException extends RuntimeException {

    public DuplicateMailException(String email) {
        super("Such email " + email + " exist");
    }
}
