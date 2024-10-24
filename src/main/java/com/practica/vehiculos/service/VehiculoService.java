package com.practica.vehiculos.service;


import com.practica.vehiculos.model.Vehiculo;
import com.practica.vehiculos.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Metodo para insertar un vehículo usando un stored procedure
    public void insertarVehiculo(String vin, String placa, String modelo, String estatus) {
        String sql = "CALL insertar_vehiculo(?, ?, ?, ?)";
        jdbcTemplate.update(sql, vin, placa, modelo, estatus);
    }

    // Metodo para editar un vehículo usando un stored procedure
    public void editarVehiculo(int id, String vin, String placa, String modelo, String estatus) {
        String sql = "CALL editar_vehiculo(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, vin, placa, modelo, estatus);
    }

    // Metodo para consultar un vehículo por ID usando un stored procedure
    public Optional<Vehiculo> consultarVehiculo(int id) {
        String sql = "SELECT * FROM consultar_vehiculo(?)";
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (rs, rowNum) -> {
                    Vehiculo vehiculo = new Vehiculo();
                    vehiculo.setId(rs.getInt("id"));
                    vehiculo.setVin(rs.getString("vin"));
                    vehiculo.setPlaca(rs.getString("placa"));
                    vehiculo.setModelo(rs.getString("modelo"));
                    vehiculo.setEstatus(rs.getString("estatus"));
                    return vehiculo;
                }
        ));
    }

    // Métodos CRUD adicionales
    public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public void eliminarVehiculo(int id) {
        vehiculoRepository.deleteById(id);
    }

    public Optional<Vehiculo> obtenerVehiculoPorId(int id) {
        return vehiculoRepository.findById(id);
    }

    public List<Vehiculo> listarVehiculos() {
        return vehiculoRepository.findAll();
    }
}
