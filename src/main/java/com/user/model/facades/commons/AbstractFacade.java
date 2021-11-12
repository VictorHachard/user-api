package com.user.model.facades.commons;

import com.user.init.AbstractAutowire;
import com.user.model.entities.commons.AbstractEntity;
import lombok.extern.java.Log;

import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;

/**
 * This class is the base class for all the Facades.
 * @param <T>
 */
// Lombok
@Log
public abstract class AbstractFacade<T> extends AbstractAutowire {

    /**
     * This method return a new generic type of the entity.
     * The creation date is set to the current date.
     * @return
     */
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

}