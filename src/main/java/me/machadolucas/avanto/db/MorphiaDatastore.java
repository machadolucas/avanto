package me.machadolucas.avanto.db;

import java.io.IOException;
import java.util.Properties;

import me.machadolucas.avanto.entities.Author;
import me.machadolucas.avanto.entities.Parameter;
import me.machadolucas.avanto.entities.Post;
import me.machadolucas.avanto.entities.WebAppUser;
import me.machadolucas.avanto.util.PropertiesLoader;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MappingException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Classe responsável por instanciar e manter a Datastore do Morphia, que faz
 * comunicação com o mongodb
 * 
 * @author lucasmachado
 * 
 */
public class MorphiaDatastore {

	private static Datastore DS;
	private static MongoClient mongo;
	private static Morphia morphia;
	private static Properties dbProperties;

	private static final String DB_ADDRESS = "DB_ADDRESS";
	private static final String DB_PORT = "DB_PORT";
	private static final String DB_NAME = "DB_NAME";
	private static final String DB_PASSWORD = "DB_PASSWORD";
	private static final String DB_USER_NAME = "DB_USER_NAME";

	public static Datastore getDatabaseObject() {
		if (DS != null) {
			return DS;
		}

		try {
			dbProperties = PropertiesLoader
					.loadProperties("/me/machadolucas/blog/db/Database.properties");// FIXME
			String address = dbProperties.getProperty(DB_ADDRESS);
			String port = dbProperties.getProperty(DB_PORT);
			String dbname = dbProperties.getProperty(DB_NAME);
			String password = dbProperties.getProperty(DB_PASSWORD);
			String username = dbProperties.getProperty(DB_USER_NAME);

			String CONNECTION_URI = "mongodb://" + username + ":" + password
					+ "@" + address + ":" + port + "/" + dbname;

			if (mongo == null) {
				mongo = new MongoClient(new MongoClientURI(CONNECTION_URI));
				DB db = mongo.getDB(dbname);
				boolean auth = db
						.authenticate(username, password.toCharArray());
				if (!auth) {
					throw new Exception(
							"Production database authentication error");
				}
			}

			if (morphia == null) {
				morphia = new Morphia();
			}

			morphia.map(Post.class);
			morphia.map(Author.class);
			morphia.map(Parameter.class);
			morphia.map(WebAppUser.class);

			DS = morphia.createDatastore(mongo, dbname);

			System.out.println("Connected successfully to production database");

		} catch (IOException e) {
			e.printStackTrace();
			System.err
					.println("=== Error loading Database.properties file. Please check it");
		} catch (MappingException e) {
			e.printStackTrace();
			System.err.println("=== Error mapping entities to morphia.");

		} catch (Exception e) {

			mongo.close();

			System.err.println(e.getCause().toString());
			System.err
					.println("=== Could not connect to production database. Trying to connect to a localhost database.");

			try {
				mongo = new MongoClient();

				if (morphia == null) {
					morphia = new Morphia();
				}
				DS = morphia.createDatastore(mongo, "machadolucas");

				System.out
						.println("Connected successfully to localhost database");

			} catch (Exception e1) {
				mongo.close();
				System.err
						.println("=== Could not connect to local database. Please run a mongod instance");
				e1.printStackTrace();
				return null;
			}
		}
		return DS;
	}
}
