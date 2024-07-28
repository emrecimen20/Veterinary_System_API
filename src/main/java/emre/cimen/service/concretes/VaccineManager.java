package emre.cimen.service.concretes;

import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.exception.DataAlreadyExistException;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.Msg;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.vaccine.VaccineSaveRequest;
import emre.cimen.dto.request.vaccine.VaccineUpdateRequest;
import emre.cimen.dto.response.vaccine.VaccineResponse;
import emre.cimen.entities.Animal;
import emre.cimen.entities.Vaccine;
import emre.cimen.repositories.VaccineRepo;
import emre.cimen.service.abstracts.IAnimalService;
import emre.cimen.service.abstracts.IVaccineService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccineManager implements IVaccineService {
    private final VaccineRepo vaccineRepo;
    private final IModelMapperService modelMapperService;
    private final IAnimalService animalService;

    public VaccineManager(VaccineRepo vaccineRepo, IModelMapperService modelMapperService, IAnimalService animalService) {
        this.vaccineRepo = vaccineRepo;
        this.modelMapperService = modelMapperService;
        this.animalService = animalService;
    }

    @Override
    public Vaccine get(int id) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public ResultData<VaccineResponse> save(VaccineSaveRequest vaccineSaveRequest) {
        List<Vaccine> existVaccines = this.findByCodeAndName(vaccineSaveRequest.getCode(), vaccineSaveRequest.getName());

        Animal animal = this.animalService.get(vaccineSaveRequest.getAnimalId());
        vaccineSaveRequest.setAnimalId(null);

        Vaccine saveVaccine = this.modelMapperService.forRequest().map(vaccineSaveRequest, Vaccine.class);
        saveVaccine.setAnimal(animal);
        if (!existVaccines.isEmpty() && existVaccines.get(0).getProtectionFnshDate().isAfter(LocalDate.now())){
            return ResultHelper.error("Aynı koda sahip aşının bitiş tarihi bitmemiş! ");
        }
        if (!existVaccines.isEmpty()){
            throw new DataAlreadyExistException(Msg.getEntityForMsg(Vaccine.class));
        }
        return ResultHelper.created(this.modelMapperService.forResponse().map(this.vaccineRepo.save(saveVaccine), VaccineResponse.class));
    }

    @Override
    public ResultData<VaccineResponse> update(VaccineUpdateRequest vaccineUpdateRequest) {
        return null;
    }

    @Override
    public ResultData<CursorResponse<VaccineResponse>> cursor(int page, int pageSize) {
        return null;
    }

    @Override
    public ResultData<List<VaccineResponse>> findByAnimalId(int id) {
        return null;
    }

    @Override
    public ResultData<List<VaccineResponse>> findByDate(LocalDate entryDate, LocalDate exitDate) {
        return null;
    }

    @Override
    public List<Vaccine> findByCodeAndName(String code, String name) {
        return List.of();
    }
}
