package backend.Entity;

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
@Table(name = "datos_empresa")
public class DatosEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_empresa", length = 100, nullable = false)
    private String nombre_empresa;

    @Column(name = "cif_empresa", length = 20, nullable = false)
    private String cif_empresa;

    @Column(name = "direccion_empresa", nullable = false)
    private String direccion_empresa;

    @Column(name = "email_empresa", length = 100, nullable = false)
    private String email_empresa;

    @Column(name = "telefono_empresa", length = 20, nullable = false)
    private String telefono_empresa;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Facturas> facturas = new ArrayList<>();
}
