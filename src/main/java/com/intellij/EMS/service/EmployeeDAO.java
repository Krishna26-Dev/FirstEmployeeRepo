package com.intellij.EMS.service;

import com.intellij.EMS.model.EmployeeDTO;

import java.util.List;

public interface EmployeeDAO {

	List<EmployeeDTO> findAll();

	EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

	EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, Long id);

	EmployeeDTO getEmployeeById(Long id);

	void deleteEmployeeById(Long id);


}	
