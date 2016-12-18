package cz.muni.fi.pa165.sampleData;

import cz.muni.fi.pa165.exceptions.FacadeException;

/**
 * Populates database with sample data.
 *
 * @author Dominik Gmiterko
 */
public interface SampleDataLoadingFacade {

    /**
     * Creates sample data and entities in the system.
     *
     * @throws FacadeException  if error occurs during sample data creation
     */
    void loadData() throws FacadeException;
}
