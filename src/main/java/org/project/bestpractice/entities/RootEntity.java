package org.project.bestpractice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RootEntity<T> {

    private boolean status;
    private String message;
    private T data;

    public static <T> RootEntity<T> ok(T data) {
        RootEntity<T> entity = new RootEntity<T>();
        entity.setStatus(true);
        entity.setMessage("success");
        entity.setData(data);
        return entity;
    }

    public static <T> RootEntity<T> error(T data) {
        RootEntity<T> entity = new RootEntity<T>();
        entity.setStatus(false);
        entity.setMessage("error");
        entity.setData(null);
        return entity;
    }

}
