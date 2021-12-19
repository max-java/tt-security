package com.tutrit.tt.security.custom;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
    String token = "null";

    public String serializeToString() {
        return "token," + token +
                ",id," + id +
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

        principal.setToken(values[1]);
        principal.setId(Long.valueOf(values[3]));
        principal.setFullName(values[5]);
        principal.setIsAuthenticated(Boolean.valueOf(values[9]));
        List<String> roles = IntStream.range(12, values.length)
                .boxed()
                .map(i -> values[i])
                .map(el -> String.valueOf(el))
                .collect(Collectors.toList());

        principal.setRoles(roles.toArray(new String[roles.size()]));

        return principal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Principal principal = (Principal) o;
        return Objects.equals(id, principal.id) && Objects.equals(fullName, principal.fullName) && Arrays.equals(roles, principal.roles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, fullName);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }
}
