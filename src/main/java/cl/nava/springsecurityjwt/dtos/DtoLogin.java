package cl.nava.springsecurityjwt.dtos;

import lombok.Data;

@Data
public class DtoLogin {
    private String username;
    private String password;
}
