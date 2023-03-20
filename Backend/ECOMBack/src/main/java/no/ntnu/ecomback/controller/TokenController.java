package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.LoginRequest;
import no.ntnu.ecomback.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/token")
@EnableAutoConfiguration
@CrossOrigin
public class TokenController {


    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String getToken(final @RequestBody LoginRequest loginRequest) {
        return tokenService.getToken(loginRequest);


    }


}