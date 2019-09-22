package com.taskservice.emailservice.exception.custom;

import com.taskservice.emailservice.model.Email;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(Email email) {
        super("Such email with id " + email.getId() + " not exist");
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
