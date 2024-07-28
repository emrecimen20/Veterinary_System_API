package emre.cimen.api;

import emre.cimen.core.result.ResultData;
import emre.cimen.dto.request.vaccine.VaccineSaveRequest;
import emre.cimen.dto.response.vaccine.VaccineResponse;
import emre.cimen.service.abstracts.IVaccineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;

    public VaccineController(IVaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest){
        return this.vaccineService.save(vaccineSaveRequest);
    }
}
