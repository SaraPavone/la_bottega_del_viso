package sarapavo.la_bottega_del_viso.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sarapavo.la_bottega_del_viso.exceptions.BadRequestException;
import sarapavo.la_bottega_del_viso.salonServices.SalonService;
import sarapavo.la_bottega_del_viso.salonServices.ServiceRepository;
import sarapavo.la_bottega_del_viso.salonServices.ServiceService;
import sarapavo.la_bottega_del_viso.user.User;
import sarapavo.la_bottega_del_viso.user.UserRepository;
import sarapavo.la_bottega_del_viso.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserService userService;


    public boolean existsByDateAndTime(LocalDateTime.of(year, month, day, hour, minute, 0)) {
        return reservationRepository.existsByDateAndTime();
    }


    public Reservation saveReservation(NewReservationDTO body) {
        try {
            User userFound = userService.findByNameAndSurname(body.name(), body.surname());
            SalonService salonServiceFound = serviceService.findByTitle(body.salonServiceTitle());


            Reservation newReservation = new Reservation(
                    userFound,
                    salonServiceFound,
                    body.appointmentDateTime().getYear(),
                    body.appointmentDateTime().getMonthValue(),
                    body.appointmentDateTime().getDayOfMonth(),
                    body.appointmentDateTime().getHour(),
                    body.appointmentDateTime().getMinute()
            );

            return reservationRepository.save(newReservation);

        } catch (BadRequestException e) {
            throw new BadRequestException("Errore nel salvataggio della prenotazione: " + e.getMessage());

        }

    }

}
