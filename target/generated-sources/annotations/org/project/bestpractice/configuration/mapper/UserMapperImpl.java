package org.project.bestpractice.configuration.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.project.bestpractice.dto.request.UserRequest;
import org.project.bestpractice.dto.response.UserResponse;
import org.project.bestpractice.entities.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-10T17:33:45+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntityFromResponse(UserResponse Response) {
        if ( Response == null ) {
            return null;
        }

        User user = new User();

        user.setId( Response.getId() );
        user.setFirstname( Response.getFirstname() );
        user.setLastname( Response.getLastname() );
        user.setEmail( Response.getEmail() );

        return user;
    }

    @Override
    public UserResponse toResponseFromEntity(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( entity.getId() );
        userResponse.firstname( entity.getFirstname() );
        userResponse.lastname( entity.getLastname() );
        userResponse.email( entity.getEmail() );

        return userResponse.build();
    }

    @Override
    public UserResponse toResponseFromRequest(UserRequest dto) {
        if ( dto == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.firstname( dto.getFirstname() );
        userResponse.lastname( dto.getLastname() );
        userResponse.email( dto.getEmail() );

        return userResponse.build();
    }

    @Override
    public User toEntityFromRequest(UserRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setFirstname( dto.getFirstname() );
        user.setLastname( dto.getLastname() );
        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );

        return user;
    }

    @Override
    public List<UserResponse> toResponseListFromEntityList(List<User> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<UserResponse> list = new ArrayList<UserResponse>( dtos.size() );
        for ( User user : dtos ) {
            list.add( toResponseFromEntity( user ) );
        }

        return list;
    }
}
