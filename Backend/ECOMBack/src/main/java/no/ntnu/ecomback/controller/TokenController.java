package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.LoginRequest;
import no.ntnu.ecomback.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/token")
<<<<<<< HEAD

=======
>>>>>>> 8e474c9d99b00de11c77176c9d49f0a68eb46b3b
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