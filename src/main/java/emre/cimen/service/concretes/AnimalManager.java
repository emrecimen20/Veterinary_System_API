package emre.cimen.service.concretes;

import emre.cimen.core.config.ConvertEntityToResponse;
import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.exception.DataAlreadyExistException;
import emre.cimen.core.exception.NotFoundException;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.Msg;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.animal.AnimalSaveRequest;
import emre.cimen.dto.request.animal.AnimalUpdateRequest;
import emre.cimen.dto.response.animal.AnimalResponse;
import emre.cimen.entities.Animal;
import emre.cimen.entities.Customer;
import emre.cimen.repositories.AnimalRepo;
import emre.cimen.service.abstracts.IAnimalService;
import emre.cimen.service.abstracts.ICustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalManager implements IAnimalService {

    private final ConvertEntityToResponse<Animal,AnimalResponse> convert;
    private final AnimalRepo animalRepo;
    private final IModelMapperService modelMapperService;
    private final ICustomerService customerService;

    public AnimalManager(ConvertEntityToResponse<Animal, AnimalResponse> convert, AnimalRepo animalRepo, IModelMapperService modelMapperService, ICustomerService customerService) {
        this.convert = convert;
        this.animalRepo = animalRepo;
        this.modelMapperService = modelMapperService;
        this.customerService = customerService;
    }

    @Override
    public Animal get(int id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(int id) {
        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return true;
    }

    @Override
    public ResultData<AnimalResponse> save(AnimalSaveRequest animalSaveRequest) {
        Customer customer = this.customerService.get(animalSaveRequest.getCustomerId());
        animalSaveRequest.setCustomerId(0);
        Animal saveAnimal = this.modelMapperService.forRequest().map(animalSaveRequest, Animal.class);
        saveAnimal.setCustomer(customer);
        List<Animal> animalList = this.findByNameAndSpeciesAndBreedAndGender(
                animalSaveRequest.getName(),
                animalSaveRequest.getSpecies(),
                animalSaveRequest.getBreed(),
                animalSaveRequest.getGender()
        );
        if (!animalList.isEmpty()){
            throw new DataAlreadyExistException(Msg.getEntityForMsg(Animal.class));
        }

        return ResultHelper.created(this.modelMapperService.forResponse().map(this.animalRepo.save(saveAnimal), AnimalResponse.class));
    }

    @Override
    public ResultData<AnimalResponse> update(AnimalUpdateRequest animalUpdateRequest) {
        this.get(animalUpdateRequest.getId());
        Animal updateAnimal = this.modelMapperService.forRequest().map(animalUpdateRequest, Animal.class);
        this.animalRepo.save(updateAnimal);
        return ResultHelper.success(this.modelMapperService.forResponse().map(updateAnimal, AnimalResponse.class));
    }

    @Override
    public ResultData<CursorResponse<AnimalResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Animal> animalPage = this.animalRepo.findAll(pageable);
        Page<AnimalResponse> animalResponsePage = animalPage.map(animal -> this.modelMapperService.forResponse().map(animal, AnimalResponse.class));
        return ResultHelper.cursor(animalResponsePage);
    }

    @Override
    public ResultData<List<AnimalResponse>> findByName(String name) {
        List<Animal> animalList = this.animalRepo.findByName(name);
        List<AnimalResponse> animalResponseList = this.convert.convertToResponseList(animalList, AnimalResponse.class);
        return ResultHelper.success(animalResponseList);
    }

    @Override
    public ResultData<List<AnimalResponse>> findByCustomerId(int id) {
        List<Animal> animalList = this.animalRepo.findByCustomerId(id);
        List<AnimalResponse> animalResponseList = this.convert.convertToResponseList(animalList, AnimalResponse.class);
        return ResultHelper.success(animalResponseList);
    }

    @Override
    public List<Animal> findByNameAndSpeciesAndBreedAndGender(String name, String species, String breed, Animal.GENDER gender) {
        return List.of();
    }
}
