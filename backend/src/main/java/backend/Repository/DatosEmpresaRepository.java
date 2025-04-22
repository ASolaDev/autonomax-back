package backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.Entity.DatosEmpresa;

@Repository
public interface DatosEmpresaRepository extends JpaRepository<DatosEmpresa, Long> {

}
