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
@Table(name = "operadora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operadora.findAll", query = "SELECT o FROM Operadora o")
    , @NamedQuery(name = "Operadora.findByIdOperadora", query = "SELECT o FROM Operadora o WHERE o.idOperadora = :idOperadora")
    , @NamedQuery(name = "Operadora.findByOperadora", query = "SELECT o FROM Operadora o WHERE o.operadora = :operadora")
    , @NamedQuery(name = "Operadora.findByEstado", query = "SELECT o FROM Operadora o WHERE o.estado = :estado")})
public class Operadora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operadora")
    private Integer idOperadora;
    @Column(name = "operadora")
    private String operadora;
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "idOperadora")
    private Collection<Cliente> clienteCollection;

    public Operadora() {
    }

    public Operadora(Integer idOperadora) {
        this.idOperadora = idOperadora;
    }

    public Integer getIdOperadora() {
        return idOperadora;
    }

    public void setIdOperadora(Integer idOperadora) {
        this.idOperadora = idOperadora;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Cliente> getClienteCollection() {
        return clienteCollection;
    }

    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        this.clienteCollection = clienteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperadora != null ? idOperadora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operadora)) {
            return false;
        }
        Operadora other = (Operadora) object;
        if ((this.idOperadora == null && other.idOperadora != null) || (this.idOperadora != null && !this.idOperadora.equals(other.idOperadora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Operadora[ idOperadora=" + idOperadora + " ]";
    }
    
}
