package backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.Entity.DatosEmpresa;

@Repository
public interface DatosEmpresaRepository extends JpaRepository<DatosEmpresa, Long> {
    @Query("FROM DatosEmpresa c WHERE c.nombreEmpresa = :nombreEmpresa")
    DatosEmpresa ComprobarEmpresaPorNombre(@Param("nombreEmpresa") String nombreEmpresa);
}
