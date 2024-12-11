package sarapavo.la_bottega_del_viso.salonServices;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sarapavo.la_bottega_del_viso.exceptions.BadRequestException;
import sarapavo.la_bottega_del_viso.exceptions.NotFoundException;
import sarapavo.la_bottega_del_viso.reservation.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;


@Service
public class ServiceService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceService.class);
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public SalonService save(NewSalonServiceDTO body) {
        this.serviceRepository.findByTitle(body.title()).ifPresent(
                service -> {
                    throw new BadRequestException("Titolo " + body.title() + " già in uso!");
                }
        );
        SalonService newSalonService = new SalonService(body.title(), body.description(), body.duration(), body.price());

        return this.serviceRepository.save(newSalonService);
    }

    public boolean isReservationExists(LocalDate date, LocalTime time) {
        return reservationRepository.existsByDateAndTime(date, time);
    }

    public Page<SalonService> findAll(int page, int size, String sortBy) {
        try {
            if (size > 100)
                size = 100;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            return this.serviceRepository.findAll(pageable);
        } catch (Exception e) {
            throw new BadRequestException("Errore nel recupero dei servizi: " + e.getMessage());
        }
    }

    public SalonService findByTitle(String title) {
        try {
            return this.serviceRepository.findByTitle(title).orElseThrow(() -> new NotFoundException("Il trattamento con titolo " + title + " non é stato trovato"));
        } catch (NotFoundException e) {
            throw new BadRequestException("Errore nel recupero del trattamento: " + e.getMessage());
        }

    }

//    @Transactional
//    public SalonService save(NewSalonServiceDTO body) {
//        try {
//            logger.debug("Tentativo di salvataggio: {}", body);
//            SalonService newSalonService = new SalonService(body.title(), body.description(), body.duration(), body.price());
//            SalonService savedService = this.serviceRepository.save(newSalonService);
//            logger.debug("Salvataggio completato: {}", savedService);
//            return savedService;
//        } catch (Exception e) {
//            logger.error("Errore durante il salvataggio: {}", e.getMessage(), e);
//            throw new BadRequestException("Errore nel salvataggio del trattamento: " + e.getMessage());
//        }
//    }

    public SalonService findById(Long id) {
        try {
            return this.serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Il trattamento con id " + id + " non é stato trovato"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Errore nel recupero del trattamento: " + e.getMessage());
        }
    }

    public SalonService findByIdAndUpdate(Long id, SalonService salonService) {
        SalonService salonServiceToUpdate = this.serviceRepository.findById(id).get();
        salonServiceToUpdate.setTitle(salonService.getTitle());
        salonServiceToUpdate.setDescription(salonService.getDescription());
        salonServiceToUpdate.setDuration(salonService.getDuration());
        salonServiceToUpdate.setPrice(salonService.getPrice());
        return this.serviceRepository.save(salonServiceToUpdate);
    }

    public SalonService finbyIdAndDelete(Long id) {
        SalonService salonService = this.serviceRepository.findById(id).get();
        this.serviceRepository.deleteById(id);
        return salonService;

    }
}
