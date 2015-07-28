package lector.client.reader;

import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.EntitdadObject;
import lector.client.controler.catalogo.Finder;
import lector.client.controler.catalogo.FinderKeys;
import lector.client.controler.catalogo.FinderOwnGrafo;
import lector.client.controler.catalogo.client.EntityCatalogElements;
import lector.client.controler.catalogo.client.File;
import lector.client.controler.catalogo.client.Folder;
import lector.client.controler.catalogo.graph.BotonGrafo;
import lector.client.reader.PanelTextComent.CatalogTipo;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.ProfessorClient;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;

public class SelectorTypePopUpAnnotacion extends PopupPanel {
	private static Finder finder;
	protected MenuItem mntmNewItem;
	protected MenuBar menuBar;
	private static CatalogTipo CT;
	private HorizontalPanel panelBotonesTipo;
	private CatalogoClient Cata;
	private static HorizontalPanel HP;

	public SelectorTypePopUpAnnotacion(HorizontalPanel penelBotonesTipo,CatalogoClient Catain, CatalogTipo catalog2) {
		super(false);
		CT=catalog2;
		setModal(true);
		panelBotonesTipo=penelBotonesTipo;
		Cata=Catain;
//		SimplePanel verticalPanel = new SimplePanel();
//		
//		
//		verticalPanel.setSize("100%", "100%");
		DockPanel dockPanel = new DockPanel();
//        verticalPanel.add(dockPanel);
        dockPanel.setSize(Window.getClientWidth()-25+"px", Window.getClientHeight()-25+"px");
        setWidget(dockPanel);
        
        menuBar = new MenuBar(false);
        dockPanel.add(menuBar,DockPanel.NORTH);
        dockPanel.setCellHeight(menuBar, "20px");
        menuBar.setSize("100%", "20px");
        
       
        mntmNewItem = new MenuItem(ActualState.getLanguage().getNewAdmin(), false, new Command() {
        	public void execute() {
        		SelectorTypePopUpAnnotacionAdministracion SBTF=new SelectorTypePopUpAnnotacionAdministracion(panelBotonesTipo,Cata,finder);
        		SBTF.center();
        	}
        });
        if (ActualState.getUser() instanceof ProfessorClient)
        	menuBar.addItem(mntmNewItem);
        
        mntmNewItem.setHeight("20px");
       // mntmNewItem.setSize("100%", "100%");
        
        MenuItem GlueMnI = new MenuItem("", false, (Command) null);
        GlueMnI.setEnabled(false);
        GlueMnI.setHTML("");
        menuBar.addItem(GlueMnI);
        GlueMnI.setWidth("100%");
        GlueMnI.setHeight("20px");
        
        MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {
        	public void execute() {
        		while(HP.getWidgetCount()!=0)
        		{
        			ButtonTipo Yo=(ButtonTipo)HP.getWidget(0);
					Yo.setPertenezco(panelBotonesTipo);
					if (!exist(Yo))
						panelBotonesTipo.add(Yo);
					else HP.remove(Yo);
       		}
//        			for (Widget widget : HP) {
//					panelBotonesTipo.add(widget);				
//        		}
        		hide();
        	}

			private boolean exist(ButtonTipo yo) {
				for (Widget BB:panelBotonesTipo)
					{
					ButtonTipo Yo=(ButtonTipo)BB;
					if (yo.getEntidad().getEntry().getId().equals(Yo.getEntidad().getEntry().getId())) 
						return true;
					}
				return false;
			}
        });
        mntmNewItem_1.setHTML(ActualState.getLanguage().getClose());
        mntmNewItem_1.setHeight("20px");
        menuBar.addItem(mntmNewItem_1);
        
        ScrollPanel SP=new ScrollPanel();
        dockPanel.add(SP,DockPanel.SOUTH);
        dockPanel.setCellHeight(SP, "38px");
        SP.setSize("100%", "100%");
        HP=new HorizontalPanel();
        SP.add(HP);
        
        HP.setHeight("100%");
        
        SimplePanel scrollPanel = new SimplePanel();
        scrollPanel.setSize("100%", "100%");
        dockPanel.add(scrollPanel, DockPanel.CENTER);
        dockPanel.setSize(Window.getClientWidth()-25+"px", Window.getClientHeight()-25+"px");
        

        
        RestoreFinderButtonActio();
        
        if (ActualState.getReadingactivity().getVisualization()==null||ActualState.getReadingactivity().getVisualization().equals(Constants.VISUAL_ARBOL))
        {
        finder= new FinderOwnGrafo(Cata);
        scrollPanel.setWidget(finder);
        finder.setSize("100%", "100%");
        }
        else 
        {
        	 finder= new FinderKeys();
        	 finder.setCatalogo(Cata);
             scrollPanel.setWidget(finder);
             finder.setSize("100%", "100%");
        }
	}

	protected void setAllowCreate(boolean state)
	{
		if (state) menuBar.addItem(mntmNewItem);
	}
	
	public static void RestoreFinderButtonActio(){
		FinderOwnGrafo.setButtonTipoGrafo(new BotonGrafo("prototipo", new VerticalPanel(),HP,finder));
		 FinderOwnGrafo.setBotonClickGrafo(new ClickHandler() {

		        public void onClick(ClickEvent event) {
		        	BotonGrafo BS=((BotonGrafo) event.getSource());
		        EntityCatalogElements Act=(EntityCatalogElements) BS.getEntidad();
		        
		        if (Act instanceof File)
		        {
		        	ButtonTipo nuevo=new ButtonTipo(((File) Act),CT.getTexto(),(HorizontalPanel) BS.getSelectionPanel());
		        	nuevo.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
							
						}
					});
				
		        	nuevo.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
						}
					});
					

		        	nuevo.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
					}
				});
					

		        	nuevo.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							
							((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
						
					}
				});

		        	nuevo.setStyleName("gwt-ButtonCenter");
		        	nuevo.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							ButtonTipo Yo=(ButtonTipo)event.getSource();
							Yo.getPertenezco().remove(Yo);
							
							
						}
					});
		        	if (!ExistPreview((HorizontalPanel) BS.getSelectionPanel(),Act))
		        			BS.getSelectionPanel().add(nuevo);
		        	else Window.alert(ActualState.getLanguage().getE_ExistBefore());
		        }
		        }

				private boolean ExistPreview(HorizontalPanel labeltypo, EntityCatalogElements act) {
					for (int i = 0; i < labeltypo.getWidgetCount(); i++) {
						EntityCatalogElements temp = ((ButtonTipo)labeltypo.getWidget(i)).getEntidad();
						if (temp.getEntry().getId()==act.getEntry().getId()) return true;
						
					}
					return false;
				}
		        });
		 
		 FinderKeys.setButtonTipo(new BotonesStackPanelReaderSelectMio("prototipo", new VerticalPanel(),HP));
		 FinderKeys.setBotonClick(new ClickHandlerMioSelector(CT));
	}
	
	public void RefrescaLosDatos() {
        finder.RefrescaLosDatos();
	}
}
