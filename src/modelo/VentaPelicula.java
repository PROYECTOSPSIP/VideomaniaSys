/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author eduardo
 */
@Entity
@Table(name = "venta_pelicula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentaPelicula.findAll", query = "SELECT v FROM VentaPelicula v")
    , @NamedQuery(name = "VentaPelicula.findByIdVentaPelicula", query = "SELECT v FROM VentaPelicula v WHERE v.idVentaPelicula = :idVentaPelicula")
    , @NamedQuery(name = "VentaPelicula.findByCantidad", query = "SELECT v FROM VentaPelicula v WHERE v.cantidad = :cantidad")
    , @NamedQuery(name = "VentaPelicula.findByValorUnitario", query = "SELECT v FROM VentaPelicula v WHERE v.valorUnitario = :valorUnitario")
    , @NamedQuery(name = "VentaPelicula.findByValorTotal", query = "SELECT v FROM VentaPelicula v WHERE v.valorTotal = :valorTotal")
    , @NamedQuery(name = "VentaPelicula.findByFechaVenta", query = "SELECT v FROM VentaPelicula v WHERE v.fechaVenta = :fechaVenta")})
public class VentaPelicula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_venta_pelicula")
    private Integer idVentaPelicula;
    @Column(name = "cantidad")
    private Short cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_unitario")
    private Double valorUnitario;
    @Column(name = "valor_total")
    private Double valorTotal;
    @Column(name = "fecha_venta")
    @Temporal(TemporalType.DATE)
    private Date fechaVenta;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;
    @JoinColumn(name = "id_pelicula", referencedColumnName = "id_pelicula")
    @ManyToOne
    private Pelicula idPelicula;

    public VentaPelicula() {
    }

    public VentaPelicula(Integer idVentaPelicula) {
        this.idVentaPelicula = idVentaPelicula;
    }

    public Integer getIdVentaPelicula() {
        return idVentaPelicula;
    }

    public void setIdVentaPelicula(Integer idVentaPelicula) {
        this.idVentaPelicula = idVentaPelicula;
    }

    public Short getCantidad() {
        return cantidad;
    }

    public void setCantidad(Short cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Pelicula getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Pelicula idPelicula) {
        this.idPelicula = idPelicula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVentaPelicula != null ? idVentaPelicula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentaPelicula)) {
            return false;
        }
        VentaPelicula other = (VentaPelicula) object;
        if ((this.idVentaPelicula == null && other.idVentaPelicula != null) || (this.idVentaPelicula != null && !this.idVentaPelicula.equals(other.idVentaPelicula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.VentaPelicula[ idVentaPelicula=" + idVentaPelicula + " ]";
    }
    
}
