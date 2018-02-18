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
import modelo.Pais;

public class PaisController implements Serializable {

	private static final long serialVersionUID = 1L;

	public PaisController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) {
        if (pais.getPeliculaCollection() == null) {
            pais.setPeliculaCollection(new ArrayList<Pelicula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pelicula> attachedPeliculaCollection = new ArrayList<Pelicula>();
            for (Pelicula peliculaCollectionPeliculaToAttach : pais.getPeliculaCollection()) {
                peliculaCollectionPeliculaToAttach = em.getReference(peliculaCollectionPeliculaToAttach.getClass(), peliculaCollectionPeliculaToAttach.getIdPelicula());
                attachedPeliculaCollection.add(peliculaCollectionPeliculaToAttach);
            }
            pais.setPeliculaCollection(attachedPeliculaCollection);
            em.persist(pais);
            for (Pelicula peliculaCollectionPelicula : pais.getPeliculaCollection()) {
                Pais oldIdPaisOfPeliculaCollectionPelicula = peliculaCollectionPelicula.getIdPais();
                peliculaCollectionPelicula.setIdPais(pais);
                peliculaCollectionPelicula = em.merge(peliculaCollectionPelicula);
                if (oldIdPaisOfPeliculaCollectionPelicula != null) {
                    oldIdPaisOfPeliculaCollectionPelicula.getPeliculaCollection().remove(peliculaCollectionPelicula);
                    oldIdPaisOfPeliculaCollectionPelicula = em.merge(oldIdPaisOfPeliculaCollectionPelicula);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getIdPais());
            Collection<Pelicula> peliculaCollectionOld = persistentPais.getPeliculaCollection();
            Collection<Pelicula> peliculaCollectionNew = pais.getPeliculaCollection();
            Collection<Pelicula> attachedPeliculaCollectionNew = new ArrayList<Pelicula>();
            for (Pelicula peliculaCollectionNewPeliculaToAttach : peliculaCollectionNew) {
                peliculaCollectionNewPeliculaToAttach = em.getReference(peliculaCollectionNewPeliculaToAttach.getClass(), peliculaCollectionNewPeliculaToAttach.getIdPelicula());
                attachedPeliculaCollectionNew.add(peliculaCollectionNewPeliculaToAttach);
            }
            peliculaCollectionNew = attachedPeliculaCollectionNew;
            pais.setPeliculaCollection(peliculaCollectionNew);
            pais = em.merge(pais);
            for (Pelicula peliculaCollectionOldPelicula : peliculaCollectionOld) {
                if (!peliculaCollectionNew.contains(peliculaCollectionOldPelicula)) {
                    peliculaCollectionOldPelicula.setIdPais(null);
                    peliculaCollectionOldPelicula = em.merge(peliculaCollectionOldPelicula);
                }
            }
            for (Pelicula peliculaCollectionNewPelicula : peliculaCollectionNew) {
                if (!peliculaCollectionOld.contains(peliculaCollectionNewPelicula)) {
                    Pais oldIdPaisOfPeliculaCollectionNewPelicula = peliculaCollectionNewPelicula.getIdPais();
                    peliculaCollectionNewPelicula.setIdPais(pais);
                    peliculaCollectionNewPelicula = em.merge(peliculaCollectionNewPelicula);
                    if (oldIdPaisOfPeliculaCollectionNewPelicula != null && !oldIdPaisOfPeliculaCollectionNewPelicula.equals(pais)) {
                        oldIdPaisOfPeliculaCollectionNewPelicula.getPeliculaCollection().remove(peliculaCollectionNewPelicula);
                        oldIdPaisOfPeliculaCollectionNewPelicula = em.merge(oldIdPaisOfPeliculaCollectionNewPelicula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = pais.getIdPais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getIdPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            Collection<Pelicula> peliculaCollection = pais.getPeliculaCollection();
            for (Pelicula peliculaCollectionPelicula : peliculaCollection) {
                peliculaCollectionPelicula.setIdPais(null);
                peliculaCollectionPelicula = em.merge(peliculaCollectionPelicula);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
