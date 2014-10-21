package lector.client.controler;



import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Generador_Basico{

	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	

	public static void start() {
		bookReaderServiceHolder.saveSuper( new AsyncCallback<Void>() {

			public void onSuccess(Void result) {
			}

			public void onFailure(Throwable caught) {

			}
		});
		
	}

}
