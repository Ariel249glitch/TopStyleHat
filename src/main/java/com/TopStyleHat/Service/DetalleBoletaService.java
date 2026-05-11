package com.TopStyleHat.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TopStyleHat.DTO.DetalleBoletaDTO;
import com.TopStyleHat.Model.Boleta;
import com.TopStyleHat.Model.DetalleBoleta;
import com.TopStyleHat.Model.Gorro;
import com.TopStyleHat.Repository.BoletaRepository;
import com.TopStyleHat.Repository.DetalleBoletaRepository;
import com.TopStyleHat.Repository.GorroRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleBoletaService {

    @Autowired
    private DetalleBoletaRepository detalleBoletaRepository;

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private GorroRepository gorroRepository;

    public List<DetalleBoletaDTO> obtenerTodos() {
        List<DetalleBoletaDTO> detallesDTO = new ArrayList<>();
        for (DetalleBoleta detalle : detalleBoletaRepository.findAll()) {
            detallesDTO.add(convertirADTO(detalle));
        }
        return detallesDTO;
    }

    public DetalleBoletaDTO buscarPorId(Integer id) {
        DetalleBoleta detalle = detalleBoletaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
        return convertirADTO(detalle);
    }

    public List<DetalleBoletaDTO> buscarPorBoleta(Integer boletaId) {
        List<DetalleBoletaDTO> detallesDTO = new ArrayList<>();
        for (DetalleBoleta detalle : detalleBoletaRepository.findByBoletaId(boletaId)) {
            detallesDTO.add(convertirADTO(detalle));
        }
        return detallesDTO;
    }

    public List<DetalleBoletaDTO> buscarPorGorro(Integer gorroId) {
        List<DetalleBoletaDTO> detallesDTO = new ArrayList<>();
        for (DetalleBoleta detalle : detalleBoletaRepository.findByGorroId(gorroId)) {
            detallesDTO.add(convertirADTO(detalle));
        }
        return detallesDTO;
    }

    public DetalleBoletaDTO agregarProductoABoleta(Integer boletaId, Integer gorroId, Integer cantidad, Integer precioUnitario) {
        Boleta boleta = boletaRepository.findById(boletaId)
            .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));
        
        Gorro gorro = gorroRepository.findById(gorroId)
            .orElseThrow(() -> new RuntimeException("Gorro no encontrado"));
        
        DetalleBoleta nuevoDetalle = new DetalleBoleta();
        nuevoDetalle.setBoleta(boleta);
        nuevoDetalle.setGorro(gorro);
        nuevoDetalle.setCantidad(cantidad);
        nuevoDetalle.setPrecioUnitario(precioUnitario);
        
        DetalleBoleta detalleGuardado = detalleBoletaRepository.save(nuevoDetalle);
        return convertirADTO(detalleGuardado);
    }

    public String eliminarDetalle(Integer id) {
        try {
            DetalleBoleta detalle = detalleBoletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no existe"));
            detalleBoletaRepository.delete(detalle);
            return "Detalle eliminado exitosamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private DetalleBoletaDTO convertirADTO(DetalleBoleta detalle) {
        DetalleBoletaDTO dto = new DetalleBoletaDTO();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        
        if (detalle.getCantidad() != null && detalle.getPrecioUnitario() != null) {
            dto.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
        } else {
            dto.setSubtotal(0);
        }
        
        if (detalle.getGorro() != null) {
            dto.setNombreGorro(detalle.getGorro().getNombre());
        } else {
            dto.setNombreGorro("Desconocido");
        }        
        return dto;
    }
}
