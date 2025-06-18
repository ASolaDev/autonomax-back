package backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private String nombreEmpresa;

    @Column(name = "cif_empresa", length = 20, nullable = false)
    private String cifEmpresa;

    @Column(name = "direccion_empresa", nullable = false)
    private String direccionEmpresa;

    @Column(name = "email_empresa", length = 100, nullable = false)
    private String emailEmpresa;

    @Column(name = "telefono_empresa", length = 20, nullable = false)
    private String telefonoEmpresa;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Facturas> facturas = new ArrayList<>();
}
