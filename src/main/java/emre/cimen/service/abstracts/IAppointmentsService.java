package emre.cimen.service.abstracts;

import emre.cimen.core.result.ResultData;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.appointment.AppointmentSaveRequest;
import emre.cimen.dto.request.appointment.AppointmentUpdateRequest;
import emre.cimen.dto.response.appointment.AppointmentResponse;
import emre.cimen.entities.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IAppointmentsService {
    Appointment get(int id);
    boolean delete(int id);
    ResultData<AppointmentResponse> save(AppointmentSaveRequest appointmentSaveRequest);
    ResultData<AppointmentResponse> update(AppointmentUpdateRequest appointmentUpdateRequest);
    ResultData<CursorResponse<AppointmentResponse>> cursor(int page, int pageSize);
    List<Appointment> findByDateTime(LocalDateTime localDateTime);
    Optional<Appointment> findByValueForValid(LocalDateTime dateTime, Integer doctorId, Integer animalId);
    ResultData<List<AppointmentResponse>> findByDoctorIdAndDateTimeBetween(int id, LocalDate entryDate, LocalDate exitDate);
    ResultData<List<AppointmentResponse>> findByAnimalIdAndDateTimeBetween(int id, LocalDate entryDate, LocalDate exitDate);
}
