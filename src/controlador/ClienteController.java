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
import modelo.Operadora;
import modelo.VentaPelicula;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cliente;

/**
 *
 * @author elichinita58
 */
public class ClienteController implements Serializable {

    public ClienteController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getVentaPeliculaCollection() == null) {
            cliente.setVentaPeliculaCollection(new ArrayList<VentaPelicula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Operadora idOperadora = cliente.getIdOperadora();
            if (idOperadora != null) {
                idOperadora = em.getReference(idOperadora.getClass(), idOperadora.getIdOperadora());
                cliente.setIdOperadora(idOperadora);
            }
            Collection<VentaPelicula> attachedVentaPeliculaCollection = new ArrayList<VentaPelicula>();
            for (VentaPelicula ventaPeliculaCollectionVentaPeliculaToAttach : cliente.getVentaPeliculaCollection()) {
                ventaPeliculaCollectionVentaPeliculaToAttach = em.getReference(ventaPeliculaCollectionVentaPeliculaToAttach.getClass(), ventaPeliculaCollectionVentaPeliculaToAttach.getIdVentaPelicula());
                attachedVentaPeliculaCollection.add(ventaPeliculaCollectionVentaPeliculaToAttach);
            }
            cliente.setVentaPeliculaCollection(attachedVentaPeliculaCollection);
            em.persist(cliente);
            if (idOperadora != null) {
                idOperadora.getClienteCollection().add(cliente);
                idOperadora = em.merge(idOperadora);
            }
            for (VentaPelicula ventaPeliculaCollectionVentaPelicula : cliente.getVentaPeliculaCollection()) {
                Cliente oldIdClienteOfVentaPeliculaCollectionVentaPelicula = ventaPeliculaCollectionVentaPelicula.getIdCliente();
                ventaPeliculaCollectionVentaPelicula.setIdCliente(cliente);
                ventaPeliculaCollectionVentaPelicula = em.merge(ventaPeliculaCollectionVentaPelicula);
                if (oldIdClienteOfVentaPeliculaCollectionVentaPelicula != null) {
                    oldIdClienteOfVentaPeliculaCollectionVentaPelicula.getVentaPeliculaCollection().remove(ventaPeliculaCollectionVentaPelicula);
                    oldIdClienteOfVentaPeliculaCollectionVentaPelicula = em.merge(oldIdClienteOfVentaPeliculaCollectionVentaPelicula);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdCliente());
            Operadora idOperadoraOld = persistentCliente.getIdOperadora();
            Operadora idOperadoraNew = cliente.getIdOperadora();
            Collection<VentaPelicula> ventaPeliculaCollectionOld = persistentCliente.getVentaPeliculaCollection();
            Collection<VentaPelicula> ventaPeliculaCollectionNew = cliente.getVentaPeliculaCollection();
            if (idOperadoraNew != null) {
                idOperadoraNew = em.getReference(idOperadoraNew.getClass(), idOperadoraNew.getIdOperadora());
                cliente.setIdOperadora(idOperadoraNew);
            }
            Collection<VentaPelicula> attachedVentaPeliculaCollectionNew = new ArrayList<VentaPelicula>();
            for (VentaPelicula ventaPeliculaCollectionNewVentaPeliculaToAttach : ventaPeliculaCollectionNew) {
                ventaPeliculaCollectionNewVentaPeliculaToAttach = em.getReference(ventaPeliculaCollectionNewVentaPeliculaToAttach.getClass(), ventaPeliculaCollectionNewVentaPeliculaToAttach.getIdVentaPelicula());
                attachedVentaPeliculaCollectionNew.add(ventaPeliculaCollectionNewVentaPeliculaToAttach);
            }
            ventaPeliculaCollectionNew = attachedVentaPeliculaCollectionNew;
            cliente.setVentaPeliculaCollection(ventaPeliculaCollectionNew);
            cliente = em.merge(cliente);
            if (idOperadoraOld != null && !idOperadoraOld.equals(idOperadoraNew)) {
                idOperadoraOld.getClienteCollection().remove(cliente);
                idOperadoraOld = em.merge(idOperadoraOld);
            }
            if (idOperadoraNew != null && !idOperadoraNew.equals(idOperadoraOld)) {
                idOperadoraNew.getClienteCollection().add(cliente);
                idOperadoraNew = em.merge(idOperadoraNew);
            }
            for (VentaPelicula ventaPeliculaCollectionOldVentaPelicula : ventaPeliculaCollectionOld) {
                if (!ventaPeliculaCollectionNew.contains(ventaPeliculaCollectionOldVentaPelicula)) {
                    ventaPeliculaCollectionOldVentaPelicula.setIdCliente(null);
                    ventaPeliculaCollectionOldVentaPelicula = em.merge(ventaPeliculaCollectionOldVentaPelicula);
                }
            }
            for (VentaPelicula ventaPeliculaCollectionNewVentaPelicula : ventaPeliculaCollectionNew) {
                if (!ventaPeliculaCollectionOld.contains(ventaPeliculaCollectionNewVentaPelicula)) {
                    Cliente oldIdClienteOfVentaPeliculaCollectionNewVentaPelicula = ventaPeliculaCollectionNewVentaPelicula.getIdCliente();
                    ventaPeliculaCollectionNewVentaPelicula.setIdCliente(cliente);
                    ventaPeliculaCollectionNewVentaPelicula = em.merge(ventaPeliculaCollectionNewVentaPelicula);
                    if (oldIdClienteOfVentaPeliculaCollectionNewVentaPelicula != null && !oldIdClienteOfVentaPeliculaCollectionNewVentaPelicula.equals(cliente)) {
                        oldIdClienteOfVentaPeliculaCollectionNewVentaPelicula.getVentaPeliculaCollection().remove(ventaPeliculaCollectionNewVentaPelicula);
                        oldIdClienteOfVentaPeliculaCollectionNewVentaPelicula = em.merge(oldIdClienteOfVentaPeliculaCollectionNewVentaPelicula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Operadora idOperadora = cliente.getIdOperadora();
            if (idOperadora != null) {
                idOperadora.getClienteCollection().remove(cliente);
                idOperadora = em.merge(idOperadora);
            }
            Collection<VentaPelicula> ventaPeliculaCollection = cliente.getVentaPeliculaCollection();
            for (VentaPelicula ventaPeliculaCollectionVentaPelicula : ventaPeliculaCollection) {
                ventaPeliculaCollectionVentaPelicula.setIdCliente(null);
                ventaPeliculaCollectionVentaPelicula = em.merge(ventaPeliculaCollectionVentaPelicula);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
