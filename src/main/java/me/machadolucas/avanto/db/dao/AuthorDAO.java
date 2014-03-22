package me.machadolucas.avanto.db.dao;

import me.machadolucas.avanto.entities.Author;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

/**
 * @author lucas.machado
 * 
 */
public class AuthorDAO extends BasicDAO<Author, ObjectId> {

	public AuthorDAO(Class<Author> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	public Author findById(String id) {

		Datastore ds = getDatastore();
		Query<Author> query = ds.createQuery(Author.class).field("_id")
				.equal(id);

		return query.get();
	}

}
