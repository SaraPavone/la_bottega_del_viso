package sarapavo.la_bottega_del_viso.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sarapavo.la_bottega_del_viso.admin.Admin;
import sarapavo.la_bottega_del_viso.admin.AdminService;
import sarapavo.la_bottega_del_viso.admin.NewAdminDTO;
import sarapavo.la_bottega_del_viso.customer.Customer;
import sarapavo.la_bottega_del_viso.customer.CustomerService;
import sarapavo.la_bottega_del_viso.customer.NewCustomerDTO;
import sarapavo.la_bottega_del_viso.exceptions.BadRequestException;
import sarapavo.la_bottega_del_viso.user.LoginDTO;
import sarapavo.la_bottega_del_viso.user.LoginResponseDTO;
import sarapavo.la_bottega_del_viso.user.UserService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin save(@RequestBody @Validated NewAdminDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.adminService.save(body);
    }

    @PostMapping("/register/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer save(@RequestBody @Validated NewCustomerDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        ;

        return this.userService.save(body);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }


}
