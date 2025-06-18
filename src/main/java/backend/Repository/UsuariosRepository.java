package backend.Repository;

import backend.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    @Query("FROM Usuarios u WHERE u.nombreUsuario = :nombre_usuario")
    Usuarios ComprobarUsuarioPorNombreUsuario(@Param("nombre_usuario") String nombre_usuario);

    @Query("FROM Usuarios u WHERE u.email = :email")
    Usuarios ComprobarUsuarioPorEmail(@Param("email") String email);
}
