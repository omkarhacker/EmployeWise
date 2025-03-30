package com.example.EmployeWise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.EmployeWise.dto.EmployeeDTO;
import com.example.EmployeWise.model.Employee;
import com.example.EmployeWise.repository.EmployeeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public String addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID().toString());
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setEmail(employeeDTO.getEmail());
        employee.setReportsTo(employeeDTO.getReportsTo());
        employee.setProfileImage(employeeDTO.getProfileImage());

        employeeRepository.save(employee);
        return employee.getId();
    }

    public Page<Employee> getAllEmployees(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return employeeRepository.findAll(pageable);
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(String id, EmployeeDTO employeeDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new NoSuchElementException("Employee not found with id: " + id);
        }

        Employee employee = optionalEmployee.get();
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setEmail(employeeDTO.getEmail());
        employee.setReportsTo(employeeDTO.getReportsTo());
        employee.setProfileImage(employeeDTO.getProfileImage());

        return employeeRepository.save(employee);
    }

    public Employee getNthLevelManager(String employeeId, int level) {
        if (level <= 0) {
            throw new IllegalArgumentException("Level must be greater than 0");
        }
    
        Employee currentEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + employeeId));
    
        // Use a separate variable for iteration
        Employee current = currentEmployee;
        for (int i = 0; i < level; i++) {
            if (current.getReportsTo() == null || current.getReportsTo().isEmpty()) {
                throw new NoSuchElementException("No manager found at level " + (i + 1));
            }
            String managerId = current.getReportsTo();
            current = employeeRepository.findById(managerId)
                    .orElseThrow(() -> new NoSuchElementException("Manager not found with id: " + managerId));
        }
    
        return current;
    }
}
