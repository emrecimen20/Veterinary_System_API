package emre.cimen.api;

import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.result.Result;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.vaccine.VaccineSaveRequest;
import emre.cimen.dto.request.vaccine.VaccineUpdateRequest;
import emre.cimen.dto.response.vaccine.VaccineResponse;
import emre.cimen.entities.Vaccine;
import emre.cimen.service.abstracts.IVaccineService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;
    private final IModelMapperService modelMapperService;

    public VaccineController(IVaccineService vaccineService, IModelMapperService modelMapperService) {
        this.vaccineService = vaccineService;
        this.modelMapperService = modelMapperService;
    }

    // Registeration vaccine operation.
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest){
        return this.vaccineService.save(vaccineSaveRequest);
    }

    // Display vaccine by ID.
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") int id){
        Vaccine vaccine = this.vaccineService.get(id);
        VaccineResponse vaccineResponse = this.modelMapperService.forResponse().map(vaccine,VaccineResponse.class);
        return ResultHelper.success(vaccineResponse);
    }
    // Delete vaccine operation.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable int id){
        this.vaccineService.delete(id);
        return ResultHelper.ok();
    }

    // Updating vaccine operation.
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest){
        return this.vaccineService.update(vaccineUpdateRequest);
    }

    // Display vaccine get by animal ID.
    @GetMapping("/animal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccineByAnimalId(@PathVariable("id") int animalId){
        return this.vaccineService.findByAnimalId(animalId);
    }

    // Display vaccine by date.
    @GetMapping("/findByDate")
    public ResultData<List<VaccineResponse>> getVaccinesByDate(
            @RequestParam(name = "entryDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate entryDate,
            @RequestParam(name = "exitDate") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate exitDate
    ){
        return this.vaccineService.findByDate(entryDate,exitDate);
    }

    // Display all vaccine operation.
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return this.vaccineService.cursor(page, pageSize);
    }

}
