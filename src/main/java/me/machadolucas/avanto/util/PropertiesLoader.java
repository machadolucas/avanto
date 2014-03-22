package me.machadolucas.avanto.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class to load properties files into Java objects
 * 
 * @author lucasmachado
 * 
 */
public class PropertiesLoader {

	/**
	 * Load a properties file into a object and returns it
	 * 
	 * @param filePath
	 *            path of the properties file, relative to the classpath
	 * @return a {@link Properties} object
	 * @throws IOException
	 *             if the file is not found
	 */
	public static Properties loadProperties(String filePath) throws IOException {
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream(filePath);
		if (stream == null) {
			throw new IOException();
		}
		prop.load(stream);
		return prop;
	}
}
