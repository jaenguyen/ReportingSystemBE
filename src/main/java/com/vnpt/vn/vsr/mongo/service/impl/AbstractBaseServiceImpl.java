package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.model.AbstractBaseEntity;
import com.vnpt.vn.vsr.mongo.repository.AbstractBaseRepository;
import com.vnpt.vn.vsr.mongo.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public abstract class AbstractBaseServiceImpl <T extends AbstractBaseEntity, ID extends Serializable>
        implements AbstractBaseService<T, ID> {

    private AbstractBaseRepository<T, ID> abstractBaseRepository;

    @Autowired
    public AbstractBaseServiceImpl(AbstractBaseRepository<T, ID> abstractBaseRepository) {
        this.abstractBaseRepository = abstractBaseRepository;
    }

    @Override
    public T save(T entity) {
        return abstractBaseRepository.save(entity);
    }

    @Override
    public List<T> saveAll(List<T> entity) {
        return abstractBaseRepository.saveAll(entity);
    }

    @Override
    public List<T> findAll() {
        return abstractBaseRepository.findAll();
    }

    @Override
    public List<T> findAllBySort(Sort sort) {
        return abstractBaseRepository.findAll(sort);
    }

    @Override
    public Optional<T> findById(ID entityId) {
        return abstractBaseRepository.findById(entityId);
    }

    @Override
    public T update(T entity) {
        return abstractBaseRepository.save(entity);
    }

    @Override
    public T updateById(T entity, ID entityId) {
        Optional<T> optional = abstractBaseRepository.findById(entityId);
        if (optional.isPresent()) {
            return abstractBaseRepository.save(entity);
        } else {
            return null;
        }
    }

    @Override
    public void delete(T entity) {
        abstractBaseRepository.delete(entity);
    }

    @Override
    public void deleteById(ID entityId) {
        abstractBaseRepository.deleteById(entityId);
    }
}
