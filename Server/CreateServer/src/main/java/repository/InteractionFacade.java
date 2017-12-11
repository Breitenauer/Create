package repository;

import entity.Interaction;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class InteractionFacade extends AbstractFacade<Interaction> {

    @PersistenceContext(unitName = "createPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InteractionFacade() {
        super(Interaction.class);
    }
    
}
