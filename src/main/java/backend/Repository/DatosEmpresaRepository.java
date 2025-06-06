package backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.Entity.DatosEmpresa;

@Repository
public interface DatosEmpresaRepository extends JpaRepository<DatosEmpresa, Long> {
    @Query("FROM DatosEmpresa c WHERE c.nombre_empresa = :nombre_empresa")
    DatosEmpresa ComprobarEmpresaPorNombre(@Param("nombre_empresa") String nombre_empresa);
}
