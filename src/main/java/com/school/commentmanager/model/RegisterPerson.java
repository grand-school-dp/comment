package com.school.commentmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterPerson {
    private String fullName;
    private String email;
    private String phoneNumber;
}
