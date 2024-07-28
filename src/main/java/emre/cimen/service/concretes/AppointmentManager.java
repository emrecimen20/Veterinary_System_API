package emre.cimen.service.concretes;

import emre.cimen.core.config.ConvertEntityToResponse;
import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.exception.DataAlreadyExistException;
import emre.cimen.core.exception.NotFoundException;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.Msg;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.appointment.AppointmentSaveRequest;
import emre.cimen.dto.request.appointment.AppointmentUpdateRequest;
import emre.cimen.dto.response.appointment.AppointmentResponse;
import emre.cimen.entities.Animal;
import emre.cimen.entities.Appointment;
import emre.cimen.entities.Doctor;
import emre.cimen.repositories.AppointmentRepo;
import emre.cimen.service.abstracts.IAnimalService;
import emre.cimen.service.abstracts.IAppointmentsService;
import emre.cimen.service.abstracts.IDoctorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentManager implements IAppointmentsService {
    private final AppointmentRepo appointmentRepo;
    private final IAnimalService animalService;
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapperService;
    private final ConvertEntityToResponse<Appointment, AppointmentResponse> convert;

    public AppointmentManager(AppointmentRepo appointmentRepo, IAnimalService animalService, IDoctorService doctorService, IModelMapperService modelMapperService, ConvertEntityToResponse<Appointment, AppointmentResponse> convert) {
        this.appointmentRepo = appointmentRepo;
        this.animalService = animalService;
        this.doctorService = doctorService;
        this.modelMapperService = modelMapperService;
        this.convert = convert;
    }

    @Override
    public Appointment get(int id) {
        return this.appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(int id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    @Override
    public ResultData<AppointmentResponse> save(AppointmentSaveRequest appointmentSaveRequest) {
        LocalDateTime dateTime = appointmentSaveRequest.getDateTime();
        if (dateTime.getMinute() != 0) {
            return ResultHelper.error("Please enter minute information as '00'.");
        }

        Optional<Appointment> appointmentOptional = this.findByValueForValid(
                appointmentSaveRequest.getDateTime(),
                appointmentSaveRequest.getDoctorId(),
                appointmentSaveRequest.getAnimalId()
        );

        if (appointmentOptional.isPresent()) {
            throw new DataAlreadyExistException(Msg.getEntityForMsg(Appointment.class));
        }

        Animal animal = this.animalService.get(appointmentSaveRequest.getAnimalId());
        Doctor doctor = this.doctorService.get(appointmentSaveRequest.getDoctorId());

        List<Doctor> doctorList = this.doctorService.findByIdAndAvailableDateDate(appointmentSaveRequest.getDoctorId(), LocalDate.from(dateTime));
        List<Appointment> appointmentByDate = this.findByDateTime(dateTime);

        appointmentSaveRequest.setAnimalId(null);
        appointmentSaveRequest.setDateTime(null);
        appointmentSaveRequest.setDoctorId(null);

        Appointment saveAppointment = this.modelMapperService.forRequest().map(appointmentSaveRequest, Appointment.class);
        saveAppointment.setAnimal(animal);
        saveAppointment.setDoctor(doctor);
        saveAppointment.setDateTime(dateTime);

       //  Doctor's availability check when booking an appointment.
        if (doctorList.isEmpty()) {
            return ResultHelper.error("The doctor is not available on this date.");
        } else if (!appointmentByDate.isEmpty()) {
            return ResultHelper.error("The doctor has an appointment at this time.");
        } else {
            return ResultHelper.created(this.modelMapperService.forResponse().map(this.appointmentRepo.save(saveAppointment), AppointmentResponse.class));
        }
    }

    @Override
    public ResultData<AppointmentResponse> update(AppointmentUpdateRequest appointmentUpdateRequest) {
        this.get(appointmentUpdateRequest.getId());
        Appointment updateAppointment = this.modelMapperService.forRequest().map(appointmentUpdateRequest, Appointment.class);
        return ResultHelper.success(this.modelMapperService.forResponse().map(this.appointmentRepo.save(updateAppointment), AppointmentResponse.class));
    }

    @Override
    public ResultData<CursorResponse<AppointmentResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Appointment> appointmentPage =  this.appointmentRepo.findAll(pageable);
        Page<AppointmentResponse> appointmentResponsePage = appointmentPage.map(appointment -> this.modelMapperService.forResponse().map(appointment, AppointmentResponse.class));
        return ResultHelper.cursor(appointmentResponsePage);
    }

    @Override
    public List<Appointment> findByDateTime(LocalDateTime localDateTime) {
        return this.appointmentRepo.findByDateTime(localDateTime);
    }

    @Override
    public Optional<Appointment> findByValueForValid(LocalDateTime dateTime, Integer doctorId, Integer animalId) {
        return this.appointmentRepo.findByDateTimeAndDoctorIdAndAnimalId(dateTime, doctorId, animalId);
    }

    @Override
    public ResultData<List<AppointmentResponse>> findByDoctorIdAndDateTimeBetween(int id, LocalDate entryDate, LocalDate exitDate) {
        LocalDateTime convertedEntryDate = entryDate.atStartOfDay();
        LocalDateTime convertedExitDate = exitDate.atStartOfDay().plusDays(1);
        List<Appointment> appointmentList = this.appointmentRepo.findByDoctorIdAndDateTimeBetween(id, convertedEntryDate, convertedExitDate);
        List<AppointmentResponse> appointmentResponseList = this.convert.convertToResponseList(appointmentList, AppointmentResponse.class);
        return ResultHelper.success(appointmentResponseList);
    }

    @Override
    public ResultData<List<AppointmentResponse>> findByAnimalIdAndDateTimeBetween(int id, LocalDate entryDate, LocalDate exitDate) {
        LocalDateTime convertedEntryDate = entryDate.atStartOfDay();
        LocalDateTime convertedExitDate = exitDate.atStartOfDay().plusDays(1);
        List<Appointment> appointmentList = this.appointmentRepo.findByAnimalIdAndDateTimeBetween(id, convertedEntryDate, convertedExitDate);
        List<AppointmentResponse> appointmentResponseList = this.convert.convertToResponseList(appointmentList, AppointmentResponse.class);
        return ResultHelper.success(appointmentResponseList);
    }
}
