package cz.muni.fi.pa165.service;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    private final Mapper dozer;

    @Inject
    public BeanMappingServiceImpl(Mapper dozer) {
        this.dozer = dozer;
    }

    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(mapTo(object, mapToClass));
        }
        return mappedCollection;
    }

    public <T> T mapTo(Object u, Class<T> mapToClass) {
        if (u == null) {
            return null;
        }
        return dozer.map(u, mapToClass);
    }

    public Mapper getMapper() {
        return dozer;
    }
}
