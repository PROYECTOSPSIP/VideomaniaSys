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
 * @author eduardo
 */
@Entity
@Table(name = "pais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p")
    , @NamedQuery(name = "Pais.findByIdPais", query = "SELECT p FROM Pais p WHERE p.idPais = :idPais")
    , @NamedQuery(name = "Pais.findByNombrePais", query = "SELECT p FROM Pais p WHERE p.nombrePais = :nombrePais")
    , @NamedQuery(name = "Pais.findByIso", query = "SELECT p FROM Pais p WHERE p.iso = :iso")})
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pais")
    private Short idPais;
    @Column(name = "nombre_pais")
    private String nombrePais;
    @Column(name = "iso")
    private String iso;
    @OneToMany(mappedBy = "idPais")
    private Collection<Pelicula> peliculaCollection;

    public Pais() {
    }

    public Pais(Short idPais) {
        this.idPais = idPais;
    }

    public Short getIdPais() {
        return idPais;
    }

    public void setIdPais(Short idPais) {
        this.idPais = idPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    @XmlTransient
    public Collection<Pelicula> getPeliculaCollection() {
        return peliculaCollection;
    }

    public void setPeliculaCollection(Collection<Pelicula> peliculaCollection) {
        this.peliculaCollection = peliculaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPais != null ? idPais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.idPais == null && other.idPais != null) || (this.idPais != null && !this.idPais.equals(other.idPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Pais[ idPais=" + idPais + " ]";
    }
    
}
