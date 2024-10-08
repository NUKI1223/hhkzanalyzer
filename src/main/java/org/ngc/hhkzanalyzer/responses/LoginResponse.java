package org.ngc.hhkzanalyzer.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;

    private long expires;

    public LoginResponse(String token, long expires) {
        this.token = token;
        this.expires = expires;
    }
}
