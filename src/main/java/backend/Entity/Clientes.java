package backend.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_cliente", length = 100, nullable = false)
    private String nombreCliente;

    @Column(name = "cif_cliente", length = 20, nullable = false, unique = true)
    private String cifCliente;

    @Column(name = "direccion_cliente", nullable = false)
    private String direccionCliente;

    @Column(name = "email_cliente", length = 100, nullable = false)
    private String emailCliente;

    @Column(name = "telefono_cliente", length = 20, nullable = false)
    private String telefonoCliente;

    @Column(name = "ciudad_cliente", length = 20, nullable = false)
    private String ciudadCliente;

    @Column(name = "provincia_cliente", length = 20, nullable = false)
    private String provinciaCliente;

    @Column(name = "tipo_cliente", length = 20, nullable = false)
    private TipoCliente tipoCliente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Facturas> facturas = new ArrayList<>();

}
