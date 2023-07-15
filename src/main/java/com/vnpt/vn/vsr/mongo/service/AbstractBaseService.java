package com.vnpt.vn.vsr.mongo.service;

import com.vnpt.vn.vsr.mongo.model.AbstractBaseEntity;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface AbstractBaseService <T extends AbstractBaseEntity, ID extends Serializable> {

    public abstract T save(T entity);

    public abstract List<T> saveAll(List<T> entity);

    public abstract List<T> findAll();

    public abstract List<T> findAllBySort(Sort sort);

    public abstract Optional<T> findById(ID entityId);

    public abstract T update(T entity);

    public abstract T updateById(T entity, ID entityId);

    public abstract void delete(T entity);

    public abstract void deleteById(ID entityId);
}
