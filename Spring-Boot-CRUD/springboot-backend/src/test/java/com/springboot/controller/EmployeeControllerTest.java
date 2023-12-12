package com.springboot.controller;

import com.springboot.exception.ResourceNotFoundException;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeControllerTest {

@Mock
private EmployeeRepository employeeRepository;

@InjectMocks
private EmployeeController employeeController;

@Test
public void getTestAllEmployees(){

    List<Employee> listEmployee=new ArrayList<>();

    final long id1=1;
    Employee employee1=new Employee();
    employee1.setId(id1);
    employee1.setEmailId("k@gmail.com");
    employee1.setFirstName("karan");
    employee1.setLastName("kanojiya");

    final long id2=2;
    Employee employee2=new Employee();
    employee2.setId(id2);
    employee2.setEmailId("p@gmail.com");
    employee2.setFirstName("prashant");
    employee2.setLastName("rodat");

    listEmployee.add(employee1);
    listEmployee.add(employee2);
    when(employeeRepository.findAll()).thenReturn(listEmployee);

    //Then

    ResponseEntity<List<Employee>> response=employeeController.getAllEmployees();

    assertEquals(HttpStatus.OK,response.getStatusCode());
    assertEquals(listEmployee.size(),response.getBody().size());

}

    @Test
    public void getEmployeeById_validId_returnEmployee(){

        //Given
        final long id=1;
        Employee employee=new Employee();
        employee.setId(id);
        employee.setEmailId("k@gmail.com");
        employee.setFirstName("karan");
        employee.setLastName("kanojiya");
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        //Then

        ResponseEntity<Employee> response=employeeController.getEmployeeById(id);


        verify(employeeRepository).findById(id);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(employee,response.getBody());

    }

    @Test
    public void testGetEmployeeByIdNotFound() {
        long id = 100L; // Assuming an ID that doesn't exist
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()->employeeController.getEmployeeById(id));
    }

    @Test
    public void testUpdateEmployee() {
        long id=1;
        Employee existingEmployee=new Employee();
        existingEmployee.setId(id);
        existingEmployee.setEmailId("k@gmail.com");
        existingEmployee.setFirstName("karan");
        existingEmployee.setLastName("kanojiya");

        Employee updatedEmployee=new Employee();
        updatedEmployee.setId(id);
        updatedEmployee.setEmailId("p@gmail.com");
        updatedEmployee.setFirstName("prashant");
        updatedEmployee.setLastName("rodat");
        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(updatedEmployee);

        ResponseEntity<Employee> response = employeeController.updateEmployee(id, updatedEmployee);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEmployee, response.getBody());
    }

    @Test
    public void testUpdateEmployeeNotFound() {
        long id = 100L; // Assuming an ID that doesn't exist
        Employee updatedEmployee = new Employee(id, "UpdatedFirstName", "UpdatedLastName", "updated@example.com");

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeController.updateEmployee(id, updatedEmployee));
    }

    @Test
    public void testDeleteEmployee() {
        long id = 1L;
        Employee existingEmployee = new Employee(id, "John", "Doe", "john@example.com");

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));

        ResponseEntity<HttpStatus> response = employeeController.deleteEmployee(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(employeeRepository, Mockito.times(1)).delete(existingEmployee);
    }

    @Test
    public void testDeleteEmployeeNotFound() {
        long id = 100L; // Assuming an ID that doesn't exist

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeController.deleteEmployee(id));
    }



}