/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Pelicula;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Idioma;

public class IdiomaController implements Serializable {

	private static final long serialVersionUID = 1L;

	public IdiomaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Idioma idioma) {
        if (idioma.getPeliculaCollection() == null) {
            idioma.setPeliculaCollection(new ArrayList<Pelicula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pelicula> attachedPeliculaCollection = new ArrayList<Pelicula>();
            for (Pelicula peliculaCollectionPeliculaToAttach : idioma.getPeliculaCollection()) {
                peliculaCollectionPeliculaToAttach = em.getReference(peliculaCollectionPeliculaToAttach.getClass(), peliculaCollectionPeliculaToAttach.getIdPelicula());
                attachedPeliculaCollection.add(peliculaCollectionPeliculaToAttach);
            }
            idioma.setPeliculaCollection(attachedPeliculaCollection);
            em.persist(idioma);
            for (Pelicula peliculaCollectionPelicula : idioma.getPeliculaCollection()) {
                Idioma oldIdIdiomaOfPeliculaCollectionPelicula = peliculaCollectionPelicula.getIdIdioma();
                peliculaCollectionPelicula.setIdIdioma(idioma);
                peliculaCollectionPelicula = em.merge(peliculaCollectionPelicula);
                if (oldIdIdiomaOfPeliculaCollectionPelicula != null) {
                    oldIdIdiomaOfPeliculaCollectionPelicula.getPeliculaCollection().remove(peliculaCollectionPelicula);
                    oldIdIdiomaOfPeliculaCollectionPelicula = em.merge(oldIdIdiomaOfPeliculaCollectionPelicula);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Idioma idioma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Idioma persistentIdioma = em.find(Idioma.class, idioma.getIdIdioma());
            Collection<Pelicula> peliculaCollectionOld = persistentIdioma.getPeliculaCollection();
            Collection<Pelicula> peliculaCollectionNew = idioma.getPeliculaCollection();
            Collection<Pelicula> attachedPeliculaCollectionNew = new ArrayList<Pelicula>();
            for (Pelicula peliculaCollectionNewPeliculaToAttach : peliculaCollectionNew) {
                peliculaCollectionNewPeliculaToAttach = em.getReference(peliculaCollectionNewPeliculaToAttach.getClass(), peliculaCollectionNewPeliculaToAttach.getIdPelicula());
                attachedPeliculaCollectionNew.add(peliculaCollectionNewPeliculaToAttach);
            }
            peliculaCollectionNew = attachedPeliculaCollectionNew;
            idioma.setPeliculaCollection(peliculaCollectionNew);
            idioma = em.merge(idioma);
            for (Pelicula peliculaCollectionOldPelicula : peliculaCollectionOld) {
                if (!peliculaCollectionNew.contains(peliculaCollectionOldPelicula)) {
                    peliculaCollectionOldPelicula.setIdIdioma(null);
                    peliculaCollectionOldPelicula = em.merge(peliculaCollectionOldPelicula);
                }
            }
            for (Pelicula peliculaCollectionNewPelicula : peliculaCollectionNew) {
                if (!peliculaCollectionOld.contains(peliculaCollectionNewPelicula)) {
                    Idioma oldIdIdiomaOfPeliculaCollectionNewPelicula = peliculaCollectionNewPelicula.getIdIdioma();
                    peliculaCollectionNewPelicula.setIdIdioma(idioma);
                    peliculaCollectionNewPelicula = em.merge(peliculaCollectionNewPelicula);
                    if (oldIdIdiomaOfPeliculaCollectionNewPelicula != null && !oldIdIdiomaOfPeliculaCollectionNewPelicula.equals(idioma)) {
                        oldIdIdiomaOfPeliculaCollectionNewPelicula.getPeliculaCollection().remove(peliculaCollectionNewPelicula);
                        oldIdIdiomaOfPeliculaCollectionNewPelicula = em.merge(oldIdIdiomaOfPeliculaCollectionNewPelicula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = idioma.getIdIdioma();
                if (findIdioma(id) == null) {
                    throw new NonexistentEntityException("The idioma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Idioma idioma;
            try {
                idioma = em.getReference(Idioma.class, id);
                idioma.getIdIdioma();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The idioma with id " + id + " no longer exists.", enfe);
            }
            Collection<Pelicula> peliculaCollection = idioma.getPeliculaCollection();
            for (Pelicula peliculaCollectionPelicula : peliculaCollection) {
                peliculaCollectionPelicula.setIdIdioma(null);
                peliculaCollectionPelicula = em.merge(peliculaCollectionPelicula);
            }
            em.remove(idioma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Idioma> findIdiomaEntities() {
        return findIdiomaEntities(true, -1, -1);
    }

    public List<Idioma> findIdiomaEntities(int maxResults, int firstResult) {
        return findIdiomaEntities(false, maxResults, firstResult);
    }

    private List<Idioma> findIdiomaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Idioma.class));
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

    public Idioma findIdioma(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Idioma.class, id);
        } finally {
            em.close();
        }
    }

    public int getIdiomaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Idioma> rt = cq.from(Idioma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
