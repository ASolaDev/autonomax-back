package backend.Repository;

import backend.Entity.CategoriaGastos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaGastosRepository extends JpaRepository<CategoriaGastos, Long> {
    @Query("FROM CategoriaGastos u WHERE u.categoria = :categoria")
    CategoriaGastos ComprobarCategoriaPorNombre(@Param("categoria") String categoria);
}
