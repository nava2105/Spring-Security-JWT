package cl.nava.springsecurityjwt.dtos;

import lombok.Data;

@Data
public class DtoAssignRole {
    private String username;
    private String role;
}