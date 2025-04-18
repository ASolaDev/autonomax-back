package backend.Repository;

import backend.Entity.DatosEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosEmpresaRepository extends JpaRepository<DatosEmpresa,Long>
{

}
