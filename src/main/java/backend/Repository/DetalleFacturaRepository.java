package backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.Entity.DetalleFactura;

import java.util.List;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {
    @Query("FROM DetalleFactura f WHERE f.factura = :factura")
    List<DetalleFactura> borrarDetallesFacturaPorIdFactura(@Param("factura") Long factura);

}
