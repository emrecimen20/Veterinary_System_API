package emre.cimen.service.concretes;

import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.exception.DataAlreadyExistException;
import emre.cimen.core.exception.NotFoundException;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.Msg;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.availableDate.AvailableDateSaveRequest;
import emre.cimen.dto.request.availableDate.AvailableDateUpdateRequest;
import emre.cimen.dto.response.availableDate.AvailableDateResponse;
import emre.cimen.entities.AvailableDate;
import emre.cimen.entities.Doctor;
import emre.cimen.repositories.AvailableDateRepo;
import emre.cimen.service.abstracts.IAvailableDateService;
import emre.cimen.service.abstracts.IDoctorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableDateManager implements IAvailableDateService {

    private final AvailableDateRepo availableDateRepo;
    private final IModelMapperService modelMapperService;
    private final IDoctorService doctorService;

    public AvailableDateManager(AvailableDateRepo availableDateRepo, IModelMapperService modelMapperService, IDoctorService doctorService) {
        this.availableDateRepo = availableDateRepo;
        this.modelMapperService = modelMapperService;
        this.doctorService = doctorService;
    }

    @Override
    public AvailableDate get(int id) {
        return this.availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(int id) {
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }

    @Override
    public ResultData<AvailableDateResponse> save(AvailableDateSaveRequest availableDateSaveRequest) {
        List<AvailableDate> availableDateList = this.availableDateRepo.findByDateAndDoctorId(
                availableDateSaveRequest.getDate(),
                availableDateSaveRequest.getDoctorId());
        if (!availableDateList.isEmpty()) {
            throw new DataAlreadyExistException(Msg.getEntityForMsg(AvailableDate.class));
        }

        Doctor doctor = this.doctorService.get(availableDateSaveRequest.getDoctorId());
        availableDateSaveRequest.setDoctorId(null);

        AvailableDate saveAvailableDate = this.modelMapperService.forRequest().map(availableDateSaveRequest, AvailableDate.class);
        saveAvailableDate.setDoctor(doctor);
        return ResultHelper.created(this.modelMapperService.forResponse().map(this.availableDateRepo.save(saveAvailableDate), AvailableDateResponse.class));
    }

    @Override
    public ResultData<AvailableDateResponse> update(AvailableDateUpdateRequest availableDateUpdateRequest) {
        this.get(availableDateUpdateRequest.getId());
        AvailableDate updateAvailableDate = this.modelMapperService.forResponse().map(availableDateUpdateRequest, AvailableDate.class);
        return ResultHelper.success(this.modelMapperService.forResponse().map(this.availableDateRepo.save(updateAvailableDate), AvailableDateResponse.class));
    }

    @Override
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AvailableDate> availableDatePage = this.availableDateRepo.findAll(pageable);
        Page<AvailableDateResponse> availableDateResponsePage = availableDatePage.map(availableDate -> this.modelMapperService.forResponse().map(availableDate, AvailableDateResponse.class));
        return ResultHelper.cursor(availableDateResponsePage);
    }
}
