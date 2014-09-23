package lector.client.reader;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ConstantsError;
import lector.client.controler.HelpMessage;
import lector.client.reader.hilocomentarios.ArbitroThreads;
import lector.client.reader.filter.FilterBasicPopUp;
import lector.client.reader.hilocomentarios.JeraquiaSimulada;
import lector.client.reader.hilocomentarios.ParesLlamada;
import lector.client.welcome.About;
import lector.share.model.AnnotationNotFoundException;
import lector.share.model.Language;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.BookClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.TextSelectorClient;
import lector.share.model.client.TypeClient;
import lector.share.model.client.UserClient;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

/***
 * Main entry point.
 * 
 * @author Cesar y Gayoso
 */
public class MainEntryPoint implements EntryPoint {

	//IDIOMA
	
	private static String Export="Export";
	
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	// Google Books Widgets
	private static int currentPageNumber = 0;
	private static BookClient book;

	// GUI
	private enum State {

		AllAnnotations, SelectedFree, SelectedBloked, NoAnnotations
	};

	
	
	private static Image originalBook = new Image("");
	private static Button pageBack = new Button("<");
	private static Button pageForward = new Button(">");
	private static MenuBar menuBar = new MenuBar(false);
	private static TextBox selectorPageBox = new TextBox();
	private static DecoratorPanel decoratorPanel = new DecoratorPanel();
	private static boolean isSelectionMode;
	private static boolean isShiftSelectionMode;
	private static boolean isShowDensity;
	private static ArrayList<SelectorPanel> popUpSelector;
	private static SelectorPanel popUpSelectoract;
	private static State state = State.NoAnnotations;
	private static MenuItem mntmShowAllComments;
	private static MenuItem mntmShowSelectedComments;
	private static MenuItem mntmBlockedComments;
	private static ScrollPanel ScrollAnnotationsPanel = new ScrollPanel();
	private static VerticalPanel verticalAnnotationsPanel = new VerticalPanel();
	private static TechnicalSpecs technicalSpecs;
	private MenuItem mntmRefresh;
	private MenuItem Annotacion;
	private static MenuItem mntmNoAnnotacion;
	private MenuItem AboutMenuButton;
	private static MenuItem Ficha;
	private final MenuItemSeparator separator = new MenuItemSeparator();
	private final MenuItemSeparator separator_1 = new MenuItemSeparator();
	private static MenuItem mntmFilter;
	private static FilterBasicPopUp TyposFilter;
	private final MenuItemSeparator separator_2 = new MenuItemSeparator();
	private static ArrayList<TypeClient> filtroTypes;
	private static ArrayList<UserClient> filtroUsers;
	private static ArrayList<String> filtroWords;
	private static ArrayList<Long> filtroAnotPar;
	private final MenuItemSeparator separator_3 = new MenuItemSeparator();
	private static Language ActualLang;
	private MenuItem mntmBrowser;
	private final MenuItemSeparator separator_4 = new MenuItemSeparator();
	private final MenuItemSeparator separator_5 = new MenuItemSeparator();
	private static MenuItem FilterInfo;
	private final HorizontalPanel horizontalPanel = new HorizontalPanel();
	private MenuItem DensidadAnot;
	private static SimplePanel Glue;
	private static ArrayList<SelectorPanel> SE;
	private static ArrayList<SelectorPanel> SER;
	private HorizontalPanel panelbase ;
	private VerticalPanel PanelBotoneseImagen;
	private HorizontalPanel PanelBaseCentrado;
	private MenuItem Exportacion;
	private SimplePanel GLueExportacion;
	private static PopUPEXportacion PEX;
	private static List<AnnotationClient> anotaciones_Actuales;
	private static final int DesviacionVentanaExportacion=34;
	private static final int DesviacionVentanaExportacionH=11;

	public MainEntryPoint() {
		
		isShiftSelectionMode=false;
		popUpSelector=new ArrayList<SelectorPanel>();
		
		pageForward.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterMoreLess");
				
			}
		});
	
	pageForward.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterMoreLessPush");
			}
		});
		

		pageForward.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterMoreLess");
		}
	});
		

		pageForward.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterMoreLessOver");
			
		}
	});

		pageForward.setStyleName("gwt-ButtonCenterMoreLess");
		
		
		pageBack.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterMoreLess");
				
			}
		});
	
