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

import com.TopStyleHat.DTO.ComunaDTO;
import com.TopStyleHat.Model.Comuna;
import com.TopStyleHat.Service.ComunaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/Comuna")
public class ComunaController {
    
    @Autowired
    ComunaService comunaService;

    //Mostrar todas las comunas
    @GetMapping
    public ResponseEntity<?> TodosLasComunas(){
        List<ComunaDTO> comuna = comunaService.MostrarTodas();
        if (!comuna.isEmpty()) {
            return new ResponseEntity<>(comuna, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay comunas", HttpStatus.NO_CONTENT);
    }

    //Agregar
    @PostMapping
    public ResponseEntity<?> agregarComuna(@Valid @RequestBody Comuna comuna){
        try {
            return new ResponseEntity<>(comunaService.guardarComuna(comuna), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo la comuna", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @PutMapping("/id")
    public ResponseEntity<Comuna> actualizarComuna(@PathVariable Integer id, @RequestBody Comuna comuna){
        try {
            Comuna newComuna = comunaService.actualizarComu(id, comuna);
            return new ResponseEntity<>(newComuna, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @DeleteMapping("/id")
    public ResponseEntity<String> eliminarComuna(@PathVariable Integer id){
        String resultado = comunaService.EliminarComuna(id);

        if (resultado.contains("Eliminado")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
