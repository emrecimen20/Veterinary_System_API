package emre.cimen.repositories;

import emre.cimen.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate,Integer> {
    List<AvailableDate> findByDateAndDoctorId(LocalDate availableDate, Integer doctorId);
}
