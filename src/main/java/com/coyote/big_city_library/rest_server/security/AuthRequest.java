package com.coyote.big_city_library.rest_server.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AuthRequest {
    
    @NonNull
    private String username;

    @NonNull
    private String password;
}
