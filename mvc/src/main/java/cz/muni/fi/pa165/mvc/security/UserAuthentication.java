package cz.muni.fi.pa165.mvc.security;

import cz.muni.fi.pa165.dto.CustomerDTO;
import cz.muni.fi.pa165.dto.EmployeeDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Custom Authentication object for providing more information about logged users
 *
 * @author Martin Vrábel
 * @version 16.01.2017 13:49
 */
public class UserAuthentication extends UsernamePasswordAuthenticationToken implements Authentication {

    private final long id;
    private final String firstName;
    private final String lastName;

    public UserAuthentication(CustomerDTO customerDTO) {
        super(customerDTO.getUsername(), customerDTO.getPassword());

        this.id = customerDTO.getId();
        this.firstName = customerDTO.getFirstName();
        this.lastName = customerDTO.getLastName();
    }

    public UserAuthentication(EmployeeDTO employeeDTO) {
        super(employeeDTO.getUsername(), employeeDTO.getPassword());

        this.id = employeeDTO.getId();
        this.firstName = employeeDTO.getFirstName();
        this.lastName = employeeDTO.getLastName();
    }

    public UserAuthentication(CustomerDTO customerDTO, Collection<? extends GrantedAuthority> authorities) {
        super(customerDTO.getUsername(), customerDTO.getPassword(), authorities);

        this.id = customerDTO.getId();
        this.firstName = customerDTO.getFirstName();
        this.lastName = customerDTO.getLastName();
    }

    public UserAuthentication(EmployeeDTO employeeDTO, Collection<? extends GrantedAuthority> authorities) {
        super(employeeDTO.getUsername(), employeeDTO.getPassword(), authorities);

        this.id = employeeDTO.getId();
        this.firstName = employeeDTO.getFirstName();
        this.lastName = employeeDTO.getLastName();
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
