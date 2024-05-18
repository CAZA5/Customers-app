package com.consultora.customerMicroservice.controllers;

import com.consultora.customerMicroservice.entity.CustomerEntity;
import com.consultora.customerMicroservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers-service")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    //metodos
    //Buscar todos
    @GetMapping("/findAll")
    public List<CustomerEntity> findAll(){
        return customerRepository.findAll();
    }

    //Buscar por id
    @GetMapping("/{customerId}")
    public ResponseEntity<ResponseEntity> findById(@PathVariable Long customerId){
        return new ResponseEntity(customerRepository.findById(customerId), HttpStatus.OK);
    }

    //Crear cliente
    @PostMapping("/createCustomer")
    public ResponseEntity createNewCustomer(@RequestBody CustomerEntity customerEntity){
        customerRepository.save(customerEntity);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //Borrar cliente
    @DeleteMapping("/{customerId}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Long customerId){
        customerRepository.findById(customerId);
        customerRepository.deleteById(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Actualizar cliente
    @PutMapping("/{customerId}")
    public ResponseEntity updateCustomer(@PathVariable Long customerId, @RequestBody String newName){
        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            CustomerEntity customer = optionalCustomer.get();
            customer.setName(newName);
            customerRepository.save(customer); // Guardar los cambios en la base de datos
            return ResponseEntity.ok().build(); // Devolver respuesta 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Devolver respuesta 404 Not Found si el cliente no se encuentra
        }
    }

}
