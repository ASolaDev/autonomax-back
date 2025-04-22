package backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.Entity.Clientes;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {
    @Query("FROM Clientes c WHERE c.email_cliente = :email")
    Clientes ComprobarUsuarioPorEmail(@Param("email") String email);
}
