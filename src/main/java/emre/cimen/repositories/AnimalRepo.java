package emre.cimen.repositories;

import emre.cimen.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AnimalRepo extends JpaRepository<Animal,Integer> {
    List<Animal> findByName(String name);
    List<Animal> findByCustomerId(int id);
    List<Animal> findByNameAndSpeciesAndBreedAndGender(String name,String species,String breed,Animal.GENDER gender);
}
