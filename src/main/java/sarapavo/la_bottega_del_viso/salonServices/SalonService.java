package sarapavo.la_bottega_del_viso.salonServices;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "salon_services")
public class SalonService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Il titolo non può essere vuoto")
    private String title;
    @NotBlank(message = "La descrizione non può essere vuota")
    private String description;
    @NotNull(message = "La durata non può essere nulla")
    @Positive(message = "La durata deve essere maggiore di zero")
    private String duration;
    @NotNull(message = "Il prezzo non può essere nullo")
    @Positive(message = "Il prezzo deve essere maggiore di zero")
    private Double price;

    public SalonService() {
    }

    public SalonService(String title, String description, String duration, double price) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SalonService{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                '}';
    }
}