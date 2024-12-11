package sarapavo.la_bottega_del_viso.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkReservation(@RequestParam LocalDate date, @RequestParam LocalTime time) {
        boolean exists = reservationService.existsByDateAndTime(date, time);
        return ResponseEntity.ok(exists);
    }
}
