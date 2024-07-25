package emre.cimen.api;

import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.result.Result;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.doctor.DoctorSaveRequest;
import emre.cimen.dto.request.doctor.DoctorUpdateRequest;
import emre.cimen.dto.response.doctor.DoctorResponse;
import emre.cimen.entities.Doctor;
import emre.cimen.service.abstracts.IDoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/doctors")
public class DoctorController {
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapperService;

    public DoctorController(IDoctorService doctorService, IModelMapperService modelMapperService) {
        this.doctorService = doctorService;
        this.modelMapperService = modelMapperService;
    }

    // Registration of doctor.
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest){
        return this.doctorService.save(doctorSaveRequest);
    }

    // Call doctor by ID.
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get(@PathVariable int id){
        Doctor doctor = this.doctorService.get(id);
        DoctorResponse doctorResponse = this.modelMapperService.forResponse().map(doctor, DoctorResponse.class);
        return ResultHelper.success(doctorResponse);
    }

    // Call all doctors.
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return this.doctorService.cursor(page, pageSize);
    }

    // Update doctor by ID.
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> update(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest){
        return this.doctorService.update(doctorUpdateRequest);
    }

    // Delete doctor by ID.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id){
        this.doctorService.delete(id);
        return ResultHelper.ok();
    }
}
