package com.tutrit.tt.security.staff.controller;

import com.tutrit.tt.security.staff.bean.Department;
import com.tutrit.tt.security.staff.repository.DepartmentRepository;
import com.tutrit.tt.security.staff.securityconfig.acl.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {

    DepartmentRepository departmentRepository;
    PermissionService permissionService;

    @GetMapping("/department")
    public ModelAndView getDepartments() {
        ModelAndView mov = new ModelAndView();
        mov.addObject("title", "Departments");
        mov.setViewName("department_list");
        mov.addObject("departments", departmentRepository.findAll());
        return mov;
    }

    @GetMapping("/department/{id}")
    public ModelAndView getDepartmentById(@PathVariable Long id) {
        ModelAndView mov = new ModelAndView();
        mov.addObject("title", "Department");
        mov.setViewName("department_card");
        mov.addObject("department", departmentRepository.findById(id).get());
        return mov;
    }

    @GetMapping("/department/new")
    public ModelAndView saveDepartment() {
        ModelAndView mov = new ModelAndView();
        mov.addObject("title", "Department");
        mov.setViewName("department_card");
        mov.addObject("department", new Department());
        return mov;
    }
    @PostMapping("/department/new")
    public String createDepartment(@ModelAttribute Department department, Authentication authentication) {
        // TODO: 1/24/22 implement acl policy
        permissionService.addPermissionForUser(department, BasePermission.WRITE, authentication.getName());
        permissionService.addPermissionForUser(department, BasePermission.READ, authentication.getName());
        departmentRepository.save(department);
        return "redirect:/department";
    }


    @PostMapping("/department/{id}")
    public String saveDepartment(@ModelAttribute Department department, Authentication authentication) {
        // TODO: 1/24/22 implement acl policy
        permissionService.addPermissionForUser(department, BasePermission.WRITE, authentication.getName());
        permissionService.addPermissionForUser(department, BasePermission.READ, authentication.getName());
        departmentRepository.save(department);
        return "redirect:/department";
    }
}
