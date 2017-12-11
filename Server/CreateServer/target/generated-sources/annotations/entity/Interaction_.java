package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Interaction.class)
public abstract class Interaction_ {

	public static volatile SingularAttribute<Interaction, Integer> interactionId;
	public static volatile SingularAttribute<Interaction, Integer> timestampToB;
	public static volatile SingularAttribute<Interaction, Integer> timestampToA;
	public static volatile SingularAttribute<Interaction, String> methodName;
	public static volatile SingularAttribute<Interaction, Boolean> doesVideoEnd;
	public static volatile SingularAttribute<Interaction, Integer> timestamp;

}

