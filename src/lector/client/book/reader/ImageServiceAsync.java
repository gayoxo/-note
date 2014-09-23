/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;
import java.util.List;

import lector.share.model.ExportObject;
import lector.share.model.client.BookClient;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author Cesar y Gayoso.
 */
public interface ImageServiceAsync {

	void loadBookById(Long id, AsyncCallback<BookClient> callback);

	void getBookByUserId(Long userAppId,
			AsyncCallback<List<BookClient>> callback);

	void loadHTMLStringForExportUni(ExportObject exportObject,
			AsyncCallback<String> callback);

	void loadRTFStringForExportUni(ExportObject exportObject,
			AsyncCallback<String> callback);

	void loadHTMLStringForExport(ArrayList<ExportObject> lista,
			AsyncCallback<String> asyncCallback);

	void loadRTFStringForExport(ArrayList<ExportObject> lista,
			AsyncCallback<String> asyncCallback);

}
