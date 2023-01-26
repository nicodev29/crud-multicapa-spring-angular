package com.example.demo.controller;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Empleado;
import com.example.demo.repository.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200/")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;


    //metodo para listar todos los empleados
    @GetMapping("/empleados")
    public List<Empleado> listarTodosEmpleados() {
        return empleadoRepositorio.findAll();
    }


    //metodo para guardar un empleado con formato json
    @PostMapping("/empleados")
    public Empleado crearEmpleado(@RequestBody Empleado empleado) {
        return empleadoRepositorio.save(empleado);
    }

    //metodo para editar un empleado
    @PutMapping("/empleados/{id}")
    public Empleado editarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        Empleado empleadoActual = empleadoRepositorio.findById(id).orElse(null);
        empleadoActual.setNombre(empleado.getNombre());
        empleadoActual.setApellido(empleado.getApellido());
        empleadoActual.setEmail(empleado.getEmail());
        return empleadoRepositorio.save(empleadoActual);
    }


    //Metodo para buscar un empleado por id usando la ResponseEntity
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoId(@PathVariable Long id) {
        Empleado empleado = empleadoRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empleado no existe con el id: " + id));
        return ResponseEntity.ok(empleado);
    }

    //Metodo para eliminar un empleado por id
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Empleado> eliminarEmpleadoId(@PathVariable Long id) {
        Empleado empleado = empleadoRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empleado no existe con el id: " + id));
        empleadoRepositorio.delete(empleado);
        return ResponseEntity.ok(empleado);
    }


}
