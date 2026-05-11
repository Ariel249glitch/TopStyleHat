package com.TopStyleHat.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.TopStyleHat.DTO.MarcasDTO;
import com.TopStyleHat.Model.Marcas;
import com.TopStyleHat.Repository.MarcasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class MarcasService {

    @Autowired
    private MarcasRepository marcasRepository;


    //Mostrar todas las marcas
    public List<MarcasDTO> MostrarTodas(){
        List<MarcasDTO> marcas = new ArrayList<>();
        for (Marcas marcas2 : marcasRepository.findAll()) {
            marcas.add(convertirADTO(marcas2));
        }
        return marcas;
    }

     //Convertir DTO
    private MarcasDTO convertirADTO (Marcas marcas){
        MarcasDTO marcasDTO = new MarcasDTO();
        marcasDTO.setId(marcas.getId());
        marcasDTO.setNombre(marcas.getNombre());
        return marcasDTO;
    }

    //buscar por id
    public MarcasDTO buscarPorId(Integer id){
        Marcas marcas = marcasRepository.findById(id).orElseThrow(() -> new RuntimeException("no encontrada"));
        return convertirADTO(marcas);
    }

    //Guardar Marcas
    public MarcasDTO guardarMarcas(Marcas nuevasMarcas){
        Marcas marcasGuardadas = marcasRepository.save(nuevasMarcas);
        return convertirADTO(marcasGuardadas);
    }

    //Actualizar Marcas
    public Marcas actualizarMarcas(Integer id, Marcas marcas){
        Marcas Brands = marcasRepository.findById(id).orElseThrow(() ->  new RuntimeException("no existe"));
        if (marcas.getNombre() != null) {
            Brands.setNombre(marcas.getNombre());
        }
        return marcasRepository.save(Brands);
    }

    //Eliminar
    public String EliminarMarcas(Integer id){
        try {
            Marcas marcas = marcasRepository.findById(id).orElseThrow(() -> new RuntimeException("No se puede eliminar la marca con id" + id + "No existe" ));
            marcasRepository.delete(marcas);
            return "La marca '" + marcas.getNombre() + "' a sido eliminada";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }



}
