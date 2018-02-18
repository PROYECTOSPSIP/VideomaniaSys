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
import modelo.Actor;
import modelo.Pelicula;

public class ActorController implements Serializable {

	private static final long serialVersionUID = 1L;

	public ActorController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actor actor) {
        if (actor.getPeliculaCollection() == null) {
            actor.setPeliculaCollection(new ArrayList<Pelicula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pelicula> attachedPeliculaCollection = new ArrayList<Pelicula>();
            for (Pelicula peliculaCollectionPeliculaToAttach : actor.getPeliculaCollection()) {
                peliculaCollectionPeliculaToAttach = em.getReference(peliculaCollectionPeliculaToAttach.getClass(), peliculaCollectionPeliculaToAttach.getIdPelicula());
                attachedPeliculaCollection.add(peliculaCollectionPeliculaToAttach);
            }
            actor.setPeliculaCollection(attachedPeliculaCollection);
            em.persist(actor);
            for (Pelicula peliculaCollectionPelicula : actor.getPeliculaCollection()) {
                peliculaCollectionPelicula.getActorCollection().add(actor);
                peliculaCollectionPelicula = em.merge(peliculaCollectionPelicula);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actor actor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actor persistentActor = em.find(Actor.class, actor.getIdActor());
            Collection<Pelicula> peliculaCollectionOld = persistentActor.getPeliculaCollection();
            Collection<Pelicula> peliculaCollectionNew = actor.getPeliculaCollection();
            Collection<Pelicula> attachedPeliculaCollectionNew = new ArrayList<Pelicula>();
            for (Pelicula peliculaCollectionNewPeliculaToAttach : peliculaCollectionNew) {
                peliculaCollectionNewPeliculaToAttach = em.getReference(peliculaCollectionNewPeliculaToAttach.getClass(), peliculaCollectionNewPeliculaToAttach.getIdPelicula());
                attachedPeliculaCollectionNew.add(peliculaCollectionNewPeliculaToAttach);
            }
            peliculaCollectionNew = attachedPeliculaCollectionNew;
            actor.setPeliculaCollection(peliculaCollectionNew);
            actor = em.merge(actor);
            for (Pelicula peliculaCollectionOldPelicula : peliculaCollectionOld) {
                if (!peliculaCollectionNew.contains(peliculaCollectionOldPelicula)) {
                    peliculaCollectionOldPelicula.getActorCollection().remove(actor);
                    peliculaCollectionOldPelicula = em.merge(peliculaCollectionOldPelicula);
                }
            }
            for (Pelicula peliculaCollectionNewPelicula : peliculaCollectionNew) {
                if (!peliculaCollectionOld.contains(peliculaCollectionNewPelicula)) {
                    peliculaCollectionNewPelicula.getActorCollection().add(actor);
                    peliculaCollectionNewPelicula = em.merge(peliculaCollectionNewPelicula);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actor.getIdActor();
                if (findActor(id) == null) {
                    throw new NonexistentEntityException("The actor with id " + id + " no longer exists.");
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
            Actor actor;
            try {
                actor = em.getReference(Actor.class, id);
                actor.getIdActor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actor with id " + id + " no longer exists.", enfe);
            }
            Collection<Pelicula> peliculaCollection = actor.getPeliculaCollection();
            for (Pelicula peliculaCollectionPelicula : peliculaCollection) {
                peliculaCollectionPelicula.getActorCollection().remove(actor);
                peliculaCollectionPelicula = em.merge(peliculaCollectionPelicula);
            }
            em.remove(actor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actor> findActorEntities() {
        return findActorEntities(true, -1, -1);
    }

    public List<Actor> findActorEntities(int maxResults, int firstResult) {
        return findActorEntities(false, maxResults, firstResult);
    }

    private List<Actor> findActorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actor.class));
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

    public Actor findActor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actor.class, id);
        } finally {
            em.close();
        }
    }

    public int getActorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actor> rt = cq.from(Actor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}