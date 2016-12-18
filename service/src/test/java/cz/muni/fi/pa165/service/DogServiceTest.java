package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.DogDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for correct contract implementation defined by {@link DogService} interface
 *
 * @author Martin Vrábel
 * @version 22.11.2016 13:34
 */
@RunWith(MockitoJUnitRunner.class)
public class DogServiceTest {

    @Mock
    private DogDAO dogDAO;

    private DogService dogService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        dogService = new DogServiceImpl(dogDAO);
    }

    @Test
    public void create() throws Exception {

    }

    @Test
    public void getById() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void getByCustomer() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }
}