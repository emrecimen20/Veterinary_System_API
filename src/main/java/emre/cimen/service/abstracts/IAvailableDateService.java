package emre.cimen.service.abstracts;

import emre.cimen.core.result.ResultData;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.availableDate.AvailableDateSaveRequest;
import emre.cimen.dto.request.availableDate.AvailableDateUpdateRequest;
import emre.cimen.dto.response.availableDate.AvailableDateResponse;
import emre.cimen.entities.AvailableDate;

public interface IAvailableDateService {
    AvailableDate get(int id);
    boolean delete(int id);
    ResultData<AvailableDateResponse> save(AvailableDateSaveRequest availableDateSaveRequest);
    ResultData<AvailableDateResponse> update(AvailableDateUpdateRequest availableDateUpdateRequest);
    ResultData<CursorResponse<AvailableDateResponse>> cursor(int page, int pageSize);
}
