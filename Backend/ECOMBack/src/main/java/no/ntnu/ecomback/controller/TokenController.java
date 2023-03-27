package no.ntnu.ecomback.controller;

import io.swagger.v3.oas.annotations.Operation;
import no.ntnu.ecomback.model.LoginRequest;
import no.ntnu.ecomback.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/token")

@CrossOrigin
public class TokenController {


    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Operation(summary = "Get a token for a user with valid credentials")
    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String getToken(final @RequestBody LoginRequest loginRequest) {
        return tokenService.getToken(loginRequest);


    }


}