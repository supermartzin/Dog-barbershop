package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.exceptions.ServiceException;

import java.util.List;

/**
 * Service for working with {@link Dog} objects.
 *
 * @author Dominik Gmiterko
 */
public interface DogService {

    /**
     * Creates new entry from provided {@link Dog} object.
     *
     * @param dog                       {@link Dog} object to save
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     * @throws ServiceException         if error occurs during creation of {@link Dog}
     */
    void create(Dog dog) throws ServiceException;

    /**
     * Retrieves a {@see Dog} object with provided <b>ID</b>.
     *
     * @param id                        <b>ID</b> number of {@link Dog} to retrieve
     * @return                          found {@link Dog} object or {@code null} if object with specified <b>ID</b> not found
     * @throws IllegalArgumentException when {@code id} is negative number
     * @throws ServiceException         if error occurs during retrieving of {@link Dog}
     */
    Dog getById(long id) throws ServiceException;

    /**
     * Retrieves all {@link Dog} objects.
     *
     * @return                  list with all {@link Dog} objects
     *                          or <b>empty list</b> if there is no entry in database
     * @throws ServiceException if error occurs during retrieving of {@link Dog}s
     */
    List<Dog> getAll() throws ServiceException;

    /**
     * Updates attributes of an existing {@link Dog} object.
     *
     * @param dog                       {@link Dog} object with updated attributes
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     * @throws ServiceException         if error occurs during updating {@link Dog}
     */
    void update(Dog dog) throws ServiceException;

    /**
     * Deletes an existing {@link Dog} entry.
     *
     * @param dog                       {@link Dog} object to delete
     * @throws IllegalArgumentException when {@code dog} is {@code null}
     * @throws ServiceException         if error occurs during deleting {@link Dog}
     */
    void delete(Dog dog) throws ServiceException;
}
