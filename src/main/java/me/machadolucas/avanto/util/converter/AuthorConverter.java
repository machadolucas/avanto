package me.machadolucas.avanto.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import me.machadolucas.avanto.db.MorphiaDatastore;
import me.machadolucas.avanto.db.dao.AuthorDAO;
import me.machadolucas.avanto.entities.Author;

@FacesConverter(forClass = Author.class, value = "authorConverter")
public class AuthorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value == null || value.toString().equals("")) {
			return null;
		}

		AuthorDAO authorDAO = new AuthorDAO(Author.class,
				MorphiaDatastore.getDatabaseObject());

		Author obj = authorDAO.findOne("email", value);

		return obj;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null || value.toString().equals("")) {
			return (String) value;
		}
		try {
			Author author = (Author) value;
			return author.getEmail();

		} catch (Exception e) {
			System.err.println("Cannot cast Author with value "
					+ value.toString());
			e.printStackTrace();
			return null;
		}
	}

}
