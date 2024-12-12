package sarapavo.la_bottega_del_viso.reservation;


import jakarta.persistence.*;
import sarapavo.la_bottega_del_viso.salonServices.SalonService;
import sarapavo.la_bottega_del_viso.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private int duration;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
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

    public Reservation(LocalDate appointmentDate, LocalTime appointmentTime, int duration, String notes, User user, SalonService salonService, List<SalonService> salonServices) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.duration = duration;
        this.notes = notes;
        this.user = user;
        this.salonService = salonService;
        this.salonServices = salonServices;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SalonService getSalonService() {
        return salonService;
    }

    public void setSalonService(SalonService salonService) {
        this.salonService = salonService;
    }

    public List<SalonService> getSalonServices() {
        return salonServices;
    }

    public void setSalonServices(List<SalonService> salonServices) {
        this.salonServices = salonServices;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", duration=" + duration +
                ", notes='" + notes + '\'' +
                ", user=" + user +
                ", salonService=" + salonService +
                ", salonServices=" + salonServices +
                '}';
    }
}

