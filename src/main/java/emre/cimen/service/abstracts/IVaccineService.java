package emre.cimen.service.abstracts;

import emre.cimen.core.result.ResultData;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.vaccine.VaccineSaveRequest;
import emre.cimen.dto.request.vaccine.VaccineUpdateRequest;
import emre.cimen.dto.response.vaccine.VaccineResponse;
import emre.cimen.entities.Vaccine;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {
    Vaccine get(int id);
    boolean delete(int id);
    ResultData<VaccineResponse> save(VaccineSaveRequest vaccineSaveRequest);
    ResultData<VaccineResponse> update(VaccineUpdateRequest vaccineUpdateRequest);
    ResultData<CursorResponse<VaccineResponse>> cursor(int page, int pageSize);
    ResultData<List<VaccineResponse>> findByAnimalId(int id);
    ResultData<List<VaccineResponse>> findByDate(LocalDate entryDate, LocalDate exitDate);
    List<Vaccine> findByCodeAndName(String code, String name);
}
