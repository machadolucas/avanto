package me.machadolucas.avanto.db.dao;

import me.machadolucas.avanto.entities.Parameter;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

/**
 * @author lucas.machado
 * 
 */
public class ParameterDAO extends BasicDAO<Parameter, ObjectId> {

	public ParameterDAO(Class<Parameter> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	public Parameter findById(String id) {

		Datastore ds = getDatastore();
		Query<Parameter> query = ds.createQuery(Parameter.class).field("_id")
				.equal(id);

		return query.get();
	}

}
