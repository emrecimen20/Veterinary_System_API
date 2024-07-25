package emre.cimen.service.abstracts;

import emre.cimen.core.result.ResultData;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.doctor.DoctorSaveRequest;
import emre.cimen.dto.request.doctor.DoctorUpdateRequest;
import emre.cimen.dto.response.doctor.DoctorResponse;
import emre.cimen.entities.Doctor;

import java.time.LocalDate;
import java.util.List;

public interface IDoctorService {
    Doctor get(int id);
    boolean delete(int id);
    ResultData<DoctorResponse> save(DoctorSaveRequest doctorSaveRequest);
    ResultData<DoctorResponse> update(DoctorUpdateRequest doctorUpdateRequest);
    ResultData<CursorResponse<DoctorResponse>> cursor(int page, int pageSize);
    List<Doctor> findByNameAndMailAndPhone(String name, String mail, String phone);
    List<Doctor> findByIdAndAvailableDateDate(int id, LocalDate localDate);

}
