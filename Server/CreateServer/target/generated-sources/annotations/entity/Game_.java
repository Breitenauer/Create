package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Game.class)
public abstract class Game_ {

	public static volatile SingularAttribute<Game, Integer> gameId;
	public static volatile SingularAttribute<Game, String> linkcode;
	public static volatile SingularAttribute<Game, Project> actProject;
	public static volatile SingularAttribute<Game, Player> actPlayer;

}

