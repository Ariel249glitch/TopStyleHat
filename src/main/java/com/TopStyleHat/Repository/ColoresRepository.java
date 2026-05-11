package com.TopStyleHat.Repository;

import com.TopStyleHat.Model.Colores;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ColoresRepository extends JpaRepository<Colores, Integer> {

    List<Colores> findByGorroId(Integer gorroId);

    List<Colores> findByColorId(Integer colorId);
}
