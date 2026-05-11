package com.TopStyleHat.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TopStyleHat.DTO.RegionDTO;
import com.TopStyleHat.Model.Region;
import com.TopStyleHat.Service.RegionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/Region")
public class RegionController {
    @Autowired
    RegionService regionService;

    //Mostrar todas las regiones
    @GetMapping
    public ResponseEntity<?> TodosLasRegiones(){
        List<RegionDTO> region = regionService.MostrarTodas();
        if (!region.isEmpty()) {
            return new ResponseEntity<>(region, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay regiones", HttpStatus.NO_CONTENT);
    }

    //Agregar
    @PostMapping
    public ResponseEntity<?> agregarRegion(@Valid @RequestBody Region region){
        try {
            return new ResponseEntity<>(regionService.guardarRegion(region), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo la region", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @PutMapping("/id")
    public ResponseEntity<Region> actualizarRegion(@PathVariable Integer id, @RequestBody Region region){
        try {
            Region newRegion = regionService.actualizarRegion(id, region);
            return new ResponseEntity<>(newRegion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @DeleteMapping("/id")
    public ResponseEntity<?> eliminarRegion(@PathVariable Integer id){
        try {
            regionService.EliminarRegion(id);
            return new ResponseEntity<>("Region eliminada", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontro la region", HttpStatus.NOT_FOUND);
        }
    }
}

