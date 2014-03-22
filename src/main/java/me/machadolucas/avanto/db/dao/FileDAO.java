package me.machadolucas.avanto.db.dao;

import me.machadolucas.avanto.entities.File;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

/**
 * @author lucas.machado
 * 
 */
public class FileDAO extends BasicDAO<File, ObjectId> {

	public FileDAO(Class<File> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	public File findById(String id) {

		Datastore ds = getDatastore();
		Query<File> query = ds.createQuery(File.class).field("_id").equal(id);

		return query.get();
	}

}
