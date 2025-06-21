package backend.Repository;

import backend.Entity.Facturas;
import backend.Entity.Gastos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastosRepository extends JpaRepository<Gastos, Long> {

    @Query("FROM Gastos u WHERE u.usuario.id = :id_usuario")
    List<Gastos> ObtenerGastosPorIdUsuario(@Param("id_usuario") Long id_usuario);
}
