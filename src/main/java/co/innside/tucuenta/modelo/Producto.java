package co.innside.tucuenta.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="producto")
public class Producto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idproducto")
    private Long id;
	
    @Column(name="nombre")
    private String nombre;
    
    @Column(name="precio")
    private Double precio;
    
    @Column(name="cantidad")
    private Integer cantidad;
    
    @ManyToOne
    @JoinColumn(name="idfactura")
    private Factura factura;

}
