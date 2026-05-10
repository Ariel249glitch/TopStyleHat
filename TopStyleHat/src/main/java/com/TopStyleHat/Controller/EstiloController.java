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

import com.TopStyleHat.DTO.EstiloDTO;
import com.TopStyleHat.Model.Estilo;
import com.TopStyleHat.Service.EstiloService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/Estilo")
public class EstiloController {
    
    @Autowired
    EstiloService estiloService;

    //Mostrar todos los tipos 
    @GetMapping
    public ResponseEntity<?> TodosLosEstilos(){
        List<EstiloDTO> estilo = estiloService.MostrarTodas();
        if (!estilo.isEmpty()) {
            return new ResponseEntity<>("No hay Estilo", HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay Estilo", HttpStatus.NO_CONTENT);
    }

    //Agregar
    @PostMapping
    public ResponseEntity<?> agregarEsti(@Valid @RequestBody Estilo estilo){
        try {
            return new ResponseEntity<>(estiloService.guardarEstilo(estilo), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo el estilo", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @PutMapping("/id")
    public ResponseEntity<Estilo> actualizarEsti(@PathVariable Integer id, @RequestBody Estilo Esti){
        try {
            Estilo newEsti = estiloService.actualizarEsti(id, Esti);
            return new ResponseEntity<>(newEsti, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @DeleteMapping("/id")
    public ResponseEntity<String> eliminarEsti(@PathVariable Integer id){
        String resultado = estiloService.EliminarEstilo(id);

        if (resultado.contains("Eliminado")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
