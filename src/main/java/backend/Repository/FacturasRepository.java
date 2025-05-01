package backend.Repository;

import backend.Entity.Facturas;
import backend.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacturasRepository extends JpaRepository<Facturas, Long> {
    @Query("FROM Facturas u WHERE u.numero_factura = :numero_factura")
    Facturas ObtenerFacturaPorNumeroFactura(@Param("numero_factura") String numeroFactura);
}
