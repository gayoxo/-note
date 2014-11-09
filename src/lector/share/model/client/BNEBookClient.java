package lector.share.model.client;

import java.io.Serializable;

public class BNEBookClient extends RemoteBookClient implements Serializable {
	private static final long serialVersionUID = 1L;

	private String url;

	public BNEBookClient() {
		super("BNE LIBRARY");
	}

	public BNEBookClient(String author, String ISBN, String pagesCount,
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
