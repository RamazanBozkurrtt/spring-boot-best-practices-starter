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
    date = "2025-12-09T18:43:29+0300",
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
        user.setUsername( Response.getUsername() );
        user.setEmail( Response.getEmail() );
        user.setCreatedAt( Response.getCreatedAt() );

        return user;
    }

    @Override
    public UserResponse toResponseFromEntity(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( entity.getId() );
        userResponse.username( entity.getUsername() );
        userResponse.email( entity.getEmail() );
        userResponse.createdAt( entity.getCreatedAt() );

        return userResponse.build();
    }

    @Override
    public UserResponse toResponseFromRequest(UserRequest dto) {
        if ( dto == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.username( dto.getUsername() );
        userResponse.email( dto.getEmail() );

        return userResponse.build();
    }

    @Override
    public User toEntityFromRequest(UserRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.getUsername() );
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
