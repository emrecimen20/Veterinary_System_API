package emre.cimen.service.concretes;

import emre.cimen.core.config.ConvertEntityToResponse;
import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.exception.DataAlreadyExistException;
import emre.cimen.core.exception.NotFoundException;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.Msg;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.customer.CustomerSaveRequest;
import emre.cimen.dto.request.customer.CustomerUpdateRequest;
import emre.cimen.dto.response.customer.CustomerResponse;
import emre.cimen.entities.Customer;
import emre.cimen.repositories.CustomerRepo;
import emre.cimen.service.abstracts.ICustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerManager implements ICustomerService {
    private final IModelMapperService modelMapperService;
    private final CustomerRepo customerRepo;
    private final ConvertEntityToResponse<Customer, CustomerResponse> convert;

    public CustomerManager(IModelMapperService modelMapperService, CustomerRepo customerRepo, ConvertEntityToResponse<Customer, CustomerResponse> convert) {
        this.modelMapperService = modelMapperService;
        this.customerRepo = customerRepo;
        this.convert = convert;
    }

    @Override
    public ResultData<CustomerResponse> save(CustomerSaveRequest customerSaveRequest) {
        Customer saveCustomer = this.modelMapperService.forRequest().map(customerSaveRequest, Customer.class);
        List<Customer> getByNamePhoneMail = this.findByNameAndMailAndPhone(
                saveCustomer.getName(),
                saveCustomer.getMail(),
                saveCustomer.getPhone());
        if (!getByNamePhoneMail.isEmpty()){
            throw new DataAlreadyExistException(Msg.getEntityForMsg(Customer.class));
        }
        if (customerRepo.existsByMail(customerSaveRequest.getMail())) {
            return ResultHelper.EmailExists();
        }
        if (customerRepo.existsByPhone(customerSaveRequest.getPhone())) {
            return ResultHelper.PhoneExists();
        }

        return ResultHelper.created(this.modelMapperService.forResponse().map(this.customerRepo.save(saveCustomer), CustomerResponse.class));
    }

    @Override
    public Customer get(int id) {
        return this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public ResultData<CursorResponse<CustomerResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Customer> customerPage = this.customerRepo.findAll(pageable);
        Page<CustomerResponse> customerResponsePage = customerPage.map(customer -> this.modelMapperService.forResponse().map(customer, CustomerResponse.class));
        return ResultHelper.cursor(customerResponsePage);
    }

    @Override
    public ResultData<CustomerResponse> update(CustomerUpdateRequest customerUpdateRequest) {
        this.get(customerUpdateRequest.getId());
        Customer updateCustomer = this.modelMapperService.forRequest().map(customerUpdateRequest, Customer.class);
        return ResultHelper.success(this.modelMapperService.forResponse().map(this.customerRepo.save(updateCustomer), CustomerResponse.class));
    }

    @Override
    public ResultData<List<CustomerResponse>> findByName(String name) {
        List<Customer> customerList = this.customerRepo.findByName(name);
        List<CustomerResponse> customerResponseList = this.convert.convertToResponseList(customerList, CustomerResponse.class);
        return ResultHelper.success(customerResponseList);
    }

    @Override
    public List<Customer> findByNameAndMailAndPhone(String name, String mail, String phone) {
        return this.customerRepo.findByNameAndMailAndPhone(name, mail, phone);
    }

    @Override
    public boolean delete(int id) {
        Customer customer = this.get(id);
        this.customerRepo.delete(customer);
        return true;
    }
}
