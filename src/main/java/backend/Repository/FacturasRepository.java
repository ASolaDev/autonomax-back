package backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import backend.Entity.Facturas;

public interface FacturasRepository extends JpaRepository<Facturas, Long> {
    @Query("FROM Facturas u WHERE u.numeroFactura = :numeroFactura")
    Facturas ObtenerFacturaPorNumeroFactura(@Param("numeroFactura") String numeroFactura);

}
