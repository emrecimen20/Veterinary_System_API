package emre.cimen.service.abstracts;

import emre.cimen.core.result.ResultData;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.animal.AnimalSaveRequest;
import emre.cimen.dto.request.animal.AnimalUpdateRequest;
import emre.cimen.dto.response.animal.AnimalResponse;
import emre.cimen.entities.Animal;

import java.util.List;

public interface IAnimalService {
    Animal get(int id);
    boolean delete(int id);
    ResultData<AnimalResponse> save(AnimalSaveRequest animalSaveRequest);
    ResultData<AnimalResponse> update(AnimalUpdateRequest animalUpdateRequest);
    ResultData<CursorResponse<AnimalResponse>> cursor(int page, int pageSize);
    ResultData<List<AnimalResponse>> findByName(String name);
    ResultData<List<AnimalResponse>> findByCustomerId(int id);
    List<Animal> findByNameAndSpeciesAndBreedAndGender(String name,String species,String breed,Animal.GENDER gender);
}
