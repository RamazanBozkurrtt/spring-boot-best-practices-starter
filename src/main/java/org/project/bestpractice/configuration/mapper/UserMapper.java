package org.project.bestpractice.configuration.mapper;

import org.mapstruct.Mapper;
import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserResponse, UserRequest>{

}
