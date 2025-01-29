package com.intellij.EMS.controller;

import com.intellij.EMS.apiresponse.APIResponse;
import com.intellij.EMS.model.EmployeeDTO;
import com.intellij.EMS.service.EmployeeDAO;
import io.swagger.models.Response;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

        @Autowired
        private EmployeeDAO employeeDAO;

        @Autowired
        private ModelMapper modelMapper;

        @GetMapping
        public ResponseEntity<List<EmployeeDTO>> findAll() {
            List<EmployeeDTO> list = this.employeeDAO.findAll();
            return new ResponseEntity<List<EmployeeDTO>>(list, HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<APIResponse> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
            EmployeeDTO create = employeeDAO.createEmployee(employeeDTO);
            return new ResponseEntity<APIResponse>(new APIResponse("Employee added succesfully..!", true),
                    HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<APIResponse> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO,
                                                          @PathVariable Long id) {
            employeeDAO.updateEmployee(employeeDTO, id);
            return new ResponseEntity<APIResponse>(new APIResponse("Employee updated succesfully!", true), HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
            EmployeeDTO employeeById = employeeDAO.getEmployeeById(id);
            if (employeeById != null) {
                return new ResponseEntity<EmployeeDTO>(employeeById, HttpStatus.OK);
            } else {
                return new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_FOUND);
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<APIResponse> deleteEmployeeById(@Valid @PathVariable Long id) {
            employeeDAO.deleteEmployeeById(id);
            return new ResponseEntity<APIResponse>(new APIResponse("Employee deleted succesfully!", true), HttpStatus.OK);
        }

}
