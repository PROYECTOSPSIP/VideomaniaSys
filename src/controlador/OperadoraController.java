/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cliente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Operadora;

/**
 *
 * @author elichinita58
 */
public class OperadoraController implements Serializable {

    public OperadoraController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Operadora operadora) {
        if (operadora.getClienteCollection() == null) {
            operadora.setClienteCollection(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : operadora.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getIdCliente());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            operadora.setClienteCollection(attachedClienteCollection);
            em.persist(operadora);
            for (Cliente clienteCollectionCliente : operadora.getClienteCollection()) {
                Operadora oldIdOperadoraOfClienteCollectionCliente = clienteCollectionCliente.getIdOperadora();
                clienteCollectionCliente.setIdOperadora(operadora);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldIdOperadoraOfClienteCollectionCliente != null) {
                    oldIdOperadoraOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldIdOperadoraOfClienteCollectionCliente = em.merge(oldIdOperadoraOfClienteCollectionCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Operadora operadora) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Operadora persistentOperadora = em.find(Operadora.class, operadora.getIdOperadora());
            Collection<Cliente> clienteCollectionOld = persistentOperadora.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = operadora.getClienteCollection();
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getIdCliente());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            operadora.setClienteCollection(clienteCollectionNew);
            operadora = em.merge(operadora);
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    clienteCollectionOldCliente.setIdOperadora(null);
                    clienteCollectionOldCliente = em.merge(clienteCollectionOldCliente);
                }
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    Operadora oldIdOperadoraOfClienteCollectionNewCliente = clienteCollectionNewCliente.getIdOperadora();
                    clienteCollectionNewCliente.setIdOperadora(operadora);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldIdOperadoraOfClienteCollectionNewCliente != null && !oldIdOperadoraOfClienteCollectionNewCliente.equals(operadora)) {
                        oldIdOperadoraOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldIdOperadoraOfClienteCollectionNewCliente = em.merge(oldIdOperadoraOfClienteCollectionNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = operadora.getIdOperadora();
                if (findOperadora(id) == null) {
                    throw new NonexistentEntityException("The operadora with id " + id + " no longer exists.");
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
            Operadora operadora;
            try {
                operadora = em.getReference(Operadora.class, id);
                operadora.getIdOperadora();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operadora with id " + id + " no longer exists.", enfe);
            }
            Collection<Cliente> clienteCollection = operadora.getClienteCollection();
            for (Cliente clienteCollectionCliente : clienteCollection) {
                clienteCollectionCliente.setIdOperadora(null);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
            }
            em.remove(operadora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Operadora> findOperadoraEntities() {
        return findOperadoraEntities(true, -1, -1);
    }

    public List<Operadora> findOperadoraEntities(int maxResults, int firstResult) {
        return findOperadoraEntities(false, maxResults, firstResult);
    }

    private List<Operadora> findOperadoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operadora.class));
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

    public Operadora findOperadora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Operadora.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperadoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Operadora> rt = cq.from(Operadora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Operadora> operadoraList(){
		
		//EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VideomaniaSys");
		EntityManager em = emf.createEntityManager();
		TypedQuery<Operadora> query = em.createNamedQuery("Operadora.findAll", Operadora.class);
		List<Operadora> operadoraList = query.getResultList();
		
		return operadoraList;
	}
    
}
