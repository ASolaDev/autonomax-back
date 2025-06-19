package backend.DTOS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import backend.Entity.Clientes;
import backend.Entity.DetalleFactura;
import backend.Entity.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDetallesDTO {

    private String numeroFactura;

    private Date fechaEmision;
    private Date fechaPago;

    private BigDecimal subtotal;

    private BigDecimal iva;

    private BigDecimal total;

    private Estado estado;

    private List<DetalleFactura> facturasDetalles = new ArrayList<>();

    private Clientes cliente;

    // Datos que necesito en las relaciones
    private Long idCliente;
    private Long idUsuario;
    private Long idEmpresa;

}
