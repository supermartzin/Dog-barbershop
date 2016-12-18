package cz.muni.fi.pa165.service;

import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
public class BeanMappingServiceImpl implements BeanMappingService {

    private final Mapper dozer;

    public BeanMappingServiceImpl(Mapper dozer) {
        this.dozer = dozer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        if (objects == null)
            return null;

        List<T> mappedCollection = new ArrayList<>();

        for (Object object : objects) {
            mappedCollection.add(mapTo(object, mapToClass));
        }

        return mappedCollection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T mapTo(Object object, Class<T> mapToClass) {
        if (object == null)
            return null;

        return dozer.map(object, mapToClass);
    }
}
