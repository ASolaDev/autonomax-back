package backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

@Table(name = "proveedores")
public class Proveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_proveedor", length = 100, nullable = false)
    private String nombreProveedor;

    @Column(name = "cif_proveedor", length = 20, nullable = false, unique = true)
    private String cifProveedor;

    @Column(name = "direccion_proveedor", nullable = false)
    private String direccionProveedor;

    @Column(name = "email_proveedor", length = 100, nullable = false)
    private String emailProveedor;

    @Column(name = "ciudad_proveedor", length = 50, nullable = false)
    private String ciudadProveedor;

    @Column(name = "provincia_proveedor", length = 50, nullable = false)
    private String provinciaProveedor;

    @Column(name = "tipo_proveedor", length = 50)
    private TipoProveedor tipoProveedor;

    @Column(name = "telefono_proveedor", length = 20, nullable = false)
    private String telefonoProveedor;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gastos> gastos = new ArrayList<>();
}
