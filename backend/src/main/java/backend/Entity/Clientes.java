package backend.Entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
@Table(name = "clientes")
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_cliente", length = 100, nullable = false)
    private String nombre_cliente;

    @Column(name = "cif_cliente", length = 20, nullable = false,unique = true)
    private String cif_cliente;

    @Column(name = "direccion_cliente", nullable = false)
    private String direccion_cliente;

    @Column(name = "email_cliente", length = 100, nullable = false)
    private String email_cliente;

    @Column(name = "telefono_cliente", length = 20, nullable = false)
    private String telefono_cliente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Facturas> facturas = new ArrayList<>();



}
