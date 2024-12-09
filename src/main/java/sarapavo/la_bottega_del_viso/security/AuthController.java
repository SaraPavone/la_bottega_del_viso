package sarapavo.la_bottega_del_viso.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sarapavo.la_bottega_del_viso.exceptions.BadRequestException;
import sarapavo.la_bottega_del_viso.login.LoginDTO;
import sarapavo.la_bottega_del_viso.login.LoginResponseDTO;
import sarapavo.la_bottega_del_viso.user.NewUserDTO;
import sarapavo.la_bottega_del_viso.user.User;
import sarapavo.la_bottega_del_viso.user.UserService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }


    @PostMapping("/register/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated NewUserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.userService.save(body);
    }


}
