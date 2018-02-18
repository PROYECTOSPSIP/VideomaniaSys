/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "pelicula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pelicula.findAll", query = "SELECT p FROM Pelicula p order by p.titulo")
    , @NamedQuery(name = "Pelicula.findByIdPelicula", query = "SELECT p FROM Pelicula p WHERE p.idPelicula = :idPelicula")
    , @NamedQuery(name = "Pelicula.findByTitulo", query = "SELECT p FROM Pelicula p WHERE p.titulo = :titulo")
    , @NamedQuery(name = "Pelicula.findByAnio", query = "SELECT p FROM Pelicula p WHERE p.anio = :anio")
    , @NamedQuery(name = "Pelicula.findByDuracion", query = "SELECT p FROM Pelicula p WHERE p.duracion = :duracion")
    , @NamedQuery(name = "Pelicula.findByPrecio", query = "SELECT p FROM Pelicula p WHERE p.precio = :precio")
    , @NamedQuery(name = "Pelicula.findBySinopsis", query = "SELECT p FROM Pelicula p WHERE p.sinopsis = :sinopsis")
    , @NamedQuery(name = "Pelicula.findByCantidadCopias", query = "SELECT p FROM Pelicula p WHERE p.cantidadCopias = :cantidadCopias")})
public class Pelicula implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //ALTER TABLE public.pelicula ALTER COLUMN precio TYPE numeric(12,2);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pelicula")
    private Integer idPelicula;
    
    @Column(name = "titulo")
    private String titulo;
    
    @Column(name = "anio")
    private Short anio;
    
    @Column(name = "duracion")
    private String duracion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    @Column(name = "precio", columnDefinition = "numeric(12,2)")
    private BigDecimal precio;
    
    @Column(name = "sinopsis")
    private String sinopsis;
    
    @Column(name = "cantidad_copias")
    private Short cantidadCopias;
    
    @ManyToMany(mappedBy = "peliculaCollection")
    private Collection<Actor> actorCollection;
    
    @JoinColumn(name = "id_genero", referencedColumnName = "id_genero")
    @ManyToOne
    private Genero idGenero;
    
    @JoinColumn(name = "id_idioma", referencedColumnName = "id_idioma")
    @ManyToOne
    private Idioma idIdioma;
    
    @JoinColumn(name = "id_pais", referencedColumnName = "id_pais")
    @ManyToOne
    private Pais idPais;
    
    @JoinColumn(name = "id_tipoformato", referencedColumnName = "id_tipoformato")
    @ManyToOne
    private TipoFormato idTipoformato;
    
    @OneToMany(mappedBy = "idPelicula")
    private Collection<VentaPelicula> ventaPeliculaCollection;

    public Pelicula() {
    }

    public Pelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Short getAnio() {
        return anio;
    }

    public void setAnio(Short anio) {
        this.anio = anio;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Short getCantidadCopias() {
        return cantidadCopias;
    }

    public void setCantidadCopias(Short cantidadCopias) {
        this.cantidadCopias = cantidadCopias;
    }

    @XmlTransient
    public Collection<Actor> getActorCollection() {
        return actorCollection;
    }

    public void setActorCollection(Collection<Actor> actorCollection) {
        this.actorCollection = actorCollection;
    }

    public Genero getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Genero idGenero) {
        this.idGenero = idGenero;
    }

    public Idioma getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(Idioma idIdioma) {
        this.idIdioma = idIdioma;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    public TipoFormato getIdTipoformato() {
        return idTipoformato;
    }

    public void setIdTipoformato(TipoFormato idTipoformato) {
        this.idTipoformato = idTipoformato;
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
        hash += (idPelicula != null ? idPelicula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pelicula)) {
            return false;
        }
        Pelicula other = (Pelicula) object;
        if ((this.idPelicula == null && other.idPelicula != null) || (this.idPelicula != null && !this.idPelicula.equals(other.idPelicula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Pelicula[ idPelicula=" + idPelicula + " ]";
    }
    
}
