package emre.cimen.core.config;

import emre.cimen.core.config.modelMapper.IModelMapperService;
import emre.cimen.core.exception.NotFoundException;
import emre.cimen.core.utilies.Msg;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
public class ConvertEntityToResponse<E, RESPONSE> {
    private final IModelMapperService modelMapperService;

    // In order not to keep retyping, I used this method to convert the List type entity to response list type.
    public List<RESPONSE> convertToResponseList(List<E> entityList, Class<RESPONSE> responseClass) {
        if (entityList.isEmpty()){
            throw new NotFoundException(Msg.NOT_FOUND);

        } else {
            return entityList.stream()
                    .map(entity -> modelMapperService.forResponse().map(entity, responseClass))
                    .collect(Collectors.toList());
        }
    }
}