pageBack.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterMoreLessPush");
			}
		});
		

		pageBack.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterMoreLess");
		}
	});
		

		pageBack.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterMoreLessOver");
			
		}
	});

		pageBack.setStyleName("gwt-ButtonCenterMoreLess");

		pageBack.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				hidePopUpSelector();
				hideDENSelector();
				if (currentPageNumber == book.getWebLinks().size() - 1) {
					if (!pageForward.isVisible()) {
						pageForward.setVisible(true);
					}
				}
				currentPageNumber--;
				if (currentPageNumber == 0) {
					pageBack.setVisible(false);
				}

				MainEntryPoint.refreshP();
				Refresh();
			}
		});
		pageForward.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				hidePopUpSelector();
				hideDENSelector();
				if (currentPageNumber == 0) {
					if (!pageBack.isVisible()) {
						pageBack.setVisible(true);
					}
				}
				currentPageNumber++;
				if (currentPageNumber == book.getWebLinks().size() - 1) {
					pageForward.setVisible(false);
				}
				MainEntryPoint.refreshP();
				Refresh();
			}
		});

		selectorPageBox.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent event) {

				hidePopUpSelector();
				hideDENSelector();
				TextBox aux = (TextBox) event.getSource();
				String ele = aux.getText();
				try {
					int pag = Integer.parseInt(ele);
					if ((pag >= 0) && (pag <= book.getWebLinks().size() - 1)) {
						currentPageNumber = pag;
						if (currentPageNumber == 0) {
							pageBack.setVisible(false);
						} else {
							pageBack.setVisible(true);
						}
						if (currentPageNumber == book.getWebLinks().size() - 1) {
							pageForward.setVisible(false);
						} else {
							pageForward.setVisible(true);
						}
						MainEntryPoint.refreshP();
						Refresh();
					} else {
						Window.alert(ActualLang.getE_Page_Dont_Exist());
					}
				} catch (Exception e) {
					Window.alert(ActualLang.getE_Not_a_number());
				}
			}
		});

		
		
		filtroTypes=new ArrayList<TypeClient>();
		filtroUsers=new ArrayList<UserClient>();
		filtroWords=new ArrayList<String>();
		filtroAnotPar=new ArrayList<Long>();
		originalBook=new Image();
		
		Window.addResizeHandler(new ResizeHandler() {
			
			public void onResize(ResizeEvent event) {
				
					if (SE!=null&&isShowDensity)
					{
						
						hideDENSelector();
						SER=new ArrayList<SelectorPanel>();
						for (SelectorPanel SP : SE) {
							TextSelectorClient TS=SP.getSelector(); 
							SelectorPanel SEE = new SelectorPanel(TS.getX().intValue(),
	                				 TS.getY().intValue(),
	                                 originalBook.getAbsoluteLeft(), originalBook.getAbsoluteTop(),
	                                 TS.getWidth().intValue(),
	                                 TS.getHeight().intValue());
	                         SEE.show();
	                         SER.add(SEE);
						}
						isShowDensity=true;
					}
					if (PEX!=null&&PEX.isShowing())
						{ 
						PEX.setPopupPosition(Window.getClientWidth()+Window.getScrollLeft()-PopUPEXportacion.getLongitud()-DesviacionVentanaExportacionH, DesviacionVentanaExportacion+Window.getScrollTop());
						PEX.show();
						}
					
				
				
			}
		});
		
		Window.addWindowScrollHandler(new ScrollHandler() {
			
			public void onWindowScroll(ScrollEvent event) {
				if (PEX!=null&&PEX.isShowing())
				{ 
				PEX.setPopupPosition(Window.getClientWidth()+Window.getScrollLeft()-PopUPEXportacion.getLongitud()-DesviacionVentanaExportacionH, DesviacionVentanaExportacion+Window.getScrollTop());
				PEX.show();
				}
			}
		});
		
	
		
	}

	public void onModuleLoad() {

		PEX=new PopUPEXportacion();
		// Paneles
		//final RootPanel RootAnnotation = RootPanel.get("Etiquetas");
		RootPanel RootMenu = RootPanel.get("Menu");
//		RootPanel RootTXOriginal = RootPanel.get("Original");
//		RootPanel RootTXOriginalB = RootPanel.get("OriginalB");
		RootPanel RootTXOriginal=  RootPanel.get();

		// Stilos
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("RootMaxMin");
		RootTXOriginal.setSize("100%", "100%");

		isSelectionMode = false;
		if (state == null)
			state = State.NoAnnotations; // ya esta inicializado, no es
		
		ActualLang=ActualState.getLanguage();
		menuBar.clearItems();
		menuBar.setAnimationEnabled(true);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");

		AboutMenuButton = new MenuItem(ActualLang.getNamePage(), false, new Command() {
			public void execute() {
				hidePopUpSelector();
				hideDENSelector();
				PopupPanel About = new About();
				About.showRelativeTo(AboutMenuButton);
			}
		});
		AboutMenuButton.setEnabled(true);	
		AboutMenuButton.setHTML(ActualLang.getNamePage());
		AboutMenuButton.setTitle(HelpMessage.ABOUTANNOTATIONHELP);
		
		menuBar.addItem(AboutMenuButton);
		
		menuBar.addSeparator(separator_3);

		Ficha = new MenuItem("New item", false, new Command() {
		
			public void execute() {
				hidePopUpSelector();
				hideDENSelector();
				PopUpFicha PF = new PopUpFicha(technicalSpecs);
				PF.showRelativeTo(Ficha);
			}
		});
		Ficha.setEnabled(false);
		Ficha.setHTML(ActualLang.getSpecifications());
		Ficha.setTitle(HelpMessage.SPECIFICATIONSHELP);
		menuBar.addItem(Ficha);
		MenuBar menuBar_1 = new MenuBar(true);
		menuBar_1.setAnimationEnabled(true);

		Annotacion = new MenuItem(ActualLang.getAnnotation(), false, menuBar_1);
		Annotacion.setTitle(HelpMessage.ANNOTATIONSMODEHELP);

		mntmNoAnnotacion = new MenuItem(ActualLang.getNo_Annotation(), false, new Command() {

			public void execute() {
				hidePopUpSelector();
				hideDENSelector();
				mntmNoAnnotacion.setEnabled(false);
				mntmShowSelectedComments.setEnabled(true);
				mntmShowAllComments.setEnabled(true);
				mntmBlockedComments.setEnabled(false);
				mntmBlockedComments.setHTML("<img src=\"NAvar.gif\">");
				mntmBlockedComments.setTitle(HelpMessage.NOANNOTATIONHELP);
				state = State.NoAnnotations;
				ScrollAnnotationsPanel.setVisible(false);
				Glue.setVisible(false);
				mntmFilter.setEnabled(false);
				mntmFilter.setVisible(false);
				separator_2.setVisible(false);
				mntmBrowser.setVisible(false);
				DensidadAnot.setEnabled(false);
				DensidadAnot.setVisible(false);
				GLueExportacion.setVisible(false);
				Exportacion.setVisible(false);
			}
		});
		menuBar_1.addItem(mntmNoAnnotacion);
		mntmNoAnnotacion.setEnabled(false);
		mntmNoAnnotacion.setTitle(HelpMessage.NOANNOTATIONHELP);

		mntmShowAllComments = new MenuItem(ActualLang.getAll_Annotation(), false,
				new Command() {

					public void execute() {
						hidePopUpSelector();
						hideDENSelector();
						mntmNoAnnotacion.setEnabled(true);
						mntmShowSelectedComments.setEnabled(true);
						mntmShowAllComments.setEnabled(false);
						mntmBlockedComments.setEnabled(false);
						mntmBlockedComments.setHTML("<img src=\"All.gif\">");
						mntmBlockedComments.setTitle(HelpMessage.ALLANNOTATIONHELP);
						state = State.AllAnnotations;
						ScrollAnnotationsPanel.setVisible(true);
						Glue.setVisible(true);
						mntmFilter.setEnabled(true);
						mntmFilter.setVisible(true);
						separator_2.setVisible(true);
						mntmBrowser.setVisible(true);
						DensidadAnot.setEnabled(true);
						DensidadAnot.setVisible(true);
						GLueExportacion.setVisible(true);
						Exportacion.setVisible(true);
						refreshP();
					}
				});
		menuBar_1.addItem(mntmShowAllComments);
		mntmShowAllComments.setEnabled(false);
		mntmShowAllComments.setTitle(HelpMessage.ALLANNOTATIONHELP);

		mntmShowSelectedComments = new MenuItem(ActualLang.getOnly_Selected(), false,
				new Command() {

					public void execute() {
						hidePopUpSelector();
						hideDENSelector();
						mntmNoAnnotacion.setEnabled(true);
						mntmShowSelectedComments.setEnabled(false);
						mntmShowAllComments.setEnabled(true);
						state = State.SelectedFree;
						mntmBlockedComments.setEnabled(false);
						mntmBlockedComments.setHTML("<img src=\"Free.gif\">");
						mntmBlockedComments.setTitle(HelpMessage.FREEANNOTATIONHELP);
						refreshP();
						ScrollAnnotationsPanel.setVisible(true);
						Glue.setVisible(true);
						verticalAnnotationsPanel.clear();
						mntmFilter.setEnabled(true);
						mntmFilter.setVisible(true);
						separator_2.setVisible(true);
						mntmBrowser.setVisible(true);
						DensidadAnot.setEnabled(true);
						DensidadAnot.setVisible(true);
						GLueExportacion.setVisible(true);
						Exportacion.setVisible(true);
					}
				});
		menuBar_1.addItem(mntmShowSelectedComments);
		mntmShowSelectedComments.setEnabled(false);
		mntmShowSelectedComments.setTitle(HelpMessage.SELECTEDANNOTATIONHELP);
		menuBar.addItem(Annotacion);
		
		DensidadAnot = new MenuItem(ActualLang.getShowDensity(), false, new Command() {
			

			public void execute() {
				hidePopUpSelector();
				hideDENSelector();
				SE=new ArrayList<SelectorPanel>();
				for (AnnotationClient A:anotaciones_Actuales)
				{

						
	                	 for (TextSelectorClient TS : A.getTextSelectors()) {
	                		 SelectorPanel SEE = new SelectorPanel(TS.getX().intValue(),
	                				 TS.getY().intValue(),
	                                 originalBook.getAbsoluteLeft(), originalBook.getAbsoluteTop(),
	                                 TS.getWidth().intValue(),
	                                 TS.getHeight().intValue());
	                		 SEE.setSelector(TS);
	                         SEE.show();
	                         SE.add(SEE);
	    				}
	            isShowDensity=true;
				}
			}
		});
		DensidadAnot.setEnabled(false);
		DensidadAnot.setVisible(false);
		DensidadAnot.setTitle(HelpMessage.DENSITYANNOTATIONHELP);
		menuBar.addItem(DensidadAnot);

		menuBar.addSeparator(separator_1);

		mntmBlockedComments = new MenuItem("Anotaciones Bloqueadas", false,
				new Command() {

					public void execute() {
						hidePopUpSelector();
						hideDENSelector();
						state = State.SelectedFree;
						mntmBlockedComments.setHTML("<img src=\"Free.gif\">");
						mntmBlockedComments.setTitle(HelpMessage.FREEANNOTATIONHELP);
						mntmBlockedComments.setEnabled(false);
					}
				});

		mntmBlockedComments.setEnabled(false);
		mntmBlockedComments.setHTML("<img src=\"NAvar.gif\">");
		mntmBlockedComments.setTitle(HelpMessage.NOANNOTATIONHELP);

		menuBar.addItem(mntmBlockedComments);

		mntmRefresh = new MenuItem("Refresh", false, new Command() {

			public void execute() {
				hidePopUpSelector();
				hideDENSelector();
				refreshP();
				Refresh();
			}
		});
		mntmRefresh.setHTML("<img src=\"RefreshIco.gif\">");
		mntmRefresh.setTitle(HelpMessage.REFRESHANNOTATIONHELP);
		menuBar.addItem(mntmRefresh);
		menuBar.addSeparator(separator);

		RefresCoDeTypesYTags();

		mntmFilter = new MenuItem("New item", false, new Command() {

			public void execute() {
				hidePopUpSelector();
				hideDENSelector();
				TyposFilter.showRelativeTo(mntmFilter);

			}
		});

		mntmFilter.setHTML(ActualLang.getFilterMainButton());
		mntmFilter.setVisible(false);
		mntmFilter.setTitle(HelpMessage.FILTERHELP);
		menuBar.addItem(mntmFilter);
		
		mntmBrowser = new MenuItem("New item", false, new Command() {
			public void execute() {
				hidePopUpSelector();
				hideDENSelector();
				ActualState.setReadingActivityBook(book);
				Controlador.change2Browser();
				if (PEX!=null) PEX.hide();
			}
		});
		mntmBrowser.setHTML(ActualLang.getBrowserMainButton());
		mntmBrowser.setVisible(false);
		mntmBrowser.setTitle(HelpMessage.BROWSERHELP);
		menuBar.addItem(mntmBrowser);

		menuBar.addSeparator(separator_2);

		MenuItem mntmManage = new MenuItem(ActualLang.getBackAdministrationButton(), false,
				new Command() {

					public void execute() {
						hidePopUpSelector();
						hideDENSelector();
						ActualState.setReadingActivityBook(book);
						Controlador.change2Administrator();
						if (PEX!=null)
							PEX.hide();
					}
				});
		mntmManage.setHTML(ActualLang.getBackAdministrationButton());
		menuBar.addItem(mntmManage);
		if (!(ActualState.getUser() instanceof ProfessorClient)) {
			mntmManage.setEnabled(false);
			mntmManage.setVisible(false);
		}
		mntmManage.setTitle(HelpMessage.ADMINISTRADORHELP);

		MenuItem mntmManage2 = new MenuItem(ActualLang.getBackUserButton(), false, new Command() {

			public void execute() {
				hidePopUpSelector();
				hideDENSelector();
				ActualState.setReadingActivityBook(book);
				Controlador.change2MyActivities();
				PEX.hide();
			}
		});
		mntmManage2.setHTML(ActualLang.getBackUserButton());
		mntmManage2.setTitle(HelpMessage.BACKUSERHELP);
		menuBar.addItem(mntmManage2);
		
		menuBar.addSeparator(separator_4);
		
		menuBar.addSeparator(separator_5);
		
		FilterInfo = new MenuItem(ActualLang.getAnnotationsFiltering(), false, new Command() {
			public void execute() {
				hidePopUpSelector();
				hideDENSelector();
				AceptWindow A=new AceptWindow();
				A.center();
			}
		});
		
		if (filtroTypes.isEmpty() && filtroUsers.isEmpty() && filtroAnotPar.isEmpty() && filtroWords.isEmpty())
			setfilterinfo(false);
		else setfilterinfo(true);
		
		Exportacion = new MenuItem("", false, new Command() {

			public void execute() {
				if (PEX==null)
					PEX=new PopUPEXportacion();
				else PEX.Refresh();
				PEX.setPopupPosition(Window.getClientWidth()+Window.getScrollLeft()-PopUPEXportacion.getLongitud()-DesviacionVentanaExportacionH, DesviacionVentanaExportacion+Window.getScrollTop());
				PEX.show();
			}
		});
		Exportacion.setHTML(MainEntryPoint.Export);
		Exportacion.setVisible(false);
		menuBar.addItem(Exportacion);
		FilterInfo.setStyleName("gwt-MenuItemFiltering");
		menuBar.addItem(FilterInfo);
		
		
		PanelBaseCentrado = new HorizontalPanel();
		PanelBaseCentrado.setStyleName("fondoLogo");
		PanelBaseCentrado.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		RootTXOriginal.add(PanelBaseCentrado,0,36);
		PanelBaseCentrado.setSize("100%", "100%");
		
		panelbase = new HorizontalPanel();
		PanelBaseCentrado.add(panelbase);
		panelbase.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		PanelBotoneseImagen = new VerticalPanel();
		
		panelbase.add(PanelBotoneseImagen);
		PanelBotoneseImagen.setSize("100%", "100%");
		// necesario

		// Botones Y Panel de isSelectionMode de Pagina

		selectorPageBox.setReadOnly(false);
		PanelBotoneseImagen.add(horizontalPanel);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.add(pageBack);
		pageBack.setVisible(false);
		horizontalPanel.add(selectorPageBox);
		horizontalPanel.add(pageForward);
		
		selectorPageBox.setSize("44px", "100%");
		PanelBotoneseImagen.add(decoratorPanel);
		
		decoratorPanel.setWidget(originalBook);
		
		
		VerticalPanel SP=new VerticalPanel();
		SP.setStyleName("BlancoTransparente");
		panelbase.add(SP);
		SP.setHeight("875px");
		//SP.setSize("1000px", "875px");
		Glue=new SimplePanel();
		Glue.setWidth("700px");
		SP.add(Glue);
		SP.add(ScrollAnnotationsPanel);
		
		ScrollAnnotationsPanel.setWidget(verticalAnnotationsPanel);
		ScrollAnnotationsPanel.setHeight("875px");
		ScrollAnnotationsPanel.setWidth("");
		
		GLueExportacion = new SimplePanel();
		panelbase.add(GLueExportacion);
		GLueExportacion.setWidth("473px");
		GLueExportacion.setVisible(false);
		//verticalAnnotationsPanel.setSize("100%", "100%");
		//ScrollAnnotationsPanel.setSize("340px", "875px");
		ScrollAnnotationsPanel.setVisible(false);
		
				// Propiedades de los Elementos
		
				originalBook.setVisible(false);
				decoratorPanel.setVisible(false);
				//ScrollAnnotationsPanel.setAlwaysShowScrollBars(true);
				selectorPageBox.setVisible(false);
		if (!(ActualState.getUser() instanceof StudentClient)) {
			mntmManage2.setEnabled(false);
			mntmManage2.setVisible(false);
		}
//		pageForward.setVisible(false);

		if (filtroTypes == null ) {
			filtroTypes = new ArrayList<TypeClient>();
		}
		
		if (filtroUsers == null ) {
			filtroUsers = new ArrayList<UserClient>();
		}
		
		setCurrentPageNumber(currentPageNumber);
		
		
		switch (state) {
		case AllAnnotations:
			mntmNoAnnotacion.setEnabled(true);
			mntmShowSelectedComments.setEnabled(true);
			mntmShowAllComments.setEnabled(false);
			mntmBlockedComments.setEnabled(false);
			mntmBlockedComments.setHTML("<img src=\"All.gif\">");
			mntmBlockedComments.setTitle(HelpMessage.ALLANNOTATIONHELP);
			ScrollAnnotationsPanel.setVisible(true);
			Glue.setVisible(true);
			mntmFilter.setVisible(true);
			separator_2.setVisible(true);
			mntmBrowser.setVisible(true);
			DensidadAnot.setEnabled(true);
			DensidadAnot.setVisible(true);
			GLueExportacion.setVisible(false);
			Exportacion.setVisible(true);
			break;

		case NoAnnotations:
			mntmNoAnnotacion.setEnabled(false);
			mntmShowSelectedComments.setEnabled(true);
			mntmShowAllComments.setEnabled(true);
			mntmBlockedComments.setEnabled(false);
			mntmBlockedComments.setHTML("<img src=\"NAvar.gif\">");
			mntmBlockedComments.setTitle(HelpMessage.NOANNOTATIONHELP);
			ScrollAnnotationsPanel.setVisible(false);
			Glue.setVisible(false);
			mntmFilter.setVisible(false);
			separator_2.setVisible(false);
			mntmBrowser.setVisible(false);
			DensidadAnot.setEnabled(false);
			DensidadAnot.setVisible(false);
			GLueExportacion.setVisible(true);
			Exportacion.setVisible(false);
			break;

		case SelectedBloked:
			mntmNoAnnotacion.setEnabled(true);
			mntmShowSelectedComments.setEnabled(false);
			mntmShowAllComments.setEnabled(true);
			mntmBlockedComments.setEnabled(true);
			mntmBlockedComments.setHTML("<img src=\"Bloked.gif\">");
			mntmBlockedComments.setTitle(HelpMessage.BLOKEDANNOTATIONHELP);
			ScrollAnnotationsPanel.setVisible(true);
			Glue.setVisible(true);
			verticalAnnotationsPanel.clear();
			mntmFilter.setVisible(true);
			separator_2.setVisible(true);
			mntmBrowser.setVisible(true);
			DensidadAnot.setEnabled(true);
			DensidadAnot.setVisible(true);
			GLueExportacion.setVisible(true);
			Exportacion.setVisible(true);
			break;
		case SelectedFree:
			mntmNoAnnotacion.setEnabled(true);
			mntmShowSelectedComments.setEnabled(false);
			mntmShowAllComments.setEnabled(true);
			mntmBlockedComments.setEnabled(false);
			mntmBlockedComments.setHTML("<img src=\"Free.gif\">");
			mntmBlockedComments.setTitle(HelpMessage.FREEANNOTATIONHELP);
			ScrollAnnotationsPanel.setVisible(true);
			Glue.setVisible(true);
			verticalAnnotationsPanel.clear();
			mntmFilter.setVisible(true);
			separator_2.setVisible(true);
			mntmBrowser.setVisible(true);
			DensidadAnot.setEnabled(true);
			DensidadAnot.setVisible(true);
			GLueExportacion.setVisible(true);
			Exportacion.setVisible(true);
			break;
		default:
			break;
		}
		
		
	}

	protected static void Refresh() {
		if (book.getWebLinks() != null) {
			imageOnLoad(book.getWebLinks().get(currentPageNumber));
			//originalBook.setUrl(book.getImagesPath());
			selectorPageBox.setText(Integer.toString(currentPageNumber));
		}
	}

	protected static ArrayList<AnnotationClient> refreshSelected(int x, int y) {
		ArrayList<AnnotationClient> toverticalAnnotationsPanel = new ArrayList<AnnotationClient>();
		if (anotaciones_Actuales != null) {
			for (int i = 0; i < anotaciones_Actuales.size(); i++) {
				if (isIn(anotaciones_Actuales.get(i), x, y)) {
					toverticalAnnotationsPanel.add(anotaciones_Actuales.get(i));
				}
			}
		}
		return toverticalAnnotationsPanel;
	}

	protected static void insertrefreshedAnot(List<AnnotationClient> List) {
		if (List != null) {
			ArbitroThreads AT=new ArbitroThreads();
			for (int i = 0; i < List.size(); i++) {
				CommentPanel commentPanel = new CommentPanel(List.get(i),
						originalBook);
				JeraquiaSimulada JS=new JeraquiaSimulada();
					AT.addLlamada(new ParesLlamada(JS.getVerticalPanel(), List.get(i)));
				verticalAnnotationsPanel.add(commentPanel);
				verticalAnnotationsPanel.add(JS);
			}
			AT.Start();
		
		}
	}

	private static boolean isIn(AnnotationClient annotationTest, int horizontal,
			int vertical) {
		boolean resultado=false;
		int i=0;
		while(!resultado&&i<annotationTest.getTextSelectors().size())
		{
		TextSelectorClient TS = annotationTest.getTextSelectors().get(i);
		resultado = (horizontal >= TS.getX())
				&& (horizontal <= TS.getX() + TS.getWidth());
		resultado = resultado && (vertical >= TS.getY())
				&& (vertical <= TS.getY() + TS.getHeight());
		i++;
		}
		return resultado;
	
		}

	protected static void refreshC(int X, int Y) {
		switch (state) {
		case NoAnnotations:
			verticalAnnotationsPanel.clear();
			break;
		case SelectedFree:
			if (verticalAnnotationsPanel.getWidgetCount() != 0) {
				verticalAnnotationsPanel.clear();
			}
			List<AnnotationClient> Result = refreshSelected(X, Y);			
			FiltraAnotaciones(Result);
			insertrefreshedAnot(Result);
			break;
		default:
			break;
		}

	}

	private static ArrayList<AnnotationClient> OrdenaResultado(
			ArrayList<AnnotationRanked> resultRanked) {
		
		ArrayList<AnnotationClient> Resulout =new ArrayList<AnnotationClient>();
		quickSort(resultRanked,0,resultRanked.size()-1);
		for (AnnotationRanked annotation : resultRanked) {
			Resulout.add(annotation.getAnotacionRankeada());
		}
		return Resulout;
	}

	
	private static int partition(ArrayList<AnnotationRanked> resultRanked, int left, int right)
	{
	      int i = left, j = right;
	      AnnotationRanked tmp;
	      float pivot = resultRanked.get((left + right) / 2).getRanking();
	     
	      while (i <= j) {
	            while (resultRanked.get(i).getRanking() > pivot)
	                  i++;
	            while (resultRanked.get(j).getRanking()  < pivot)
	                  j--;
	            if (i <= j) {
	                  tmp = resultRanked.get(i);
	                  resultRanked.set(i, resultRanked.get(j));
	                  resultRanked.set(j, tmp);
	                  i++;
	                  j--;
	            }
	      };
	     
	      return i;
	}
	 
	private static void quickSort(ArrayList<AnnotationRanked> resultRanked, int left, int right) {
		if (!resultRanked.isEmpty()){
	      int index = partition(resultRanked, left, right);
	      if (left < index - 1)
	            quickSort(resultRanked, left, index - 1);
	      if (index < right)
	            quickSort(resultRanked, index, right);
		}
	}
	
	public static void refreshP() {
		if (popUpSelector != null) {
			for (SelectorPanel SP : popUpSelector) {
				SP.hide();
			}
		}
		RefresCoDeTypesYTags();
		verticalAnnotationsPanel.clear();
		switch (state) {
		case AllAnnotations:
			refreshAllComments();
			break;
		case SelectedFree:
			refreshLoadComments();
			break;
		default:
			break;
		}

	}

	private static ArrayList<AnnotationRanked> setFilerByType(
			List<AnnotationClient> result) {
		
		ArrayList<AnnotationRanked> resultout = new ArrayList<AnnotationRanked>();
		if (filtroTypes.isEmpty())
			{
//			for (AnnotationClient annotationRanked : result) {
//				resultout.add(new AnnotationRanked(annotationRanked));
//			}
			return resultout;
			}
		
		
//		resultout.addAll(result);
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				List<TypeClient> A = result.get(i).getTags();
				int resulInt=isInType(A);
				if (resulInt!=0) {
					AnnotationRanked act=new AnnotationRanked(result.get(i));
					act.setRanking(resulInt);
					resultout.add(act);
				}
			}
		}

		
		return resultout;
	}



	private static int isInType(List<TypeClient> a) {
		if (a == null) {
			return 1;
		}
		int points=0;
		for (int pos = 0; pos < filtroTypes.size(); pos++){ 
			
			for (int pos2 = 0; pos2 < a.size();pos2++){
				if (a.get(pos2).getId().equals(filtroTypes.get(pos).getId()))
				points++;
			}
		}
		return points;
	}

	
	private static void RefresCoDeTypesYTags() {
		TyposFilter = new FilterBasicPopUp();	
	}

	protected static void refreshLoadComments() {
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ActualLang.getLoading());
		AsyncCallback<List<AnnotationClient>> callback = new AsyncCallback<List<AnnotationClient>>() {

			public void onFailure(Throwable caught) {
				if (!(caught instanceof AnnotationNotFoundException)) {
					Window.alert(ActualLang.getE_Coments_dont_be_refresh());
				}
				anotaciones_Actuales=new ArrayList<AnnotationClient>();
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(List<AnnotationClient> result) {

				anotaciones_Actuales=result;
				LoadingPanel.getInstance().hide();
			}
		};
		if (ActualState.getUser() instanceof ProfessorClient) {
			bookReaderServiceHolder.getAnnotationsByPageNumber(
					currentPageNumber, book.getISBN(),ActualState.getReadingactivity().getId(), callback);
		} else {
			bookReaderServiceHolder.getAnnotationsByPageNumbertAndUserId(
					currentPageNumber, book.getISBN(), ActualState.getUser()
							.getId(), ActualState.getReadingactivity().getId(), callback);
		}

	}

	protected static void refreshAllComments() {
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ActualLang.getLoading());
		AsyncCallback<List<AnnotationClient>> callback = new AsyncCallback<List<AnnotationClient>>() {

			public void onFailure(Throwable caught) {
				if (!(caught instanceof AnnotationNotFoundException)) {
					Window.alert(ActualLang.getE_Coments_dont_be_refresh());
				}
				anotaciones_Actuales=new ArrayList<AnnotationClient>();
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(List<AnnotationClient> result) {

				anotaciones_Actuales=result;
				ArrayList<AnnotationClient> Salida=FiltraAnotaciones(result);
				insertrefreshedAnot(Salida);
				LoadingPanel.getInstance().hide();
			}
		};
		if (ActualState.getUser() instanceof ProfessorClient) {
			bookReaderServiceHolder.getAnnotationsByPageNumber(
					currentPageNumber, book.getISBN(),ActualState.getReadingactivity().getId(), callback);
		} else {
			bookReaderServiceHolder.getAnnotationsByPageNumbertAndUserId(
					currentPageNumber, book.getISBN(), ActualState.getUser()
							.getId(), ActualState.getReadingactivity().getId(), callback);
		}

	}


	protected static ArrayList<AnnotationClient> FiltraAnotaciones(List<AnnotationClient> result) {
		
		if (!filtroAnotPar.isEmpty()){
			setfilterinfo(true);
			return (ArrayList<AnnotationClient>) applyConcreteFilter(result);
		}
		else{
			if (!filtroTypes.isEmpty()||!filtroUsers.isEmpty()||!filtroWords.isEmpty())
			{
				ArrayList<AnnotationRanked> ResultRankedTipe = setFilerByType(result);
				ArrayList<AnnotationRanked> ResultRankedUser = setfilterByUser(result);
				ArrayList<AnnotationRanked> ResultRankedWords = setfilterByContain(result);
				setfilterinfo(true);
				ArrayList<AnnotationRanked> SalidaRank1=combine(ResultRankedTipe,ResultRankedUser);
				ArrayList<AnnotationRanked> SalidaRank2=combine(SalidaRank1,ResultRankedWords);
				return OrdenaResultado(SalidaRank2);
			}
			else
				return (ArrayList<AnnotationClient>) result;
		}
			
	}

	private static ArrayList<AnnotationRanked> setfilterByContain(
			List<AnnotationClient> result) {
		
		ArrayList<AnnotationRanked> resultout = new ArrayList<AnnotationRanked>();
		if (filtroWords.isEmpty())
			{
			return resultout;
			}

		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				String A = result.get(i).getComment();
				int resulInt=isInWords(A);
				if (resulInt>0) {
					AnnotationRanked resultRanked = new AnnotationRanked(result.get(i));
					resultRanked.setRanking(resulInt);
					resultout.add(resultRanked);
				}
			}
			
		}

		
		return resultout;
	}

	private static int isInWords(String a) {
		int Salida=0;
		for (String word : filtroWords) {
			if (a.toUpperCase().contains(word.toUpperCase()))
				Salida++;
		}
		return Salida;
	}

	private static ArrayList<AnnotationRanked> combine(ArrayList<AnnotationRanked> resultRankedTipe,
			ArrayList<AnnotationRanked> resultRankedUser) {
		for (AnnotationRanked annotationRanked : resultRankedUser) {
			boolean found = false;
			for (AnnotationRanked annotationRanked1 : resultRankedTipe) {
				if (annotationRanked.getAnotacionRankeada().getId().equals(annotationRanked1.getAnotacionRankeada().getId()))
					{
					found=true;
					annotationRanked.setRanking(annotationRanked1.getRanking()+annotationRanked.getRanking());
					break;
					}
			}
			if (!found) resultRankedTipe.add(annotationRanked);
		}
		return  resultRankedTipe;
	}


	public static int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public static void setCurrentPageNumber(int currentPageNumber) {
		MainEntryPoint.currentPageNumber = currentPageNumber;
		if (book!=null){
			if (currentPageNumber < book.getWebLinks().size() - 1) {
				pageForward.setVisible(true);
			}
			else pageForward.setVisible(false);
			if (currentPageNumber > 0) {
			pageBack.setVisible(true);
			}
			else pageBack.setVisible(false);
		}
		
	}

	public static void SetBook(BookClient result) {
		originalBook.setVisible(true);
		decoratorPanel.setVisible(true);
		selectorPageBox.setVisible(true);
		Ficha.setEnabled(true);
		book = result;
		verticalAnnotationsPanel.clear();
		setCurrentPageNumber(currentPageNumber);
		MainEntryPoint.refreshP();
		Refresh();
	}

	public static TechnicalSpecs getTechnicalSpecs() {
		if (technicalSpecs==null) technicalSpecs=new TechnicalSpecs(book);
		return technicalSpecs;
	}

	public static ArrayList<TypeClient> getFiltroTypes() {
		return filtroTypes;
	}

