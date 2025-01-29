package com.intellij.EMS.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.intellij.EMS.exception.ResourceNotFoundException;
import com.intellij.EMS.model.Employee;
import com.intellij.EMS.model.EmployeeDTO;
import com.intellij.EMS.repository.EmployeeRepository;
import com.intellij.EMS.service.EmployeeDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<EmployeeDTO> findAll() {
        List<EmployeeDTO> listOfDtos = new ArrayList<EmployeeDTO>();
        List<Employee> listOfEmployees = this.employeeRepository.findAll();

        listOfEmployees.stream().map(e -> {
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
                    e.setSalary(Double.valueOf(df.format(e.getSalary())));
                    System.out.println(e.getSalary());
                    return e;
        }).map(s -> {
                    s.setSalary(s.getSalary()+10000);
                    return  s;
                }).map(m -> { 
                    String maskedAadhar = m.getAadharnumber().substring(0, m.getAadharnumber().length() - 4).replaceAll("[0-9]", "*")
                            + m.getAadharnumber().substring(m.getAadharnumber().length() - 4, m.getAadharnumber().length());
                    m.setAadharnumber(maskedAadhar); return  m;
        }).
                collect(Collectors.toList());

        for (Employee employee : listOfEmployees) {
            EmployeeDTO emp = this.modelMapper.map(employee, EmployeeDTO.class);
            System.out.println(emp);
            listOfDtos.add(emp);
        }

        //List<EmployeeDTO> listOfDtos = listOfEmployees.stream()
        //.map((list) -> this.modelMapper.map(list, EmployeeDTO.class)).collect(Collectors.toList());
        return listOfDtos;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDto) {

        Employee employee = new Employee();
        employee.setFirstname(employeeDto.getFirstname());
        employee.setLastname(employeeDto.getLastname());

        // Save the entity to the database
        employeeRepository.save(employee);

        // Set full name in DTO
        employeeDto.setEmployeename(employee.getFirstname() + " " + employee.getLastname());

        return employeeDto;
/*//			Employee employee = this.modelMapper.map(employeeDto, Employee.class);
//			Employee employee2 = this.employeeRepository.save(employee);
//			return this.modelMapper.map(employee2, EmployeeDTO.class);*/
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, Long employeeId) {

        Employee emp = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Employee Id", employeeId));
        //employeeDTO.setEmployeename(emp.getFirstname() + " " + emp.getLastname());
        emp.setSalary(employeeDTO.getSalary());
        emp.setCategory(employeeDTO.getCategory());
        emp.setGender(employeeDTO.getGender());
        emp.setMobilenumber(employeeDTO.getMobilenumber());
        emp.setAadharnumber(employeeDTO.getAadharnumber());

        Employee updatedemp = this.employeeRepository.save(emp);

        return this.modelMapper.map(updatedemp, EmployeeDTO.class);

        /*
         * // Optional<Employee> optional = employeeRepository.findById(id); // if
         * (optional.isPresent()) { // Employee employee2 = optional.get(); //
         * employee2.setEmployeename(employee.getEmployeename()); //
         * employee2.setSalary(employee.getSalary()); //
         * employee2.setCategory(employee.getCategory()); //
         * employee2.setGender(employee.getGender()); //
         * employee2.setMobilenumber(employee.getMobilenumber()); //
         * employeeRepository.save(employee2); // return true; // } // return null;
         */
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {

        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Employee Id", id));

        return this.modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Employee Id", id));

        this.employeeRepository.delete(employee);
    }

}
