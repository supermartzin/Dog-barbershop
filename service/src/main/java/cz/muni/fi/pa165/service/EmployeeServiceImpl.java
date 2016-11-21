package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entities.Employee;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.utils.Constants;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 *
 *
 * @author Denis Richtarik
 * @version 25.10.2016
 */
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public void create(Employee employee) {

    }

    @Override
    public Employee getById(long id) {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public Employee getByUsername(String username) {
        return null;
    }

    @Override
    public void update(Employee employee) {

    }

    @Override
    public void delete(Employee employee) {

    }
}
