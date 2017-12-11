package entity;

import java.net.URL;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Video.class)
public abstract class Video_ {

	public static volatile SingularAttribute<Video, String> name;
	public static volatile SingularAttribute<Video, Integer> videoId;
	public static volatile SingularAttribute<Video, URL> url;

}

