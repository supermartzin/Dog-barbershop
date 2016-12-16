package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.EmployeeDTO;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.facade.EmployeeFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;

/**
 * @author Denis Richtarik
 * @version 16.12.2016 19:40
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Inject
    private EmployeeFacade employeeFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws FacadeException {
        model.addAttribute("employee", employeeFacade.getAll());
        return "employee/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) throws FacadeException {
        model.addAttribute("employee", employeeFacade.getById(id));
        return "employee/detail";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newEmployee(Model model) throws FacadeException{
        model.addAttribute("employeeCreate", new EmployeeDTO());
        return "employee/new";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) throws FacadeException {
        EmployeeDTO orderDTO = employeeFacade.getById(id);
        employeeFacade.delete(orderDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Employee \"" + orderDTO.getId() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/employee/list").toUriString();
    }


}
