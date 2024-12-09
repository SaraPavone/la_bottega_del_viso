package sarapavo.la_bottega_del_viso.salonServices;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<SalonService, Long> {
    Optional<SalonService> findByTitle(String title);
}
