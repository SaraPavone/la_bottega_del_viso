package sarapavo.la_bottega_del_viso.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import sarapavo.la_bottega_del_viso.salonServices.SalonService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<SalonService> findBySalonServiceTitle(String title);

    List<Reservation> findByAppointmentDateAndAppointmentTimeBetween(LocalDate appointmentDate, LocalTime startTime, LocalTime endTime);
    //Boolean existsByDateAndTime(LocalDateTime.of(year, month, day, hour, minute, 0));
}
