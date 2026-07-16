package com.dlinteriorismo.sistema_interiorismo.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String usuario;
    private String password;

}