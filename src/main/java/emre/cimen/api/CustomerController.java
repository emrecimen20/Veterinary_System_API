package emre.cimen.api;

import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.result.Result;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.customer.CustomerSaveRequest;
import emre.cimen.dto.request.customer.CustomerUpdateRequest;
import emre.cimen.dto.response.customer.CustomerResponse;
import emre.cimen.entities.Customer;
import emre.cimen.service.abstracts.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;
    private final IModelMapperService modelMapperService;

    public CustomerController(ICustomerService customerService, IModelMapperService modelMapperService) {
        this.customerService = customerService;
        this.modelMapperService = modelMapperService;
    }

    // Registration of pet owner.
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest){
        return this.customerService.save(customerSaveRequest);
    }

    // Update of pet owner.
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest){
        return this.customerService.update(customerUpdateRequest);
    }

    // Showing pet owner by id.
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("id") int id ){
        Customer customer = this.customerService.get(id);
        CustomerResponse customerResponse = this.modelMapperService.forResponse().map(customer,CustomerResponse.class);
        return ResultHelper.success(customerResponse);
    }

    // Delete pet owner by id.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id){
        this.customerService.delete(id);
        return ResultHelper.ok();
    }

    // Call pet owners by name.
    @GetMapping("/byName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> get(@PathVariable("name") String name){
        return this.customerService.findByName(name);
    }

    // Show all pet owner.
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return this.customerService.cursor(page, pageSize);
    }


}
