package com.TopStyleHat.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TopStyleHat.DTO.DetalleBoletaDTO;
import com.TopStyleHat.Service.DetalleBoletaService;

@RestController
@RequestMapping("/api/v1/detalle-boleta")
public class DetalleBoletaController {

    @Autowired
    private DetalleBoletaService detalleBoletaService;

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<DetalleBoletaDTO> detalles = detalleBoletaService.obtenerTodos();
        if (detalles.isEmpty()) {
            return new ResponseEntity<>("No hay detalles de boleta", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detalles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            DetalleBoletaDTO detalle = detalleBoletaService.buscarPorId(id);
            return new ResponseEntity<>(detalle, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Detalle no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/boleta/{boletaId}")
    public ResponseEntity<?> buscarPorBoleta(@PathVariable Integer boletaId) {
        List<DetalleBoletaDTO> detalles = detalleBoletaService.buscarPorBoleta(boletaId);
        if (detalles.isEmpty()) {
            return new ResponseEntity<>("Esta boleta no tiene productos", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detalles, HttpStatus.OK);
    }

    @GetMapping("/gorro/{gorroId}")
    public ResponseEntity<?> buscarPorGorro(@PathVariable Integer gorroId) {
        List<DetalleBoletaDTO> detalles = detalleBoletaService.buscarPorGorro(gorroId);
        if (detalles.isEmpty()) {
            return new ResponseEntity<>("Este gorro no ha sido vendido", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detalles, HttpStatus.OK);
    }

    @PostMapping("/boleta/{boletaId}/gorro/{gorroId}")
    public ResponseEntity<?> agregarProductoABoleta(
            @PathVariable Integer boletaId,
            @PathVariable Integer gorroId,
            @RequestParam Integer cantidad,
            @RequestParam Integer precioUnitario) {
        try {
            DetalleBoletaDTO nuevoDetalle = detalleBoletaService.agregarProductoABoleta(boletaId, gorroId, cantidad, precioUnitario);
            return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDetalle(@PathVariable Integer id) {
        String resultado = detalleBoletaService.eliminarDetalle(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
