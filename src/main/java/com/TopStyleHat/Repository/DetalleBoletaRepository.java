package com.TopStyleHat.Repository;

import com.TopStyleHat.Model.DetalleBoleta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, Integer> {

    List<DetalleBoleta> findByBoletaId(Integer boletaId);

    List<DetalleBoleta> findByGorroId(Integer gorroId);
}
