package co.innside.tucuenta.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
	
    @Column(name="fecha")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime fechaCompra;
	
    @Column(name="url")
    private String url;
    
    @Column(name="total")
    private Double total;
    
    @ManyToOne
    @JoinColumn(name="idcomercio")
    private Comercio comercio;
    
    @ManyToOne
    @JoinColumn(name="idcliente")
    private Cliente cliente;
    
    @ManyToMany(
    	    cascade = {
    	            CascadeType.PERSIST, 
    	            CascadeType.MERGE
    	        })
    @JoinTable(name="producto",
    		   joinColumns=@JoinColumn(name="idfactura"),
    		   inverseJoinColumns=@JoinColumn(name="idproducto"))
    private List<Producto> productos = new ArrayList<>();
    
}
