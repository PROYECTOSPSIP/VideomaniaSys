/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
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
import modelo.Genero;
import modelo.Pelicula;

public class GeneroController implements Serializable {

	private static final long serialVersionUID = 1L;

	public GeneroController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genero genero) {
        if (genero.getPeliculaCollection() == null) {
            genero.setPeliculaCollection(new ArrayList<Pelicula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pelicula> attachedPeliculaCollection = new ArrayList<Pelicula>();
            for (Pelicula peliculaCollectionPeliculaToAttach : genero.getPeliculaCollection()) {
                peliculaCollectionPeliculaToAttach = em.getReference(peliculaCollectionPeliculaToAttach.getClass(), peliculaCollectionPeliculaToAttach.getIdPelicula());
                attachedPeliculaCollection.add(peliculaCollectionPeliculaToAttach);
            }
            genero.setPeliculaCollection(attachedPeliculaCollection);
            em.persist(genero);
            for (Pelicula peliculaCollectionPelicula : genero.getPeliculaCollection()) {
                Genero oldIdGeneroOfPeliculaCollectionPelicula = peliculaCollectionPelicula.getIdGenero();
                peliculaCollectionPelicula.setIdGenero(genero);
                peliculaCollectionPelicula = em.merge(peliculaCollectionPelicula);
                if (oldIdGeneroOfPeliculaCollectionPelicula != null) {
                    oldIdGeneroOfPeliculaCollectionPelicula.getPeliculaCollection().remove(peliculaCollectionPelicula);
                    oldIdGeneroOfPeliculaCollectionPelicula = em.merge(oldIdGeneroOfPeliculaCollectionPelicula);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genero genero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero persistentGenero = em.find(Genero.class, genero.getIdGenero());
            Collection<Pelicula> peliculaCollectionOld = persistentGenero.getPeliculaCollection();
            Collection<Pelicula> peliculaCollectionNew = genero.getPeliculaCollection();
            Collection<Pelicula> attachedPeliculaCollectionNew = new ArrayList<Pelicula>();
            for (Pelicula peliculaCollectionNewPeliculaToAttach : peliculaCollectionNew) {
                peliculaCollectionNewPeliculaToAttach = em.getReference(peliculaCollectionNewPeliculaToAttach.getClass(), peliculaCollectionNewPeliculaToAttach.getIdPelicula());
                attachedPeliculaCollectionNew.add(peliculaCollectionNewPeliculaToAttach);
            }
            peliculaCollectionNew = attachedPeliculaCollectionNew;
            genero.setPeliculaCollection(peliculaCollectionNew);
            genero = em.merge(genero);
            for (Pelicula peliculaCollectionOldPelicula : peliculaCollectionOld) {
                if (!peliculaCollectionNew.contains(peliculaCollectionOldPelicula)) {
                    peliculaCollectionOldPelicula.setIdGenero(null);
                    peliculaCollectionOldPelicula = em.merge(peliculaCollectionOldPelicula);
                }
            }
            for (Pelicula peliculaCollectionNewPelicula : peliculaCollectionNew) {
                if (!peliculaCollectionOld.contains(peliculaCollectionNewPelicula)) {
                    Genero oldIdGeneroOfPeliculaCollectionNewPelicula = peliculaCollectionNewPelicula.getIdGenero();
                    peliculaCollectionNewPelicula.setIdGenero(genero);
                    peliculaCollectionNewPelicula = em.merge(peliculaCollectionNewPelicula);
                    if (oldIdGeneroOfPeliculaCollectionNewPelicula != null && !oldIdGeneroOfPeliculaCollectionNewPelicula.equals(genero)) {
                        oldIdGeneroOfPeliculaCollectionNewPelicula.getPeliculaCollection().remove(peliculaCollectionNewPelicula);
                        oldIdGeneroOfPeliculaCollectionNewPelicula = em.merge(oldIdGeneroOfPeliculaCollectionNewPelicula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = genero.getIdGenero();
                if (findGenero(id) == null) {
                    throw new NonexistentEntityException("The genero with id " + id + " no longer exists.");
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
            Genero genero;
            try {
                genero = em.getReference(Genero.class, id);
                genero.getIdGenero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genero with id " + id + " no longer exists.", enfe);
            }
            Collection<Pelicula> peliculaCollection = genero.getPeliculaCollection();
            for (Pelicula peliculaCollectionPelicula : peliculaCollection) {
                peliculaCollectionPelicula.setIdGenero(null);
                peliculaCollectionPelicula = em.merge(peliculaCollectionPelicula);
            }
            em.remove(genero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Genero> findGeneroEntities() {
        return findGeneroEntities(true, -1, -1);
    }

    public List<Genero> findGeneroEntities(int maxResults, int firstResult) {
        return findGeneroEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
	private List<Genero> findGeneroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genero.class));
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

    public Genero findGenero(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genero.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<Genero> rt = cq.from(Genero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
