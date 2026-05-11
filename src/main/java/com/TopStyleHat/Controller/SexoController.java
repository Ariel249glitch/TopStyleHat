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


import com.TopStyleHat.DTO.SexoDTO;
import com.TopStyleHat.Model.Sexo;
import com.TopStyleHat.Service.SexoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/Sexo")
public class SexoController {

    @Autowired
    SexoService sexoService;

    //Mostrar todos
    @GetMapping
    public ResponseEntity<?> MostrarTodos(){
        List <SexoDTO> sexo = sexoService.mostrarTodos();
        if (!sexo.isEmpty()) {
            return new ResponseEntity<>("No hay registros", HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay registros", HttpStatus.NO_CONTENT);
    }

    //buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarPorId(@PathVariable Integer id){
        try {
            SexoDTO sexo = sexoService.buscarPorId(id);
            return new ResponseEntity<>(sexo, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontro", HttpStatus.NOT_FOUND);
        }
    }

    //Agregar
    @PostMapping
    public ResponseEntity<?> agregarSexo(@Valid @RequestBody Sexo sexo){
        try {
            return new ResponseEntity<>(sexoService.guardarSexo(sexo), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @PutMapping("/id")
    public ResponseEntity<Sexo> actualizarGenero(@PathVariable Integer id, @RequestBody Sexo genero){
        try {
            Sexo newGenero = sexoService.actualizarSexo(id, genero);
            return new ResponseEntity<>(newGenero, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @DeleteMapping
    public ResponseEntity<String> eliminarStuff(@PathVariable Integer id){
        String resultado = sexoService.eliminarSexo(id);

        if (resultado.contains("Eliminado")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}
