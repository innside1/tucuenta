package co.innside.tucuenta.datasource.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="factura")
public class Factura implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8189127881281991547L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idfactura")
    private Long id;
	
    @Column(name="fecha", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCompra;
	
    @Column(name="url")
    private String url;
    
    @Column(name="total")
    private Double total;
    
    @Column(name="idCsv")
    private String idCsv;
    
    @ManyToOne
    @JoinColumn(name="idcomercio")
    private Comercio comercio;
    
    @ManyToOne
    @JoinColumn(name="idcliente")
    private Cliente cliente;
    
    @OneToMany(mappedBy = "factura")
    private List<Producto> productos = new ArrayList<>();
    
}
