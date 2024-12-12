package sarapavo.la_bottega_del_viso.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sarapavo.la_bottega_del_viso.salonServices.SalonService;
import sarapavo.la_bottega_del_viso.salonServices.ServiceRepository;
import sarapavo.la_bottega_del_viso.salonServices.ServiceService;
import sarapavo.la_bottega_del_viso.user.UserRepository;
import sarapavo.la_bottega_del_viso.user.UserService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<LocalTime> getAvailableTimeSlots(LocalDate date, Long serviceId) {
        SalonService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));

        int serviceDuration = Integer.parseInt(service.getDuration());

        List<LocalTime> availableSlots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0);

        while (startTime.plusMinutes(serviceDuration).isBefore(LocalTime.of(18, 0))) {

            if (!isTimeSlotBooked(date, startTime, startTime.plusMinutes(serviceDuration))) {
                availableSlots.add(startTime);
            }
            startTime = startTime.plusMinutes(15);
        }

        return availableSlots;
    }


    private boolean isTimeSlotBooked(LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Reservation> reservations = reservationRepository.findByAppointmentDateAndAppointmentTimeBetween(date, startTime, endTime);
        return !reservations.isEmpty();
    }


    public Reservation createReservation(LocalDate date, LocalTime time, Long serviceId) {
        SalonService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));

        Reservation reservation = new Reservation();
        reservation.setAppointmentDate(date);
        reservation.setAppointmentTime(time);
        reservation.setSalonService(service);

        return reservationRepository.save(reservation);
    }
}

//    public boolean existsByDateAndTime(LocalDateTime.of(year, month, day, hour, minute, 0)) {
//        return reservationRepository.existsByDateAndTime();
//    }
//
//
//    public Reservation saveReservation(NewReservationDTO body) {
//        try {
//            User userFound = userService.findByNameAndSurname(body.name(), body.surname());
//            SalonService salonServiceFound = serviceService.findByTitle(body.salonServiceTitle());
//
//
//            Reservation newReservation = new Reservation(
//                    userFound,
//                    salonServiceFound,
//                    body.appointmentDateTime().getYear(),
//                    body.appointmentDateTime().getMonthValue(),
//                    body.appointmentDateTime().getDayOfMonth(),
//                    body.appointmentDateTime().getHour(),
//                    body.appointmentDateTime().getMinute()
//            );
//
//            return reservationRepository.save(newReservation);
//
//        } catch (BadRequestException e) {
//            throw new BadRequestException("Errore nel salvataggio della prenotazione: " + e.getMessage());
//
//        }
//
//    }


