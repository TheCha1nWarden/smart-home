package smart.home.authservice.model.mapper;

import org.mapstruct.Mapper;
import smart.home.authservice.model.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDto map(User user);
}
