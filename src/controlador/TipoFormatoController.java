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
import modelo.TipoFormato;

public class TipoFormatoController implements Serializable {

	private static final long serialVersionUID = 1L;

	public TipoFormatoController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoFormato tipoFormato) {
        if (tipoFormato.getPeliculaCollection() == null) {
            tipoFormato.setPeliculaCollection(new ArrayList<Pelicula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pelicula> attachedPeliculaCollection = new ArrayList<Pelicula>();
            for (Pelicula peliculaCollectionPeliculaToAttach : tipoFormato.getPeliculaCollection()) {
                peliculaCollectionPeliculaToAttach = em.getReference(peliculaCollectionPeliculaToAttach.getClass(), peliculaCollectionPeliculaToAttach.getIdPelicula());
                attachedPeliculaCollection.add(peliculaCollectionPeliculaToAttach);
            }
            tipoFormato.setPeliculaCollection(attachedPeliculaCollection);
            em.persist(tipoFormato);
            for (Pelicula peliculaCollectionPelicula : tipoFormato.getPeliculaCollection()) {
                TipoFormato oldIdTipoformatoOfPeliculaCollectionPelicula = peliculaCollectionPelicula.getIdTipoformato();
                peliculaCollectionPelicula.setIdTipoformato(tipoFormato);
                peliculaCollectionPelicula = em.merge(peliculaCollectionPelicula);
                if (oldIdTipoformatoOfPeliculaCollectionPelicula != null) {
                    oldIdTipoformatoOfPeliculaCollectionPelicula.getPeliculaCollection().remove(peliculaCollectionPelicula);
                    oldIdTipoformatoOfPeliculaCollectionPelicula = em.merge(oldIdTipoformatoOfPeliculaCollectionPelicula);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoFormato tipoFormato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoFormato persistentTipoFormato = em.find(TipoFormato.class, tipoFormato.getIdTipoformato());
            Collection<Pelicula> peliculaCollectionOld = persistentTipoFormato.getPeliculaCollection();
            Collection<Pelicula> peliculaCollectionNew = tipoFormato.getPeliculaCollection();
            Collection<Pelicula> attachedPeliculaCollectionNew = new ArrayList<Pelicula>();
            for (Pelicula peliculaCollectionNewPeliculaToAttach : peliculaCollectionNew) {
                peliculaCollectionNewPeliculaToAttach = em.getReference(peliculaCollectionNewPeliculaToAttach.getClass(), peliculaCollectionNewPeliculaToAttach.getIdPelicula());
                attachedPeliculaCollectionNew.add(peliculaCollectionNewPeliculaToAttach);
            }
            peliculaCollectionNew = attachedPeliculaCollectionNew;
            tipoFormato.setPeliculaCollection(peliculaCollectionNew);
            tipoFormato = em.merge(tipoFormato);
            for (Pelicula peliculaCollectionOldPelicula : peliculaCollectionOld) {
                if (!peliculaCollectionNew.contains(peliculaCollectionOldPelicula)) {
                    peliculaCollectionOldPelicula.setIdTipoformato(null);
                    peliculaCollectionOldPelicula = em.merge(peliculaCollectionOldPelicula);
                }
            }
            for (Pelicula peliculaCollectionNewPelicula : peliculaCollectionNew) {
                if (!peliculaCollectionOld.contains(peliculaCollectionNewPelicula)) {
                    TipoFormato oldIdTipoformatoOfPeliculaCollectionNewPelicula = peliculaCollectionNewPelicula.getIdTipoformato();
                    peliculaCollectionNewPelicula.setIdTipoformato(tipoFormato);
                    peliculaCollectionNewPelicula = em.merge(peliculaCollectionNewPelicula);
                    if (oldIdTipoformatoOfPeliculaCollectionNewPelicula != null && !oldIdTipoformatoOfPeliculaCollectionNewPelicula.equals(tipoFormato)) {
                        oldIdTipoformatoOfPeliculaCollectionNewPelicula.getPeliculaCollection().remove(peliculaCollectionNewPelicula);
                        oldIdTipoformatoOfPeliculaCollectionNewPelicula = em.merge(oldIdTipoformatoOfPeliculaCollectionNewPelicula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = tipoFormato.getIdTipoformato();
                if (findTipoFormato(id) == null) {
                    throw new NonexistentEntityException("The tipoFormato with id " + id + " no longer exists.");
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
            TipoFormato tipoFormato;
            try {
                tipoFormato = em.getReference(TipoFormato.class, id);
                tipoFormato.getIdTipoformato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoFormato with id " + id + " no longer exists.", enfe);
            }
            Collection<Pelicula> peliculaCollection = tipoFormato.getPeliculaCollection();
            for (Pelicula peliculaCollectionPelicula : peliculaCollection) {
                peliculaCollectionPelicula.setIdTipoformato(null);
                peliculaCollectionPelicula = em.merge(peliculaCollectionPelicula);
            }
            em.remove(tipoFormato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoFormato> findTipoFormatoEntities() {
        return findTipoFormatoEntities(true, -1, -1);
    }

    public List<TipoFormato> findTipoFormatoEntities(int maxResults, int firstResult) {
        return findTipoFormatoEntities(false, maxResults, firstResult);
    }

    private List<TipoFormato> findTipoFormatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoFormato.class));
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

    public TipoFormato findTipoFormato(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoFormato.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoFormatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoFormato> rt = cq.from(TipoFormato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
