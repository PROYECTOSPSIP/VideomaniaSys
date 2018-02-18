package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cliente;
import modelo.Pelicula;
import modelo.VentaPelicula;

public class VentaPeliculaController implements Serializable {

	private static final long serialVersionUID = 1L;

	public VentaPeliculaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentaPelicula ventaPelicula) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCliente = ventaPelicula.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdCliente());
                ventaPelicula.setIdCliente(idCliente);
            }
            Pelicula idPelicula = ventaPelicula.getIdPelicula();
            if (idPelicula != null) {
                idPelicula = em.getReference(idPelicula.getClass(), idPelicula.getIdPelicula());
                ventaPelicula.setIdPelicula(idPelicula);
            }
            em.persist(ventaPelicula);
            if (idCliente != null) {
                idCliente.getVentaPeliculaCollection().add(ventaPelicula);
                idCliente = em.merge(idCliente);
            }
            if (idPelicula != null) {
                idPelicula.getVentaPeliculaCollection().add(ventaPelicula);
                idPelicula = em.merge(idPelicula);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentaPelicula ventaPelicula) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VentaPelicula persistentVentaPelicula = em.find(VentaPelicula.class, ventaPelicula.getIdVentaPelicula());
            Cliente idClienteOld = persistentVentaPelicula.getIdCliente();
            Cliente idClienteNew = ventaPelicula.getIdCliente();
            Pelicula idPeliculaOld = persistentVentaPelicula.getIdPelicula();
            Pelicula idPeliculaNew = ventaPelicula.getIdPelicula();
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdCliente());
                ventaPelicula.setIdCliente(idClienteNew);
            }
            if (idPeliculaNew != null) {
                idPeliculaNew = em.getReference(idPeliculaNew.getClass(), idPeliculaNew.getIdPelicula());
                ventaPelicula.setIdPelicula(idPeliculaNew);
            }
            ventaPelicula = em.merge(ventaPelicula);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getVentaPeliculaCollection().remove(ventaPelicula);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getVentaPeliculaCollection().add(ventaPelicula);
                idClienteNew = em.merge(idClienteNew);
            }
            if (idPeliculaOld != null && !idPeliculaOld.equals(idPeliculaNew)) {
                idPeliculaOld.getVentaPeliculaCollection().remove(ventaPelicula);
                idPeliculaOld = em.merge(idPeliculaOld);
            }
            if (idPeliculaNew != null && !idPeliculaNew.equals(idPeliculaOld)) {
                idPeliculaNew.getVentaPeliculaCollection().add(ventaPelicula);
                idPeliculaNew = em.merge(idPeliculaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventaPelicula.getIdVentaPelicula();
                if (findVentaPelicula(id) == null) {
                    throw new NonexistentEntityException("The ventaPelicula with id " + id + " no longer exists.");
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
            VentaPelicula ventaPelicula;
            try {
                ventaPelicula = em.getReference(VentaPelicula.class, id);
                ventaPelicula.getIdVentaPelicula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventaPelicula with id " + id + " no longer exists.", enfe);
            }
            Cliente idCliente = ventaPelicula.getIdCliente();
            if (idCliente != null) {
                idCliente.getVentaPeliculaCollection().remove(ventaPelicula);
                idCliente = em.merge(idCliente);
            }
            Pelicula idPelicula = ventaPelicula.getIdPelicula();
            if (idPelicula != null) {
                idPelicula.getVentaPeliculaCollection().remove(ventaPelicula);
                idPelicula = em.merge(idPelicula);
            }
            em.remove(ventaPelicula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentaPelicula> findVentaPeliculaEntities() {
        return findVentaPeliculaEntities(true, -1, -1);
    }

    public List<VentaPelicula> findVentaPeliculaEntities(int maxResults, int firstResult) {
        return findVentaPeliculaEntities(false, maxResults, firstResult);
    }

    private List<VentaPelicula> findVentaPeliculaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentaPelicula.class));
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

    public VentaPelicula findVentaPelicula(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentaPelicula.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaPeliculaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentaPelicula> rt = cq.from(VentaPelicula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
