package org.project.bestpractice.controller;

import org.project.bestpractice.entities.RootEntity;

public class RestBaseController<T> {

    public <T> RootEntity<T> ok(T data){
        return RootEntity.ok(data);
    }

    public <T> RootEntity<T> error(T data){
        return RootEntity.error(data);
    }
}
