package backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.Entity.Proveedores;

@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedores, Long> {
    @Query("FROM Proveedores c WHERE c.cifProveedor = :cif")
    Proveedores ComprobarProveedorPorCIF(@Param("cif") String cif);

    @Query("FROM Proveedores c WHERE c.emailProveedor = :email")
    Proveedores ComprobarProveedorPorEmail(@Param("email") String email);
}
