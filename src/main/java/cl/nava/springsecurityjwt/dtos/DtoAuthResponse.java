package cl.nava.springsecurityjwt.dtos;

import lombok.Data;

// This class will be the one that will return the information with the token and the type of the token.
@Data
public class DtoAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer ";

    public DtoAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
