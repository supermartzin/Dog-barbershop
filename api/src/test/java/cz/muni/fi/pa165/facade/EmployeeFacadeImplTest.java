package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.entities.Address;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.facade.EmployeeFacade;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.PersistenceUnit;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;

/**
 * Tests for {@link EmployeeFacadeImplTest} class
 *
 * @author Denis Richtarik
 * @version 25.10.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-configs/main-config.xml"})
public class EmployeeFacadeImplTest {

    @Inject
    private EmployeeFacade employeeFacade;

}