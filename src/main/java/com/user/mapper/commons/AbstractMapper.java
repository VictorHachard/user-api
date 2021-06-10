package com.user.mapper.commons;

import com.user.dto.commons.Dto;
import com.user.model.entities.commmons.AbstractEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

// Lombok
@FieldDefaults(level = AccessLevel.PROTECTED)
@Log
public abstract class AbstractMapper<T, E> {

    public List<T> getAllDto(List<E> aList) {
        List<T> res = new ArrayList<>();
        aList.forEach(a -> {
            res.add(this.getDto(a));
        });
        return res;
    }

    public T getDto(E e) {
        AbstractEntity ae = (AbstractEntity) e;
        Dto obj = null;
        try {
            Object instance = ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
            obj = (Dto) instance;
        } catch (Exception ex) {
            log.info("Dto " + ex.getMessage());
        }
        obj.setId(ae.getId());
        obj.setCreatedAt(ae.getCreatedAt());
        return (T) obj;
    }

}
