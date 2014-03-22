package me.machadolucas.avanto.db.dao;

import me.machadolucas.avanto.entities.WebAppUser;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

/**
 * @author lucas.machado
 * 
 */
public class WebAppUserDAO extends BasicDAO<WebAppUser, ObjectId> {

	public WebAppUserDAO(Class<WebAppUser> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	public WebAppUser findByEmail(String email) {
		Datastore ds = getDatastore();
		Query<WebAppUser> query = ds.createQuery(WebAppUser.class)
				.field("email").equal(email);

		return query.get();

	}
}
