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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author eduardo
 */
@Entity
@Table(name = "actor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actor.findAll", query = "SELECT a FROM Actor a order by a.nombresActor")
    , @NamedQuery(name = "Actor.findByIdActor", query = "SELECT a FROM Actor a WHERE a.idActor = :idActor")
    , @NamedQuery(name = "Actor.findByNombresActor", query = "SELECT a FROM Actor a WHERE a.nombresActor = :nombresActor")})
public class Actor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    
    @Column(name = "id_actor")
    private Integer idActor;
    
    @Column(name = "nombres_actor")
    private String nombresActor;
    
    @JoinTable(name = "pelicula_actor", joinColumns = {
        @JoinColumn(name = "id_actor", referencedColumnName = "id_actor")}, inverseJoinColumns = {
        @JoinColumn(name = "id_pelicula", referencedColumnName = "id_pelicula")})
    @ManyToMany
    private Collection<Pelicula> peliculaCollection;

    public Actor() {
    }

    public Actor(Integer idActor) {
        this.idActor = idActor;
    }

    public Integer getIdActor() {
        return idActor;
    }

    public void setIdActor(Integer idActor) {
        this.idActor = idActor;
    }

    public String getNombresActor() {
        return nombresActor;
    }

    public void setNombresActor(String nombresActor) {
        this.nombresActor = nombresActor;
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
        hash += (idActor != null ? idActor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actor)) {
            return false;
        }
        Actor other = (Actor) object;
        if ((this.idActor == null && other.idActor != null) || (this.idActor != null && !this.idActor.equals(other.idActor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Actor[ idActor=" + idActor + " ]";
    }
    
}
