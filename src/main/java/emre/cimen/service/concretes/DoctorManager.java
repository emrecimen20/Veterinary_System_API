package emre.cimen.service.concretes;

import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.exception.DataAlreadyExistException;
import emre.cimen.core.exception.NotFoundException;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.Msg;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.doctor.DoctorSaveRequest;
import emre.cimen.dto.request.doctor.DoctorUpdateRequest;
import emre.cimen.dto.response.doctor.DoctorResponse;
import emre.cimen.entities.Doctor;
import emre.cimen.repositories.DoctorRepo;
import emre.cimen.service.abstracts.IDoctorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorManager implements IDoctorService {
    private final DoctorRepo doctorRepo;
    private final IModelMapperService modelMapperService;

    public DoctorManager(DoctorRepo doctorRepo, IModelMapperService modelMapperService) {
        this.doctorRepo = doctorRepo;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Doctor get(int id) {
        return doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(int id) {
        Doctor doctor = this.get(id);
        this.doctorRepo.delete(doctor);
        return true;
    }

    @Override
    public ResultData<DoctorResponse> save(DoctorSaveRequest doctorSaveRequest) {
        List<Doctor> doctorList = this.findByNameAndMailAndPhone(
                doctorSaveRequest.getName(),
                doctorSaveRequest.getMail(),
                doctorSaveRequest.getPhone()
        );
        if (!doctorList.isEmpty()){
            throw new DataAlreadyExistException(Msg.getEntityForMsg(Doctor.class));
        }
        if (doctorRepo.existsByMail(doctorSaveRequest.getMail())) {
            return ResultHelper.EmailExists();
        }
        if (doctorRepo.existsByPhone(doctorSaveRequest.getPhone())) {
            return ResultHelper.PhoneExists();
        }
        Doctor saveDoctor = this.modelMapperService.forRequest().map(doctorSaveRequest, Doctor.class);
        return ResultHelper.created(this.modelMapperService.forResponse().map(this.doctorRepo.save(saveDoctor), DoctorResponse.class));
    }

    @Override
    public ResultData<DoctorResponse> update(DoctorUpdateRequest doctorUpdateRequest) {
        this.get(doctorUpdateRequest.getId());
        Doctor updateDoctor = this.modelMapperService.forRequest().map(doctorUpdateRequest, Doctor.class);
        return ResultHelper.success(this.modelMapperService.forResponse().map(this.doctorRepo.save(updateDoctor), DoctorResponse.class));
    }

    @Override
    public ResultData<CursorResponse<DoctorResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Doctor> doctorPage =  this.doctorRepo.findAll(pageable);
        Page<DoctorResponse> doctorResponsePage = doctorPage.map(doctor -> this.modelMapperService.forResponse().map(doctor, DoctorResponse.class));
        return ResultHelper.cursor(doctorResponsePage);
    }

    @Override
    public List<Doctor> findByNameAndMailAndPhone(String name, String mail, String phone) {
        return this.doctorRepo.findByNameAndMailAndPhone(name, mail, phone);
    }

    @Override
    public List<Doctor> findByIdAndAvailableDateDate(int id, LocalDate localDate) {
        return this.doctorRepo.findByIdAndAvailableDateDate(id, localDate);
    }
}
