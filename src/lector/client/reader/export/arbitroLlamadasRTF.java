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

public class arbitroLlamadasRTF {

	private ArrayList<ExportObject> Lista;
	private FormPanel formPanel;
//	private Button Submit;
	static ImageServiceAsync imageServiceHolder = GWT
	.create(ImageService.class);
	
	public arbitroLlamadasRTF(ArrayList<ExportObject> list) {
	Lista=list;
		}

	
	public void llamadaBucle2() {
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		imageServiceHolder.loadRTFStringForExport(Lista,
				new AsyncCallback<String>() {

					public void onSuccess(String result) {
						LoadingPanel.getInstance().hide();
						CrearRTF(result);
					}

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert("Error Exporting. please try again");

					}
				});
		
	}
	

	public void CrearRTF(String Texto){
		LoadingPanel.getInstance().hide();
		

			formPanel = new FormPanel("_blank");
			formPanel
			.setAction("RTF.php");
			formPanel
					.setEncoding(FormPanel.ENCODING_URLENCODED);
			formPanel.setMethod(FormPanel.METHOD_POST);
			TextArea textArea = new TextArea();
			textArea.setText(Texto);
			textArea.setSize("100%", "100%");
			textArea.setReadOnly(true);

			textArea.setName("html");
			textArea.getValue();

			VerticalPanel V=new VerticalPanel();		
			V.add(textArea);
			V.setSize("100%", "100%");
			
			TextArea textArea2 = new TextArea();
			textArea2.setSize("100%", "100%");
			textArea2.setReadOnly(true);
			textArea2.setText(Long.toString(System.currentTimeMillis()));

			textArea2.setName("ExportN");
			textArea2.getValue();
			

			V.add(textArea2);



			formPanel.add(V);

			RootPanel RP=RootPanel.get();
			formPanel.setVisible(false);
			RP.add(formPanel);
			Window.alert(ConstantsInformation.WAIT_RESULTS);
			formPanel.submit();

		
	}

}
