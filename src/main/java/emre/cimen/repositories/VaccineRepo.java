package emre.cimen.repositories;

import emre.cimen.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Integer> {
    List<Vaccine> findByprotectionFnshDateBetween(LocalDate entryDate, LocalDate exitDate);
    List<Vaccine> findByCodeAndName(String code, String name);
    List<Vaccine> findByAnimalId(int id);
}
