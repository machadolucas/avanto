package me.machadolucas.avanto.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.machadolucas.avanto.entities.Author;
import me.machadolucas.avanto.entities.Post;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * @author lucas.machado
 * 
 */
public class PostDAO extends BasicDAO<Post, ObjectId> {

	public PostDAO(Class<Post> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	public Post findIndividualByIdentifier(String identifier) {

		Datastore ds = getDatastore();
		Query<Post> query = ds.createQuery(Post.class).field("identificator")
				.equal(identifier);

		return query.get();
	}

	public static Map<String, Integer> loadTagsDistribution() {
		Map<String, Integer> map = new HashMap<String, Integer>();

		// TODO Auto-generated method stub
		return map;
	}

	public List<Post> findLastPosts(int numberOfLastPosts, String language) {

		Datastore ds = getDatastore();

		return ds.find(Post.class, "locale", language).limit(numberOfLastPosts)
				.asList();
	}

	public List<Post> findLastPosts(int numberOfLastPosts) {

		Datastore ds = getDatastore();

		return ds.find(Post.class, "status", "published").order("-date")
				.limit(numberOfLastPosts).asList();
	}

	public List<Post> findAllPublishedPosts() {

		Datastore ds = getDatastore();

		return ds.find(Post.class, "status", "published").order("-date")
				.asList();
	}

	public void deleteAuthorReferences(Author author) {

		Datastore ds = getDatastore();

		UpdateOperations<Post> ops = ds.createUpdateOperations(Post.class)
				.unset("author");
		ds.update(ds.createQuery(Post.class).field("author").equal(author), ops);
	}

}
