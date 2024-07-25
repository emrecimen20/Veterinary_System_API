package emre.cimen.service.abstracts;

import emre.cimen.core.result.ResultData;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.customer.CustomerSaveRequest;
import emre.cimen.dto.request.customer.CustomerUpdateRequest;
import emre.cimen.dto.response.customer.CustomerResponse;
import emre.cimen.entities.Customer;

import java.util.List;

public interface ICustomerService {
    ResultData<CustomerResponse> save(CustomerSaveRequest customerSaveRequest);
    Customer get(int id);
    ResultData<CursorResponse<CustomerResponse>> cursor(int page, int pageSize);
    ResultData<CustomerResponse> update(CustomerUpdateRequest customerUpdateRequest);
    ResultData<List<CustomerResponse>> findByName(String name);
    List<Customer> findByNameAndMailAndPhone(String name, String mail, String phone);
    boolean delete(int id);
}
