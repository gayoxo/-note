/**
 * 
 */
package lector.client.admin.book.BNEAPI;

import lector.client.admin.book.googleAPI.VisorSearcherGoogleBookPopupPanel;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;
import lector.share.model.BNEBook;
import lector.share.model.client.BNEBookClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public class SearcherBNEEntryPoint implements EntryPoint {

	private static final String TEXT_CAN_NOT_BE_EMPTY = "Text Can not be empty";
	private RootPanel rootPanel;
	private DockLayoutPanel PanelFondoGeneral;
	private MenuItem GoogleLoaderWellcomeMenuItem;
	private MenuBar menuBar = new MenuBar(false);
	private MenuItem BackMenuItem;
	private MenuItemSeparator separator = new MenuItemSeparator();	
	
	
	private static String BNE_SYSTEM_MENUITEM = "BNE loader system";
	private static String BACK_MENUITEM="Back";
	private VerticalPanel verticalPanel;
	private HorizontalPanel AzulPanel;
	private TextBox textBox;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@Override
	public void onModuleLoad() {
		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");
		rootPanel.setStyleName("Root");
//		RootPanel RootMenu = RootPanel.get("Menu");
//		RootMenu.setStyleName("Root");

		PanelFondoGeneral = new DockLayoutPanel(Unit.PX);
		PanelFondoGeneral.setStyleName("fondoLogo");
		rootPanel.add(PanelFondoGeneral, 0, 0);
		PanelFondoGeneral.setSize("100%", "100%");
		
		menuBar = new MenuBar(false);
		PanelFondoGeneral.addNorth(menuBar,25);

		
		GoogleLoaderWellcomeMenuItem = new MenuItem(BNE_SYSTEM_MENUITEM,
				false, (Command) null);
		GoogleLoaderWellcomeMenuItem.setHTML(BNE_SYSTEM_MENUITEM);
		GoogleLoaderWellcomeMenuItem.setEnabled(false);
		menuBar.addItem(GoogleLoaderWellcomeMenuItem);

		menuBar.addSeparator(separator);

		BackMenuItem = new MenuItem(BACK_MENUITEM, false, new Command() {
			public void execute() {
				Controlador.change2BookAdminstrator();
			}
		});
		menuBar.addItem(BackMenuItem);
		
		verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		PanelFondoGeneral.add(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		AzulPanel = new HorizontalPanel();
		AzulPanel.setStyleName("AzulTransparente");
		AzulPanel.setSpacing(5);
		AzulPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		AzulPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(AzulPanel);
		
		Image image = new Image("logoBNE.jpg");
		image.setAltText("BNElogo");
		AzulPanel.add(image);
		
		VerticalPanel BlancoPanel_1 = new VerticalPanel();
		BlancoPanel_1.setStyleName("BlancoTransparente");
		BlancoPanel_1.setSpacing(2);
		AzulPanel.add(BlancoPanel_1);
		
		Label lblNewLabel = new Label("Insert BNE book URI  (example : http://bdh-rd.bne.es/viewer.vm?id=0000093282 ) ");
		BlancoPanel_1.add(lblNewLabel);
		
		textBox = new TextBox();
		textBox.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				
					if (!textBox.getText().isEmpty())
					{
						bookReaderServiceHolder.getBNEBook(textBox.getText(), new AsyncCallback<BNEBookClient>() {
							
							@Override
							public void onSuccess(BNEBookClient result) {
								VisorSearcherGoogleBookPopupPanel VS = new VisorSearcherGoogleBookPopupPanel(result);
								 VS.center();
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Book not supported");
								
							}
						});
					}
					else
					{
						Window.alert(TEXT_CAN_NOT_BE_EMPTY);
					}
				}
				
			}
		});
		BlancoPanel_1.add(textBox);
		textBox.setWidth("688px");
	}
}
