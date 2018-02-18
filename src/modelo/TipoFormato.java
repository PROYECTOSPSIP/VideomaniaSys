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
@Table(name = "tipo_formato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoFormato.findAll", query = "SELECT t FROM TipoFormato t")
    , @NamedQuery(name = "TipoFormato.findByIdTipoformato", query = "SELECT t FROM TipoFormato t WHERE t.idTipoformato = :idTipoformato")
    , @NamedQuery(name = "TipoFormato.findByDescripcionFormato", query = "SELECT t FROM TipoFormato t WHERE t.descripcionFormato = :descripcionFormato")})
public class TipoFormato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipoformato")
    private Short idTipoformato;
    @Column(name = "descripcion_formato")
    private String descripcionFormato;
    @OneToMany(mappedBy = "idTipoformato")
    private Collection<Pelicula> peliculaCollection;

    public TipoFormato() {
    }

    public TipoFormato(Short idTipoformato) {
        this.idTipoformato = idTipoformato;
    }

    public Short getIdTipoformato() {
        return idTipoformato;
    }

    public void setIdTipoformato(Short idTipoformato) {
        this.idTipoformato = idTipoformato;
    }

    public String getDescripcionFormato() {
        return descripcionFormato;
    }

    public void setDescripcionFormato(String descripcionFormato) {
        this.descripcionFormato = descripcionFormato;
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
        hash += (idTipoformato != null ? idTipoformato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoFormato)) {
            return false;
        }
        TipoFormato other = (TipoFormato) object;
        if ((this.idTipoformato == null && other.idTipoformato != null) || (this.idTipoformato != null && !this.idTipoformato.equals(other.idTipoformato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoFormato[ idTipoformato=" + idTipoformato + " ]";
    }
    
}
