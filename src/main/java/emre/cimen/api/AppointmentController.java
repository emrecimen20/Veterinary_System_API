package emre.cimen.api;

import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.result.Result;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.appointment.AppointmentSaveRequest;
import emre.cimen.dto.request.appointment.AppointmentUpdateRequest;
import emre.cimen.dto.response.appointment.AppointmentResponse;
import emre.cimen.entities.Appointment;
import emre.cimen.service.abstracts.IAppointmentsService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final IAppointmentsService appointmentService;
    private final IModelMapperService modelMapperService;

    public AppointmentController(IAppointmentsService appointmentService, IModelMapperService modelMapperService) {
        this.appointmentService = appointmentService;
        this.modelMapperService = modelMapperService;
    }

    // Registeration appointment operation.
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest){
        return this.appointmentService.save(appointmentSaveRequest);
    }

    // Display appointment by ID.
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") int id){
        Appointment appointment = this.appointmentService.get(id);
        AppointmentResponse appointmentResponse = this.modelMapperService.forResponse().map(appointment,AppointmentResponse.class);
        return ResultHelper.success(appointmentResponse);
    }

    // Deleting appointment operations.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable int id){
        this.appointmentService.delete(id);
        return ResultHelper.ok();
    }

    // Updating appointment operations.
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest){
        return this.appointmentService.update(appointmentUpdateRequest);
    }

    // Display all appointments.
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return this.appointmentService.cursor(page, pageSize);
    }

    // Appointments are displayed according to the date range and doctor entered by the user.
    @GetMapping("/filterByDrDate/{doctorId}")
    public ResultData<List<AppointmentResponse>> getDoctorIdAndDate(
            @PathVariable("doctorId") int id,
            @RequestParam(name = "entryDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate entryDate,
            @RequestParam(name = "exitDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate exitDate
    ){
        return this.appointmentService.findByDoctorIdAndDateTimeBetween(id,entryDate,exitDate);
    }

    // Appointments are filtered by user-entered date range and animal.
    @GetMapping("/filterByAnmlDate/{animalId}")
    public ResultData<List<AppointmentResponse>> getAnimalIdAndDate(
            @PathVariable("animalId") int id,
            @RequestParam(name = "entryDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate entryDate,
            @RequestParam(name = "exitDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate exitDate
    ){
        return this.appointmentService.findByAnimalIdAndDateTimeBetween(id, entryDate, exitDate);
    }
}
