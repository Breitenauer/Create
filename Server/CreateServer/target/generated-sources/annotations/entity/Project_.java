package entity;

import java.net.URL;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public abstract class Project_ {

	public static volatile SingularAttribute<Project, String> description;
	public static volatile SingularAttribute<Project, Boolean> isPublic;
	public static volatile SingularAttribute<Project, Video> video;
	public static volatile SingularAttribute<Project, Integer> projectId;
	public static volatile SingularAttribute<Project, URL> thumbnailUrl;
	public static volatile SingularAttribute<Project, Player> player;
	public static volatile ListAttribute<Project, Interaction> interactions;

}

