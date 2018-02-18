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
@Table(name = "idioma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Idioma.findAll", query = "SELECT i FROM Idioma i")
    , @NamedQuery(name = "Idioma.findByIdIdioma", query = "SELECT i FROM Idioma i WHERE i.idIdioma = :idIdioma")
    , @NamedQuery(name = "Idioma.findByDescripcionIdioma", query = "SELECT i FROM Idioma i WHERE i.descripcionIdioma = :descripcionIdioma")})
public class Idioma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_idioma")
    private Short idIdioma;
    @Column(name = "descripcion_idioma")
    private String descripcionIdioma;
    @OneToMany(mappedBy = "idIdioma")
    private Collection<Pelicula> peliculaCollection;

    public Idioma() {
    }

    public Idioma(Short idIdioma) {
        this.idIdioma = idIdioma;
    }

    public Short getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(Short idIdioma) {
        this.idIdioma = idIdioma;
    }

    public String getDescripcionIdioma() {
        return descripcionIdioma;
    }

    public void setDescripcionIdioma(String descripcionIdioma) {
        this.descripcionIdioma = descripcionIdioma;
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
        hash += (idIdioma != null ? idIdioma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Idioma)) {
            return false;
        }
        Idioma other = (Idioma) object;
        if ((this.idIdioma == null && other.idIdioma != null) || (this.idIdioma != null && !this.idIdioma.equals(other.idIdioma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Idioma[ idIdioma=" + idIdioma + " ]";
    }
    
}
