package com.user.model.facades.commons;

import com.user.model.entities.commmons.AbstractEntity;
import lombok.extern.java.Log;

import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// Lombok
@Log
public abstract class AbstractFacade<T> {

    public T newInstance() {
        AbstractEntity obj = null;
        try {
            Object instance = ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
            obj = (AbstractEntity) instance;
        } catch (Exception e) {
            log.info("AbstractRepository " + e.getMessage());
        }
        obj.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return (T) obj;
    }

    public List<T> getList(List<T> list){
        return new ArrayList<>(list);
    }

}