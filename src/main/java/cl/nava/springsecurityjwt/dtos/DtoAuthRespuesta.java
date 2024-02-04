package cl.nava.springsecurityjwt.dtos;

import lombok.Data;

//Esta clase sera la que nos devolbera la información con el token y el tipo que tenga este
@Data
public class DtoAuthRespuesta {
    private String accessToken;
    private String tokenType = "Bearer ";

    public DtoAuthRespuesta(String accessToken) {
        this.accessToken = accessToken;
    }
}
