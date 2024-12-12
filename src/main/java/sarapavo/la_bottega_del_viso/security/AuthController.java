package sarapavo.la_bottega_del_viso.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sarapavo.la_bottega_del_viso.exceptions.BadRequestException;
import sarapavo.la_bottega_del_viso.exceptions.UnauthorizedException;
import sarapavo.la_bottega_del_viso.login.APIResponse;
import sarapavo.la_bottega_del_viso.login.APIStatus;
import sarapavo.la_bottega_del_viso.login.LoginDTO;
import sarapavo.la_bottega_del_viso.login.LoginResponseDTO;
import sarapavo.la_bottega_del_viso.tools.JWT;
import sarapavo.la_bottega_del_viso.user.NewUserDTO;
import sarapavo.la_bottega_del_viso.user.User;
import sarapavo.la_bottega_del_viso.user.UserService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    @PostMapping("/login")
    public APIResponse<LoginResponseDTO> login(@RequestBody LoginDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) throw new BadRequestException(
                validationResult.getAllErrors()
                        .stream()
                        .map(err -> err.getDefaultMessage())
                        .collect(Collectors.joining(", ")));
        User found = this.userService.findByEmail(body.email());

        if (found == null) {
            throw new UnauthorizedException("Credenziali errate! Utente non trovato.");
        }

        if (!bcrypt.matches(found.getPassword(), (body.password()))) {
            return new APIResponse<LoginResponseDTO>(APIStatus.SUCCESS, new LoginResponseDTO(jwt.createToken(body.email())), null);

        } else throw new BadRequestException("Password errata!");

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
