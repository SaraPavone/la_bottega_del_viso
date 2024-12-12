package sarapavo.la_bottega_del_viso.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/available-slots")
    public ResponseEntity<List<LocalTime>> getAvailableTimeSlots(@RequestParam LocalDate date, @RequestParam Long serviceId) {
        List<LocalTime> availableSlots = reservationService.getAvailableTimeSlots(date, serviceId);
        return ResponseEntity.ok(availableSlots);
    }

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody NewReservationDTO body) {
        Reservation reservation = reservationService.createReservation(body.date(), body.time(), body.salonServiceId());
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }


//    @GetMapping("/check")
//    public ResponseEntity<Boolean> checkReservation(@RequestParam LocalDate date, @RequestParam LocalTime time) {
//        boolean exists = reservationService.existsByDateAndTime(date, time);
//        return ResponseEntity.ok(exists);
//    }
}
