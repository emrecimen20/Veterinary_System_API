package emre.cimen.repositories;

import emre.cimen.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    List<Customer> findByName(String name);
    boolean existsByPhone(String phone);
    boolean existsByMail(String email);
    List<Customer> findByNameAndMailAndPhone(String name, String mail, String phone);
}
