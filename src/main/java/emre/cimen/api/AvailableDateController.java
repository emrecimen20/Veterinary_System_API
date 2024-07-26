package emre.cimen.api;

import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.result.Result;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.availableDate.AvailableDateSaveRequest;
import emre.cimen.dto.request.availableDate.AvailableDateUpdateRequest;
import emre.cimen.dto.response.availableDate.AvailableDateResponse;
import emre.cimen.entities.AvailableDate;
import emre.cimen.service.abstracts.IAvailableDateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/availabledates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    private final IModelMapperService modelMapperService;


    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService modelMapperService) {
        this.availableDateService = availableDateService;
        this.modelMapperService = modelMapperService;
    }

    // Adding a record of available days.
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest){
        return this.availableDateService.save(availableDateSaveRequest);
    }
    // Displaying available days by ID.
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable int id){
        AvailableDate availableDate = this.availableDateService.get(id);
        AvailableDateResponse availableDateResponse = this.modelMapperService.forResponse().map(availableDate, AvailableDateResponse.class);
        return ResultHelper.success(availableDateResponse);
    }

    // Updating available days.
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest){
        return this.availableDateService.update(availableDateUpdateRequest);
    }

    // Deleting available days.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable int id){
        this.availableDateService.delete(id);
        return ResultHelper.ok();
    }

    // Displaying all available days.
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return this.availableDateService.cursor(page, pageSize);
    }
}
