package com.om.springboot.dto.response.auth;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private boolean success = false;

    private String message;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }


    public JwtAuthenticationResponse(String accessToken, boolean success, String message) {
        this.accessToken = accessToken;
        this.success = success;
        this.message = message;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;

    }
}