//	public static void setFiltroTypes(ArrayList<TypeClient> filtroTypes) {
//		if (filtroTypes.size()==0)
//			setfilterinfo(false);
//		else setfilterinfo(true);
//		MainEntryPoint.filtroTypes = filtroTypes;
//		filtroAnotPar=new ArrayList<Long>();
//		filtroUsers=new ArrayList<UserClient>();
//		filtroWords=new ArrayList<String>();
//		if (state == State.AllAnnotations) {
//			verticalAnnotationsPanel.clear();
//			List<AnnotationClient> Result = anotaciones_Actuales;
//			FiltraAnotaciones(Result);
//			insertrefreshedAnot(Result);
//		}
//	}

	public static void hidePopUpSelector() {
		if (popUpSelector!=null)
			for (SelectorPanel SP : popUpSelector) {
			SP.hide();
		}
		popUpSelector=new ArrayList<SelectorPanel>();
		if (popUpSelectoract!=null) popUpSelectoract.hide();
	}
	
	public static void hideDENSelector() {
		if (SE!=null) 
			for (SelectorPanel SP : SE) {
			SP.hide();
		}
		if (SER!=null) 
			for (SelectorPanel SP : SER) {
			SP.hide();
		}
		
		isShowDensity=false;
		
	}
	
	public static void setFiltro(ArrayList<TypeClient> filtroTypes, ArrayList<UserClient> User, ArrayList<String> Words,ArrayList<Long> Concretas) {
		MainEntryPoint.filtroTypes = filtroTypes;
		MainEntryPoint.filtroUsers=User;
		MainEntryPoint.filtroWords=Words;
		MainEntryPoint.filtroAnotPar=Concretas;
		filtroAnotPar=new ArrayList<Long>();
		if (state == State.AllAnnotations) {
			verticalAnnotationsPanel.clear();
			List<AnnotationClient> Result = anotaciones_Actuales;
			Result=FiltraAnotaciones(Result);
			insertrefreshedAnot(Result);
		}
	}

	private static List<AnnotationClient> applyConcreteFilter(
			List<AnnotationClient> result) {
		if (filtroAnotPar.isEmpty()) 
			return result;
		ArrayList<AnnotationClient> Salida=new ArrayList<AnnotationClient>();
		for (AnnotationClient annotation : result) {
			if (isInList(annotation)) 
				Salida.add(annotation);
		}
		return Salida;
	}

	private static boolean isInList(AnnotationClient annotation) {
		for (Long long1 : filtroAnotPar) {
			if (long1.equals(annotation.getId())) 
				return true;
		}
		return false;
	}

	private static ArrayList<AnnotationRanked> setfilterByUser(
			List<AnnotationClient> result) {
		
		
		ArrayList<AnnotationRanked> resultout = new ArrayList<AnnotationRanked>();
		if (filtroUsers.isEmpty())
			{
			return resultout;
			}

		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Long A = result.get(i).getCreator().getId();
				if (isInUser(A)) {
					AnnotationRanked resultRanked = new AnnotationRanked(result.get(i));
					resultRanked.setRanking(1);
					resultout.add(resultRanked);
				}
			}
			
		}

		
		return resultout;
	}

	private static boolean isInUser(Long a) {
		if (a == null) {
			return true;
		}
		for (int pos = 0; pos < filtroUsers.size(); pos++){ 
				if (a.equals(filtroUsers.get(pos).getId()))
					{
					return true;
					}
		}
		return false;
	}
	
