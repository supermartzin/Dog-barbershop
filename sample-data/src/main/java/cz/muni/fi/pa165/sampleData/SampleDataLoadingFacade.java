package cz.muni.fi.pa165.sampleData;

import cz.muni.fi.pa165.exceptions.DAOException;

import java.io.IOException;

/**
 * Populates database with sample data.
 *
 * @author Dominik Gmiterko
 */
public interface SampleDataLoadingFacade {

    void loadData() throws DAOException;
}
