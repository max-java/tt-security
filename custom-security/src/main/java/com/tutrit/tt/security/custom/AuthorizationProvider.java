package com.tutrit.tt.security.custom;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorizationProvider {
    List<AuthorizedEndpoints> endpointsList;

    public boolean isUserHasAccess(final String endpoint,
                                   final Principal principal) {
        Optional<AuthorizedEndpoints> authorizedEndpoint = endpointsList.stream()
                .filter(ae -> endpoint.startsWith(ae.getUri()))
                .findFirst();

        if(authorizedEndpoint.isEmpty()) {
            return true;
        }

        boolean hasAccess = Arrays.stream(principal.getRoles()).anyMatch(r -> authorizedEndpoint.get().getRole().equals(r));
        if(hasAccess) {
            return true;
        }
        return false;
    }
}
