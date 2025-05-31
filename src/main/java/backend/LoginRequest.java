package backend;

import lombok.Data;

@Data
public class LoginRequest {
    private String nombre_usuario;
    private String password;
    // Getters y setters
}

