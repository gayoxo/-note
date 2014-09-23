package lector.client.reader.export;

import java.util.ArrayList;
import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.controler.ConstantsInformation;
import lector.client.reader.LoadingPanel;
import lector.share.model.ExportObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class arbitroLlamadasHTML {


	private ArrayList<ExportObject> Lista;

	static ImageServiceAsync imageServiceHolder = GWT
	.create(ImageService.class);
	
	public arbitroLlamadasHTML(ArrayList<ExportObject> list) {

	Lista=list;
	
	

	}


	public void llamadaBucle2() {
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		imageServiceHolder.loadHTMLStringForExport(Lista,
				new AsyncCallback<String>() {

					public void onSuccess(String result) {
						LoadingPanel.getInstance().hide();
						CrearHTML(result);
					}

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert("Error Exporting. please try again");
					}
				});
	}
	

	
	public void CrearHTML(String Value)
	{
		LoadingPanel.getInstance().hide();

		FormPanel formPanel = new FormPanel("_blank");
		formPanel
				.setEncoding(FormPanel.ENCODING_URLENCODED);
		formPanel.setMethod(FormPanel.METHOD_POST);
		TextArea textArea = new TextArea();
		textArea.setText(Value.toString());
		textArea.setSize("100%", "100%");
		textArea.setReadOnly(true);

		textArea.setName("html");
		textArea.getValue();
		VerticalPanel V=new VerticalPanel();

		V.add(textArea);
		V.setSize("100%", "100%");
		TextArea textArea2 = new TextArea();
		textArea2.setText(Long.toString(System.currentTimeMillis()));
		textArea2.setSize("100%", "100%");
		textArea2.setReadOnly(true);

		textArea2.setName("ExportN");
		textArea2.getValue();
		V.add(textArea2);
		formPanel
				.setAction("HTML.php");

		
		formPanel.add(V);
		RootPanel RP=RootPanel.get();
		formPanel.setVisible(false);
		RP.add(formPanel);
		Window.alert(ConstantsInformation.WAIT_RESULTS);
		formPanel.submit();

	}

}