//	public static void setFiltroAnotPar(ArrayList<Long> filtroAnotPar) {
//		MainEntryPoint.filtroAnotPar = filtroAnotPar;
//		filtroTypes=new ArrayList<TypeClient>();
//		filtroUsers=new ArrayList<UserClient>();
//		filtroWords=new ArrayList<String>();
//		
//	}
	
	public static void imageOnLoad(String URL)
	{
		
		decoratorPanel.remove(originalBook);
		
		originalBook=new Image();
		
		decoratorPanel.add(originalBook);
		
		originalBook.addMouseMoveHandler(new MouseMoveHandler() {

			public void onMouseMove(MouseMoveEvent event) {
				if (isShowDensity)
				{
					isShowDensity=false;
					if (SE!=null) 
						for (SelectorPanel SP : SE) {
						SP.hide();
					}
					if (SER!=null) 
						for (SelectorPanel SP : SER) {
						SP.hide();
					}
				}
				if (state != State.NoAnnotations) {
					if (isSelectionMode == true) {

							popUpSelectoract.setTamagno(event.getX(), event.getY());
						
					} else {
						
						if (isShiftSelectionMode && !event.isShiftKeyDown())
							{
							
							ArrayList<TextSelectorClient> ARRAT=new ArrayList<TextSelectorClient>();
								{
								for (SelectorPanel PPSelect : popUpSelector) {
								
								ARRAT.add(PPSelect.getSelector());
								}
							TextComment TC = new TextComment(ARRAT, book);
							TC.center();
							
							isShiftSelectionMode=false;
							
								}
							
							
							}
						
						if (state != State.AllAnnotations) refreshC(event.getX(), event.getY());
						
						
						if (event.isControlKeyDown()
								&& (state == State.SelectedFree)) {
							state = State.SelectedBloked;
							mntmBlockedComments.setHTML("<img src=\"Bloked.gif\">");
							mntmBlockedComments.setEnabled(true);
							mntmBlockedComments.setTitle(HelpMessage.BLOKEDANNOTATIONHELP);
						}

						if (event.isAltKeyDown() && (state == State.SelectedBloked)) {
							state = State.SelectedFree;
							mntmBlockedComments.setHTML("<img src=\"Free.gif\">");
							mntmBlockedComments.setEnabled(false);
							mntmBlockedComments.setTitle(HelpMessage.FREEANNOTATIONHELP);
						}
					}

					

				}

			}
		});

		originalBook.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				event.preventDefault();
				event.stopPropagation();

			}
		});

		originalBook.addMouseDownHandler(new MouseDownHandler() {

			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
				event.stopPropagation();
				if (state != State.NoAnnotations) {
					if (!isSelectionMode
							&& (event.getNativeButton() == NativeEvent.BUTTON_LEFT)) {
						if (!event.isShiftKeyDown()) 
							{
							for (SelectorPanel SP : popUpSelector) {
								SP.hide();
							}
							popUpSelector=new ArrayList<SelectorPanel>();
							}
						popUpSelectoract = new SelectorPanel(event.getX(), event
								.getY(), originalBook.getAbsoluteLeft(),
								originalBook.getAbsoluteTop(), 0, 0);
						popUpSelectoract.show();
						isSelectionMode = true;
							
					}

				}

			}
		});

		originalBook.addMouseUpHandler(new MouseUpHandler() {

			public void onMouseUp(MouseUpEvent event) {
				event.preventDefault();
				event.stopPropagation();
				if (state != State.NoAnnotations) {
					if (isSelectionMode){
						if (!selectorvacio(popUpSelectoract))
							popUpSelector.add(popUpSelectoract);
						else Window.alert(ConstantsError.ERROR_SELECTION_TOO_SMALL);
						
						if (!event.isShiftKeyDown())
						{
						
						ArrayList<TextSelectorClient> ARRAT=new ArrayList<TextSelectorClient>();
		
							for (SelectorPanel PPSelect : popUpSelector) {
							
							ARRAT.add(PPSelect.getSelector());
							}
						
						if (!ARRAT.isEmpty())
						{	
						TextComment TC = new TextComment(ARRAT, book);
						TC.center();
						}
						
						isShiftSelectionMode=false;
						
						}else 
						isShiftSelectionMode=true;

						isSelectionMode = false;
					}
				

				}

			}

			private boolean selectorvacio(SelectorPanel popUpSelectoract) {
				
				return popUpSelectoract.vacio_();
			}
		});
		
		originalBook.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				Image I = (Image) event.getSource();
				float He = I.getHeight();
				float Wi = I.getWidth();
				float prop = He / 830;
				float Winew = (Wi / prop);
				originalBook.setSize(Winew + "px", "830px");
				// Window.alert("Altura: " + He + "Ancho: " + Wi );
			}
		});
		
		originalBook.setUrl(URL.replace("\\", "/"));
	}
	
//	public static void setPorcentScrollAnnotationsPanel() {
//		ScrollAnnotationsPanel.setWidth("120%");
//	}
	
	
	public static void setfilterinfo(boolean estado)
	{
		if (FilterInfo!=null){
			FilterInfo.setEnabled(estado);
			FilterInfo.setVisible(estado);
		}
		
	}
	
	public static Image getOriginalBook() {
		return originalBook;
	}
	
	public static PopUPEXportacion getPEX() {
		return PEX;
	}

	public static void CleanFilter() {
		filtroAnotPar= new ArrayList<Long>();
		filtroTypes= new ArrayList<TypeClient>();
		filtroUsers = new ArrayList<UserClient>();
		filtroWords = new ArrayList<String>();
		setfilterinfo(false);
		if (state == State.AllAnnotations) {
			verticalAnnotationsPanel.clear();
			List<AnnotationClient> Result = anotaciones_Actuales;
			insertrefreshedAnot(Result);
		}
		
	}
	
	
	
}
