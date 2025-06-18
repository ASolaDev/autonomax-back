package backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.Entity.Clientes;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {
    @Query("FROM Clientes c WHERE c.cifCliente = :cif")
    Clientes ComprobarClientePorCIF(@Param("cif") String cif);

    @Query("FROM Clientes c WHERE c.emailCliente = :email")
    Clientes ComprobarClientePorEmail(@Param("email") String email);

}
