package com.TopStyleHat.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TopStyleHat.DTO.BoletaDTO;
import com.TopStyleHat.Model.Boleta;
import com.TopStyleHat.Repository.BoletaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    // Mostrar todas las boletas
    public List<BoletaDTO> obtenerTodas() {
        List<BoletaDTO> boletasDTO = new ArrayList<>();
        for (Boleta boleta : boletaRepository.findAll()) {
            boletasDTO.add(convertirADTO(boleta));
        }
        return boletasDTO;
    }

    // Buscar por ID
    public BoletaDTO buscarPorId(Integer id) {
        Boleta boleta = boletaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));
        return convertirADTO(boleta);
    }

    // Guardar boleta
    public Boleta guardar(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    // Actualizar boleta
    public Boleta actualizar(Integer id, Boleta boletaActualizada) {
        Boleta boletaExistente = boletaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Boleta no existe"));
        
        if (boletaActualizada.getMetodoPago() != null) {
            boletaExistente.setMetodoPago(boletaActualizada.getMetodoPago());
        }
        if (boletaActualizada.getMetodoEnvio() != null) {
            boletaExistente.setMetodoEnvio(boletaActualizada.getMetodoEnvio());
        }
        if (boletaActualizada.getFecha() != null) {
            boletaExistente.setFecha(boletaActualizada.getFecha());
        }
        if (boletaActualizada.getCliente() != null) {
            boletaExistente.setCliente(boletaActualizada.getCliente());
        }        
        return boletaRepository.save(boletaExistente);
    }

    // Eliminar boleta
    public String eliminar(Integer id) {
        try {
            Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no existe"));
            boletaRepository.delete(boleta);
            return "La boleta #" + boleta.getId() + " ha sido eliminada exitosamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    // Convertir Entity a DTO
    private BoletaDTO convertirADTO(Boleta boleta) {
        BoletaDTO dto = new BoletaDTO();
        dto.setId(boleta.getId());
        dto.setMetodoPago(boleta.getMetodoPago());
        dto.setMetodoEnvio(boleta.getMetodoEnvio());
        dto.setFecha(boleta.getFecha());
        
        if (boleta.getCliente() != null) {
            dto.setNombreCliente(boleta.getCliente().getNombre());
        } else {
            dto.setNombreCliente("Cliente no asignado");
        }        
        return dto;
    }
}
