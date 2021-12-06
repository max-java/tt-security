package com.tutrit.tt.security.custom;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Principal {
    Long id;
    String fullName;
    String password;
    String[] roles; //USER, ADMIN
    Boolean isAuthenticated = false;

    public String serializeToString() {
        return "id," + id +
                ",fullName," + fullName +
                ",password," + password +
                ",isAuthenticated," + isAuthenticated +
                ",roles," + Arrays.toString(roles)
                .replace("[", ",")
                .replace("]", ",")
                .replace(" ", "")
                ;
    }

    public static Principal deserializeFromString(String string) {

        Principal principal = new Principal();
        String[] values = string.split(",");

        principal.setId(Long.valueOf(values[1]));
        principal.setFullName(values[3]);
        principal.setIsAuthenticated(Boolean.valueOf(values[7]));
        List<String> roles = IntStream.range(10, values.length)
                .boxed()
                .map(i -> values[i])
                .map(el -> String.valueOf(el))
                .collect(Collectors.toList());

        principal.setRoles(roles.toArray(new String[roles.size()]));

        return principal;
    }
}
