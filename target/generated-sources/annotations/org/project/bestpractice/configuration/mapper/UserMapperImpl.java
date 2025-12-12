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
    date = "2025-12-12T04:18:13+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntityFromResponse(UserResponse Response) {
        if ( Response == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstname( Response.getFirstname() );
        user.lastname( Response.getLastname() );
        user.email( Response.getEmail() );
        user.active( Response.isActive() );
        user.locked( Response.isLocked() );

        return user.build();
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
        userResponse.active( entity.isActive() );
        userResponse.locked( entity.isLocked() );

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

        User.UserBuilder user = User.builder();

        user.firstname( dto.getFirstname() );
        user.lastname( dto.getLastname() );
        user.email( dto.getEmail() );
        user.password( dto.getPassword() );

        return user.build();
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
