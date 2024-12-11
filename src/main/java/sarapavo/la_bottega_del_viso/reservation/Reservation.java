package sarapavo.la_bottega_del_viso.reservation;


import jakarta.persistence.*;
import sarapavo.la_bottega_del_viso.salonServices.SalonService;
import sarapavo.la_bottega_del_viso.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate reservationDate;
    private LocalDateTime appointmentDateTime;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "salon_service_id")
    private SalonService salonService;

    @ManyToMany
    @JoinTable(
            name = "reservation_services",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<SalonService> salonServices = new ArrayList<>();

    public Reservation() {
    }

    public Reservation(User user, SalonService salonService, int year, int month, int day, int hour, int minute) {
        this.reservationDate = LocalDate.now();
        this.appointmentDateTime = LocalDateTime.of(year, month, day, hour, minute, 0);
        this.user = user;
        this.salonService = salonService;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationDate=" + reservationDate +
                ", appointmentDateTime=" + appointmentDateTime +
                ", user=" + user +
                ", salonService=" + salonService +
                ", services=" + salonServices +
                '}';
    }
}
