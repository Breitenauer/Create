package repository;

import entity.Game;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GameFacade extends AbstractFacade<Game> {

    @PersistenceContext(unitName = "createPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GameFacade() {
        super(Game.class);
    }
    
    public Game getGameOverLinkcode(String linkcode) {
        Game g = null;
        for (Game game :  findAll()) {
            if (linkcode.equals(game.getLinkcode())) {
                g = game;
            }
        }
        return g;
    }

}
