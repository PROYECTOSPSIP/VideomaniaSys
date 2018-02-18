/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import controlador.exceptions.NonexistentEntityException;
import modelo.Actor;
import modelo.Genero;
import modelo.Idioma;
import modelo.Pais;
import modelo.Pelicula;
import modelo.TipoFormato;
import modelo.VentaPelicula;

/**
 *
 * @author eduardo
 */
public class PeliculaController implements Serializable {
	
	Connection conn = null;
	
    public PeliculaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pelicula pelicula) {
        if (pelicula.getActorCollection() == null) {
            pelicula.setActorCollection(new ArrayList<Actor>());
        }
        if (pelicula.getVentaPeliculaCollection() == null) {
            pelicula.setVentaPeliculaCollection(new ArrayList<VentaPelicula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero idGenero = pelicula.getIdGenero();
            if (idGenero != null) {
                idGenero = em.getReference(idGenero.getClass(), idGenero.getIdGenero());
                pelicula.setIdGenero(idGenero);
            }
            Idioma idIdioma = pelicula.getIdIdioma();
            if (idIdioma != null) {
                idIdioma = em.getReference(idIdioma.getClass(), idIdioma.getIdIdioma());
                pelicula.setIdIdioma(idIdioma);
            }
            Pais idPais = pelicula.getIdPais();
            if (idPais != null) {
                idPais = em.getReference(idPais.getClass(), idPais.getIdPais());
                pelicula.setIdPais(idPais);
            }
            TipoFormato idTipoformato = pelicula.getIdTipoformato();
            if (idTipoformato != null) {
                idTipoformato = em.getReference(idTipoformato.getClass(), idTipoformato.getIdTipoformato());
                pelicula.setIdTipoformato(idTipoformato);
            }
            Collection<Actor> attachedActorCollection = new ArrayList<Actor>();
            for (Actor actorCollectionActorToAttach : pelicula.getActorCollection()) {
                actorCollectionActorToAttach = em.getReference(actorCollectionActorToAttach.getClass(), actorCollectionActorToAttach.getIdActor());
                attachedActorCollection.add(actorCollectionActorToAttach);
            }
            pelicula.setActorCollection(attachedActorCollection);
            Collection<VentaPelicula> attachedVentaPeliculaCollection = new ArrayList<VentaPelicula>();
            for (VentaPelicula ventaPeliculaCollectionVentaPeliculaToAttach : pelicula.getVentaPeliculaCollection()) {
                ventaPeliculaCollectionVentaPeliculaToAttach = em.getReference(ventaPeliculaCollectionVentaPeliculaToAttach.getClass(), ventaPeliculaCollectionVentaPeliculaToAttach.getIdVentaPelicula());
                attachedVentaPeliculaCollection.add(ventaPeliculaCollectionVentaPeliculaToAttach);
            }
            pelicula.setVentaPeliculaCollection(attachedVentaPeliculaCollection);
            em.persist(pelicula);
            if (idGenero != null) {
                idGenero.getPeliculaCollection().add(pelicula);
                idGenero = em.merge(idGenero);
            }
            if (idIdioma != null) {
                idIdioma.getPeliculaCollection().add(pelicula);
                idIdioma = em.merge(idIdioma);
            }
            if (idPais != null) {
                idPais.getPeliculaCollection().add(pelicula);
                idPais = em.merge(idPais);
            }
            if (idTipoformato != null) {
                idTipoformato.getPeliculaCollection().add(pelicula);
                idTipoformato = em.merge(idTipoformato);
            }
            for (Actor actorCollectionActor : pelicula.getActorCollection()) {
                actorCollectionActor.getPeliculaCollection().add(pelicula);
                actorCollectionActor = em.merge(actorCollectionActor);
            }
            for (VentaPelicula ventaPeliculaCollectionVentaPelicula : pelicula.getVentaPeliculaCollection()) {
                Pelicula oldIdPeliculaOfVentaPeliculaCollectionVentaPelicula = ventaPeliculaCollectionVentaPelicula.getIdPelicula();
                ventaPeliculaCollectionVentaPelicula.setIdPelicula(pelicula);
                ventaPeliculaCollectionVentaPelicula = em.merge(ventaPeliculaCollectionVentaPelicula);
                if (oldIdPeliculaOfVentaPeliculaCollectionVentaPelicula != null) {
                    oldIdPeliculaOfVentaPeliculaCollectionVentaPelicula.getVentaPeliculaCollection().remove(ventaPeliculaCollectionVentaPelicula);
                    oldIdPeliculaOfVentaPeliculaCollectionVentaPelicula = em.merge(oldIdPeliculaOfVentaPeliculaCollectionVentaPelicula);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pelicula pelicula) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pelicula persistentPelicula = em.find(Pelicula.class, pelicula.getIdPelicula());
            Genero idGeneroOld = persistentPelicula.getIdGenero();
            Genero idGeneroNew = pelicula.getIdGenero();
            Idioma idIdiomaOld = persistentPelicula.getIdIdioma();
            Idioma idIdiomaNew = pelicula.getIdIdioma();
            Pais idPaisOld = persistentPelicula.getIdPais();
            Pais idPaisNew = pelicula.getIdPais();
            TipoFormato idTipoformatoOld = persistentPelicula.getIdTipoformato();
            TipoFormato idTipoformatoNew = pelicula.getIdTipoformato();
            Collection<Actor> actorCollectionOld = persistentPelicula.getActorCollection();
            Collection<Actor> actorCollectionNew = pelicula.getActorCollection();
            Collection<VentaPelicula> ventaPeliculaCollectionOld = persistentPelicula.getVentaPeliculaCollection();
            Collection<VentaPelicula> ventaPeliculaCollectionNew = pelicula.getVentaPeliculaCollection();
            if (idGeneroNew != null) {
                idGeneroNew = em.getReference(idGeneroNew.getClass(), idGeneroNew.getIdGenero());
                pelicula.setIdGenero(idGeneroNew);
            }
            if (idIdiomaNew != null) {
                idIdiomaNew = em.getReference(idIdiomaNew.getClass(), idIdiomaNew.getIdIdioma());
                pelicula.setIdIdioma(idIdiomaNew);
            }
            if (idPaisNew != null) {
                idPaisNew = em.getReference(idPaisNew.getClass(), idPaisNew.getIdPais());
                pelicula.setIdPais(idPaisNew);
            }
            if (idTipoformatoNew != null) {
                idTipoformatoNew = em.getReference(idTipoformatoNew.getClass(), idTipoformatoNew.getIdTipoformato());
                pelicula.setIdTipoformato(idTipoformatoNew);
            }
            Collection<Actor> attachedActorCollectionNew = new ArrayList<Actor>();
            for (Actor actorCollectionNewActorToAttach : actorCollectionNew) {
                actorCollectionNewActorToAttach = em.getReference(actorCollectionNewActorToAttach.getClass(), actorCollectionNewActorToAttach.getIdActor());
                attachedActorCollectionNew.add(actorCollectionNewActorToAttach);
            }
            actorCollectionNew = attachedActorCollectionNew;
            pelicula.setActorCollection(actorCollectionNew);
            Collection<VentaPelicula> attachedVentaPeliculaCollectionNew = new ArrayList<VentaPelicula>();
            for (VentaPelicula ventaPeliculaCollectionNewVentaPeliculaToAttach : ventaPeliculaCollectionNew) {
                ventaPeliculaCollectionNewVentaPeliculaToAttach = em.getReference(ventaPeliculaCollectionNewVentaPeliculaToAttach.getClass(), ventaPeliculaCollectionNewVentaPeliculaToAttach.getIdVentaPelicula());
                attachedVentaPeliculaCollectionNew.add(ventaPeliculaCollectionNewVentaPeliculaToAttach);
            }
            ventaPeliculaCollectionNew = attachedVentaPeliculaCollectionNew;
            pelicula.setVentaPeliculaCollection(ventaPeliculaCollectionNew);
            pelicula = em.merge(pelicula);
            if (idGeneroOld != null && !idGeneroOld.equals(idGeneroNew)) {
                idGeneroOld.getPeliculaCollection().remove(pelicula);
                idGeneroOld = em.merge(idGeneroOld);
            }
            if (idGeneroNew != null && !idGeneroNew.equals(idGeneroOld)) {
                idGeneroNew.getPeliculaCollection().add(pelicula);
                idGeneroNew = em.merge(idGeneroNew);
            }
            if (idIdiomaOld != null && !idIdiomaOld.equals(idIdiomaNew)) {
                idIdiomaOld.getPeliculaCollection().remove(pelicula);
                idIdiomaOld = em.merge(idIdiomaOld);
            }
            if (idIdiomaNew != null && !idIdiomaNew.equals(idIdiomaOld)) {
                idIdiomaNew.getPeliculaCollection().add(pelicula);
                idIdiomaNew = em.merge(idIdiomaNew);
            }
            if (idPaisOld != null && !idPaisOld.equals(idPaisNew)) {
                idPaisOld.getPeliculaCollection().remove(pelicula);
                idPaisOld = em.merge(idPaisOld);
            }
            if (idPaisNew != null && !idPaisNew.equals(idPaisOld)) {
                idPaisNew.getPeliculaCollection().add(pelicula);
                idPaisNew = em.merge(idPaisNew);
            }
            if (idTipoformatoOld != null && !idTipoformatoOld.equals(idTipoformatoNew)) {
                idTipoformatoOld.getPeliculaCollection().remove(pelicula);
                idTipoformatoOld = em.merge(idTipoformatoOld);
            }
            if (idTipoformatoNew != null && !idTipoformatoNew.equals(idTipoformatoOld)) {
                idTipoformatoNew.getPeliculaCollection().add(pelicula);
                idTipoformatoNew = em.merge(idTipoformatoNew);
            }
            for (Actor actorCollectionOldActor : actorCollectionOld) {
                if (!actorCollectionNew.contains(actorCollectionOldActor)) {
                    actorCollectionOldActor.getPeliculaCollection().remove(pelicula);
                    actorCollectionOldActor = em.merge(actorCollectionOldActor);
                }
            }
            for (Actor actorCollectionNewActor : actorCollectionNew) {
                if (!actorCollectionOld.contains(actorCollectionNewActor)) {
                    actorCollectionNewActor.getPeliculaCollection().add(pelicula);
                    actorCollectionNewActor = em.merge(actorCollectionNewActor);
                }
            }
            for (VentaPelicula ventaPeliculaCollectionOldVentaPelicula : ventaPeliculaCollectionOld) {
                if (!ventaPeliculaCollectionNew.contains(ventaPeliculaCollectionOldVentaPelicula)) {
                    ventaPeliculaCollectionOldVentaPelicula.setIdPelicula(null);
                    ventaPeliculaCollectionOldVentaPelicula = em.merge(ventaPeliculaCollectionOldVentaPelicula);
                }
            }
            for (VentaPelicula ventaPeliculaCollectionNewVentaPelicula : ventaPeliculaCollectionNew) {
                if (!ventaPeliculaCollectionOld.contains(ventaPeliculaCollectionNewVentaPelicula)) {
                    Pelicula oldIdPeliculaOfVentaPeliculaCollectionNewVentaPelicula = ventaPeliculaCollectionNewVentaPelicula.getIdPelicula();
                    ventaPeliculaCollectionNewVentaPelicula.setIdPelicula(pelicula);
                    ventaPeliculaCollectionNewVentaPelicula = em.merge(ventaPeliculaCollectionNewVentaPelicula);
                    if (oldIdPeliculaOfVentaPeliculaCollectionNewVentaPelicula != null && !oldIdPeliculaOfVentaPeliculaCollectionNewVentaPelicula.equals(pelicula)) {
                        oldIdPeliculaOfVentaPeliculaCollectionNewVentaPelicula.getVentaPeliculaCollection().remove(ventaPeliculaCollectionNewVentaPelicula);
                        oldIdPeliculaOfVentaPeliculaCollectionNewVentaPelicula = em.merge(oldIdPeliculaOfVentaPeliculaCollectionNewVentaPelicula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pelicula.getIdPelicula();
                if (findPelicula(id) == null) {
                    throw new NonexistentEntityException("The pelicula with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pelicula pelicula;
            try {
                pelicula = em.getReference(Pelicula.class, id);
                pelicula.getIdPelicula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pelicula with id " + id + " no longer exists.", enfe);
            }
            Genero idGenero = pelicula.getIdGenero();
            if (idGenero != null) {
                idGenero.getPeliculaCollection().remove(pelicula);
                idGenero = em.merge(idGenero);
            }
            Idioma idIdioma = pelicula.getIdIdioma();
            if (idIdioma != null) {
                idIdioma.getPeliculaCollection().remove(pelicula);
                idIdioma = em.merge(idIdioma);
            }
            Pais idPais = pelicula.getIdPais();
            if (idPais != null) {
                idPais.getPeliculaCollection().remove(pelicula);
                idPais = em.merge(idPais);
            }
            TipoFormato idTipoformato = pelicula.getIdTipoformato();
            if (idTipoformato != null) {
                idTipoformato.getPeliculaCollection().remove(pelicula);
                idTipoformato = em.merge(idTipoformato);
            }
            Collection<Actor> actorCollection = pelicula.getActorCollection();
            for (Actor actorCollectionActor : actorCollection) {
                actorCollectionActor.getPeliculaCollection().remove(pelicula);
                actorCollectionActor = em.merge(actorCollectionActor);
            }
            Collection<VentaPelicula> ventaPeliculaCollection = pelicula.getVentaPeliculaCollection();
            for (VentaPelicula ventaPeliculaCollectionVentaPelicula : ventaPeliculaCollection) {
                ventaPeliculaCollectionVentaPelicula.setIdPelicula(null);
                ventaPeliculaCollectionVentaPelicula = em.merge(ventaPeliculaCollectionVentaPelicula);
            }
            em.remove(pelicula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pelicula> findPeliculaEntities() {
        return findPeliculaEntities(true, -1, -1);
    }

    public List<Pelicula> findPeliculaEntities(int maxResults, int firstResult) {
        return findPeliculaEntities(false, maxResults, firstResult);
    }

    private List<Pelicula> findPeliculaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pelicula.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pelicula findPelicula(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pelicula.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeliculaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pelicula> rt = cq.from(Pelicula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
  //SE DEBE UTILIZAR PERSITENCIA 
    /*public void ingresarPeliculaActor(int idActor, int idPelicula){
        try {
        	ParametrosConexion parametrosConexion = new ParametrosConexion();
			conn = parametrosConexion.conectar();
        			
        	String sql = "INSERT INTO pelicula_actor(id_actor, id_pelicula) VALUES(" + idActor + ", " + idPelicula + ")";
        	
        	PreparedStatement preparedStatement = conn.prepareStatement(sql);
		    
        	preparedStatement.executeQuery();

            conn.close();
        } 
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }*/
}
