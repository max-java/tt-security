package com.tutrit.tt.security.custom;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Principal {
    Long id;
    String fullName;
    String password;
}
