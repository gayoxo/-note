/**
 * 
 */
package lector.client.admin.book.BNEAPI;

import lector.client.admin.book.googleAPI.VisorSearcherGoogleBookPopupPanel;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;
import lector.share.RuntimeAtNoteException;
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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public class SearcherBNEEntryPoint implements EntryPoint {

	private static final String TEXT_CAN_NOT_BE_EMPTY = "URI Can not be empty";
	private static final String TITLE_CAN_NOT_BE_EMPTY = "Title Can not be empty";
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
	private VerticalPanel verticalPanel_1;
	private Label OptionalLabel;
	private Grid grid;
	private Label TittleLAbel;
	private TextBox TitleTextbox;
	private TextBox AuthorText;
	private TextBox YearTextb;
	private TextBox ISBNText;
	private HorizontalPanel horizontalPanel;
	private Button SearchButton;
	
	
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
		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(3);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		BlancoPanel_1.add(horizontalPanel);
		
		textBox = new TextBox();
		horizontalPanel.add(textBox);
		textBox.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				
					llamada();
					
				}
				
			}
		});
		textBox.setWidth("688px");
		
		SearchButton = new Button("Load Book");
		SearchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				llamada();
			}
		});
		horizontalPanel.add(SearchButton);
		
		verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setSpacing(5);
		BlancoPanel_1.add(verticalPanel_1);
		
		OptionalLabel = new Label("Aditional information (* = optional)");
		verticalPanel_1.add(OptionalLabel);
		
		grid = new Grid(4, 2);
		verticalPanel_1.add(grid);
		
		TittleLAbel = new Label("Title");
		grid.setWidget(0, 0, TittleLAbel);
		
		TitleTextbox = new TextBox();
		grid.setWidget(0, 1, TitleTextbox);
		
		Label Authorlabel = new Label("Author *");
		grid.setWidget(1, 0, Authorlabel);
		
		AuthorText = new TextBox();
		grid.setWidget(1, 1, AuthorText);
		
		Label YearLabel = new Label("Year *");
		grid.setWidget(2, 0, YearLabel);
		
		YearTextb = new TextBox();
		grid.setWidget(2, 1, YearTextb);
		
		Label ISBNLabel = new Label("ISBN *");
		grid.setWidget(3, 0, ISBNLabel);
		
		ISBNText = new TextBox();
		grid.setWidget(3, 1, ISBNText);
	}


	protected void llamada() {
		boolean Error=false;
		if (textBox.getText().isEmpty())
		{
			Window.alert(TEXT_CAN_NOT_BE_EMPTY);
			Error=true;
		}
		
		if (TitleTextbox.getText().isEmpty())
		{
			Window.alert(TITLE_CAN_NOT_BE_EMPTY);
			Error=true;
		}
		
		
		
		if (!Error)
			bookReaderServiceHolder.getBNEBook(textBox.getText(),AuthorText.getText(),ISBNText.getText(),YearTextb.getText(),TitleTextbox.getText(), new AsyncCallback<BNEBookClient>() {
				
				@Override
				public void onSuccess(BNEBookClient result) {
					VisorSearcherGoogleBookPopupPanel VS = new VisorSearcherGoogleBookPopupPanel(result);
					 VS.center();
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					if (caught instanceof RuntimeAtNoteException)
						Window.alert(((RuntimeAtNoteException)caught).getMessage());	
					else 
						Window.alert("Error in load, please try again");
				}
			});
		
	}
}
