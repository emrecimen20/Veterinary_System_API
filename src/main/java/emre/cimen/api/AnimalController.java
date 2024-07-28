package emre.cimen.api;

import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.result.Result;
import emre.cimen.core.result.ResultData;
import emre.cimen.core.utilies.ResultHelper;
import emre.cimen.dto.CursorResponse;
import emre.cimen.dto.request.animal.AnimalSaveRequest;
import emre.cimen.dto.request.animal.AnimalUpdateRequest;
import emre.cimen.dto.response.animal.AnimalResponse;
import emre.cimen.entities.Animal;
import emre.cimen.service.abstracts.IAnimalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
    private final IAnimalService animalService;
    private final IModelMapperService modelMapperService;

    public AnimalController(IAnimalService animalService, IModelMapperService modelMapperService) {
        this.animalService = animalService;
        this.modelMapperService = modelMapperService;
    }

    // Recording animal information.
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest){
        return this.animalService.save(animalSaveRequest);
    }

    // Display animal by ID.
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable int id){
        Animal animal = this.animalService.get(id);
        AnimalResponse animalResponse = this.modelMapperService.forResponse().map(animal, AnimalResponse.class);
        return ResultHelper.success(animalResponse);
    }

    // Display animals by name.
    @GetMapping("/byName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> get(@PathVariable("name") String name){
        return this.animalService.findByName(name);
    }

    // Update animal information.
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest){
        return this.animalService.update(animalUpdateRequest);
    }

    // Display all animals.
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return this.animalService.cursor(page, pageSize);
    }

    // Deleting animal information.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable int id){
        this.animalService.delete(id);
        return ResultHelper.ok();
    }

    // Bringing all the animals of the entered animal owner in the system.
    @GetMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalsByCustomerId(@PathVariable("id") int customerId){
        return this.animalService.findByCustomerId(customerId);
    }
}
