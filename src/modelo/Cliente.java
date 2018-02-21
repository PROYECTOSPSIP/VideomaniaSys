/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author elichinita58
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente")
    , @NamedQuery(name = "Cliente.findByIdentificacion", query = "SELECT c FROM Cliente c WHERE c.identificacion = :identificacion")
    , @NamedQuery(name = "Cliente.findByApellidosCliente", query = "SELECT c FROM Cliente c WHERE c.apellidosCliente = :apellidosCliente")
    , @NamedQuery(name = "Cliente.findByNombresCliente", query = "SELECT c FROM Cliente c WHERE c.nombresCliente = :nombresCliente")
    , @NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion")
    , @NamedQuery(name = "Cliente.findByTelefonoConvencional", query = "SELECT c FROM Cliente c WHERE c.telefonoConvencional = :telefonoConvencional")
    , @NamedQuery(name = "Cliente.findByTelefeonoCelular", query = "SELECT c FROM Cliente c WHERE c.telefeonoCelular = :telefeonoCelular")
    , @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "apellidos_cliente")
    private String apellidosCliente;
    @Column(name = "nombres_cliente")
    private String nombresCliente;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono_convencional")
    private String telefonoConvencional;
    @Column(name = "telefeono_celular")
    private String telefeonoCelular;
    @Column(name = "email")
    private String email;
    @JoinColumn(name = "id_operadora", referencedColumnName = "id_operadora")
    @ManyToOne
    private Operadora idOperadora;
    @OneToMany(mappedBy = "idCliente")
    private Collection<VentaPelicula> ventaPeliculaCollection;

    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public String getNombresCliente() {
        return nombresCliente;
    }

    public void setNombresCliente(String nombresCliente) {
        this.nombresCliente = nombresCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoConvencional() {
        return telefonoConvencional;
    }

    public void setTelefonoConvencional(String telefonoConvencional) {
        this.telefonoConvencional = telefonoConvencional;
    }

    public String getTelefeonoCelular() {
        return telefeonoCelular;
    }

    public void setTelefeonoCelular(String telefeonoCelular) {
        this.telefeonoCelular = telefeonoCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Operadora getIdOperadora() {
        return idOperadora;
    }

    public void setIdOperadora(Operadora idOperadora) {
        this.idOperadora = idOperadora;
    }

    @XmlTransient
    public Collection<VentaPelicula> getVentaPeliculaCollection() {
        return ventaPeliculaCollection;
    }

    public void setVentaPeliculaCollection(Collection<VentaPelicula> ventaPeliculaCollection) {
        this.ventaPeliculaCollection = ventaPeliculaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Cliente[ idCliente=" + idCliente + " ]";
    }
    
}
