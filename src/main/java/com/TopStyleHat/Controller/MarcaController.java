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

import com.TopStyleHat.DTO.MarcaDTO;
import com.TopStyleHat.Model.Marca;
import com.TopStyleHat.Service.MarcaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/Marcas")
public class MarcaController {

    @Autowired
    MarcaService marcaService;

    //Mostrar todas las Marcas
    @GetMapping
    public ResponseEntity<?> TodasLasMarcas(){
        List<MarcaDTO> marcas = marcaService.MostrarTodas();
        if (!marcas.isEmpty()) {
            return new ResponseEntity<>("No hay Marcas", HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay marcas", HttpStatus.NO_CONTENT);
    }

    //Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarPorId(@PathVariable Integer id){
        try {
            MarcaDTO marca = marcaService.buscarPorId(id);
            return new ResponseEntity<>(marca, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontro la Marca", HttpStatus.NOT_FOUND);
        }
    }

    //Agregar
    @PostMapping
    public ResponseEntity<?> agregarMarca(@Valid @RequestBody Marca marca){
        try {
            return new ResponseEntity<>(marcaService.guardarMarca(marca), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo la Marca", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @PutMapping("/id")
    public ResponseEntity<Marca> actualizarBrand(@PathVariable Integer id, @RequestBody Marca brand){
        try {
            Marca newBrand = marcaService.actualizarMarca(id, brand);
            return new ResponseEntity<>(newBrand, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @DeleteMapping
    public ResponseEntity<String> eliminarBrand(@PathVariable Integer id){
        String resultado = marcaService.EliminarMarca(id);

        if (resultado.contains("Eliminada")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }

    }





}
