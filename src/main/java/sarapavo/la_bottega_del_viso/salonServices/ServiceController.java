package sarapavo.la_bottega_del_viso.salonServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sarapavo.la_bottega_del_viso.exceptions.BadRequestException;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping("/save")
//    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public SalonService save(@RequestBody @Validated NewSalonServiceDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.serviceService.save(body);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Page<SalonService> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        try {
            return this.serviceService.findAll(page, size, sortBy);
        } catch (Exception e) {
            throw new BadRequestException("Errore nel recupero dei servizi: " + e.getMessage());
        }
    }

    @GetMapping("/search-by-title")
    public SalonService findByTitle(@RequestParam String title) {
        return this.serviceService.findByTitle(title);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SalonService findByIdAndUpdate(@RequestParam Long id, @RequestParam SalonService salonService) {
        return this.serviceService.findByIdAndUpdate(id, salonService);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SalonService findByIdAndDelete(@RequestParam Long id) {
        return this.serviceService.finbyIdAndDelete(id);
    }

}
