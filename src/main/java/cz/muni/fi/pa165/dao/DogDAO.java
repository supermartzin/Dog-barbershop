package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Dog;
import cz.muni.fi.pa165.entities.Dog;

import java.util.List;

/**
 * Data access object for retrieving Dog objects from database.
 *
 * @author Martin Vrábel
 * @version 23.10.2016 20:35
 */
public interface DogDAO {

    /**
     * Creates new entry in database from provided {@link Dog} object
     *
     * @param dog  {@link Dog} object to save
     */
    void create(Dog dog);

    /**
     * Retrieves a {@see Dog} object with provided <b>ID</b> from database
     *
     * @param id    <b>ID</b> number of {@link Dog} to retrieve
     * @return      found {@link Dog} object or {@link null} if <b>ID</b> not found
     */
    Dog getById(long id);

    /**
     * Retrieves all {@link Dog} objects from database
     *
     * @return  list of all {@link Dog} objects from database
     */
    List<Dog> getAll();

    /**
     * Updates attributes of an existing {@link Dog} object in database
     *
     * @param dog  {@link Dog} object with updated attributes
     */
    void update(Dog dog);

    /**
     * Deletes an existing {@link Dog} entry from database
     *
     * @param dog  {@link Dog} object to delete from database
     */
    void delete(Dog dog);
}
