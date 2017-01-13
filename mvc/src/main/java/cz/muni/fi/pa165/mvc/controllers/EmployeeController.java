package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.EmployeeFacade;
import cz.muni.fi.pa165.mvc.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

/**
 * @author Denis Richtarik
 * @version 16.12.2016 19:40
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeFacade employeeFacade;

    public EmployeeController(EmployeeFacade employeeFacade) {
        if (employeeFacade == null)
            throw new IllegalArgumentException("EmployeeFacade is null");

        this.employeeFacade = employeeFacade;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws FacadeException {
        model.addAttribute("employees", employeeFacade.getAll());
        return "employee/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) throws FacadeException {

        EmployeeDTO employee = employeeFacade.getById(id);

        if(employee == null) {
            throw new ResourceNotFoundException("Eemployee not found!");
        }

        model.addAttribute("employee", employee);
        return "employee/detail";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newEmployee(Model model) throws FacadeException {
        model.addAttribute("employeeCreate", new EmployeeDTO());
        return "employee/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("employeeCreate") EmployeeDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) throws FacadeException {
        // in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }

            return "employee/new";
        }

        // create employee
        employeeFacade.create(formBean);

        // report success
        redirectAttributes.addFlashAttribute("alert_success", "Employee " + formBean.getId() + " was created");
        return "redirect:" + uriBuilder.path("/employee/list").toUriString();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        EmployeeDTO employee = employeeFacade.getById(id);

        if(employee == null) {
            throw new ResourceNotFoundException("Eemployee not found!");
        }

        employeeFacade.delete(employee);
        redirectAttributes.addFlashAttribute("alert_success", "Employee \"" + employee.getId() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/employee/list").toUriString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        EmployeeDTO employee = employeeFacade.getById(id);

        if(employee == null) {
            throw new ResourceNotFoundException("Employee not found!");
        }

        employeeFacade.update(employee);
        redirectAttributes.addFlashAttribute("alert_success", "Employee " + employee.getId() + " was updated.");
        return "redirect:" + uriBuilder.path("/employee/list").toUriString();
    }
}
