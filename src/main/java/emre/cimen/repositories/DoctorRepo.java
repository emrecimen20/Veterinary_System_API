package emre.cimen.repositories;

import emre.cimen.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Integer> {
    boolean existsByPhone(String phone);
    boolean existsByMail(String email);
    List<Doctor> findByIdAndAvailableDateDate(int id, LocalDate localDate);
    List<Doctor> findByNameAndMailAndPhone(String name, String mail, String phone);
}
