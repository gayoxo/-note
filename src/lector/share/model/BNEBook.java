package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lector.share.model.Annotation;
import lector.share.model.Professor;

@Entity
@Table(name = "BNE_book")
public class BNEBook extends RemoteBook implements Serializable {
	private static final long serialVersionUID = 1L;

	private String url;

	public BNEBook() {
		super("BNE LIBRARY");
	}

	public BNEBook(String author, String ISBN, String pagesCount,
			String publishedYear, String title,String unscapedURL) {
		this();
		super.setAuthor(author);
		super.setISBN(ISBN);
		super.setPagesCount(pagesCount);
		super.setPublishedYear(publishedYear);
		super.setTitle(title);
		this.url = unscapedURL;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
