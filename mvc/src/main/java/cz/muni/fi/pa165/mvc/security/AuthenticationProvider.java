package cz.muni.fi.pa165.mvc.security;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.CustomerFacade;
import cz.muni.fi.pa165.facade.EmployeeFacade;
import cz.muni.fi.pa165.mvc.controllers.ExceptionHandlingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Provider class for performing system users authentiction
 *
 * @author Martin Vrábel
 * @version 16.01.2017 11:05
 */
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    private final CustomerFacade customerFacade;
    private final EmployeeFacade employeeFacade;

    public AuthenticationProvider(CustomerFacade customerFacade, EmployeeFacade employeeFacade) {
        if (customerFacade == null)
            throw new IllegalArgumentException("CustomerFacade is null");
        if (employeeFacade == null)
            throw new IllegalArgumentException("EmployeeFacade is null");

        this.customerFacade = customerFacade;
        this.employeeFacade = employeeFacade;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomerDTO customer = findCustomer(username);
        if (customer != null && customer.getPassword().equals(password)) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

            return new UserAuthentication(customer, grantedAuthorities);
        } else {
            EmployeeDTO employee = findEmployee(username);
            if (employee != null && employee.getPassword().equals(password)) {
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

                if (employee.getUsername().equals("administrator"))
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                else
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));

                return new UserAuthentication(employee, grantedAuthorities);
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }


    private CustomerDTO findCustomer(String username) {
        try {
            return customerFacade.getByUsername(username);
        } catch (FacadeException fEx) {
            LOGGER.error("Customer with username '" + username + "' has not been found.", fEx);
            return null;
        }
    }

    private EmployeeDTO findEmployee(String username) {
        try {
            return employeeFacade.getByUsername(username);
        } catch (FacadeException fEx) {
            LOGGER.error("Employee with username '" + username + "' has not been found.", fEx);
            return null;
        }
    }
}
