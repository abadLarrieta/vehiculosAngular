package com.practica.vehiculos.controller;

import com.practica.vehiculos.model.Vehiculo;
import com.practica.vehiculos.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    // Endpoint para insertar un vehículo usando stored procedure
    @PostMapping("/insertar")
    public ResponseEntity<String> insertarVehiculo(@RequestBody Vehiculo vehiculo) {
        vehiculoService.insertarVehiculo(vehiculo.getVin(), vehiculo.getPlaca(), vehiculo.getModelo(), vehiculo.getEstatus());
        return new ResponseEntity<>("Vehículo insertado exitosamente.", HttpStatus.CREATED);
    }

    // Endpoint para editar un vehículo usando stored procedure
    @PutMapping("/editar/{id}")
    public ResponseEntity<String> editarVehiculo(@PathVariable int id, @RequestBody Vehiculo vehiculo) {
        vehiculoService.editarVehiculo(id, vehiculo.getVin(), vehiculo.getPlaca(), vehiculo.getModelo(), vehiculo.getEstatus());
        return new ResponseEntity<>("Vehículo editado exitosamente.", HttpStatus.OK);
    }

    // Endpoint para consultar un vehículo por ID usando stored procedure
    @GetMapping("/consultar/{id}")
    public ResponseEntity<Vehiculo> consultarVehiculo(@PathVariable int id) {
        Optional<Vehiculo> vehiculoOpt = vehiculoService.consultarVehiculo(id);
        return vehiculoOpt.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint para obtener todos los vehículos (operación básica de JPA)
    @GetMapping("/todos")
    public ResponseEntity<List<Vehiculo>> listarVehiculos() {
        List<Vehiculo> vehiculos = vehiculoService.listarVehiculos();
        return new ResponseEntity<>(vehiculos, HttpStatus.OK);
    }

    // Endpoint para guardar un vehículo (operación básica de JPA)
    @PostMapping("/guardar")
    public ResponseEntity<Vehiculo> guardarVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo vehiculoGuardado = vehiculoService.guardarVehiculo(vehiculo);
        return new ResponseEntity<>(vehiculoGuardado, HttpStatus.CREATED);
    }

    // Endpoint para eliminar un vehículo por ID (operación básica de JPA)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarVehiculo(@PathVariable int id) {
        vehiculoService.eliminarVehiculo(id);
        return new ResponseEntity<>("Vehículo eliminado exitosamente.", HttpStatus.OK);
    }

    // Endpoint para obtener un vehículo por ID (operación básica de JPA)
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerVehiculoPorId(@PathVariable int id) {
        Optional<Vehiculo> vehiculoOpt = vehiculoService.obtenerVehiculoPorId(id);
        return vehiculoOpt.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
