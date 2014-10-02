package lector.client.admin.tagstypes;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.catalogo.graph.PanelGrafo;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class VisualizeGraph extends PopupPanel {
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ScrollPanel simplePanel;
	private PanelGrafo PG;
	private VerticalPanel VP;
	private CatalogoClient Catalog;
	private DockLayoutPanel dockLayoutPanel;
	private HandlerRegistration Handele;
	
	public VisualizeGraph(CatalogoClient long1) {

		super(false);
		setModal(true);
		setGlassEnabled(true);
		setAnimationEnabled(true);
		Catalog=long1;
		setSize("100%", "100%");
		dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize(Window.getClientWidth()-25+"px", Window.getClientHeight()-25+"px");
		
		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 1.9);
		
		MenuItem mntmNewItem = new MenuItem("Close", false, new Command() {
			public void execute() {
				Handele.removeHandler();
				hide();
				
			}
		});
		mntmNewItem.setHTML("Close");
		menuBar.addItem(mntmNewItem);
		simplePanel = new ScrollPanel();
		dockLayoutPanel.add(simplePanel);
		simplePanel.setSize("100%", "100%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		simplePanel.setWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		PG=new PanelGrafo();
		horizontalPanel.add(PG);
		PanelGrafo.setAccionAsociada(null);
		
		Handele = Window.addResizeHandler(new ResizeHandler() {
			
			@Override
			public void onResize(ResizeEvent event) {
				dockLayoutPanel.setSize(Window.getClientWidth()-25+"px", Window.getClientHeight()-25+"px");
			}
		});

		
		
//		bookReaderServiceHolder.loadCatalogById(long1.getId(), new AsyncCallback<CatalogoClient>() {
//			
//			@Override
//			public void onSuccess(CatalogoClient result) {
//				PanelGrafo PG=new PanelGrafo(result);
//				simplePanel.add(PG);
//				
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert(ErrorConstants.ERROR_LOADING_CATALOG);
//				Logger.GetLogger().severe(Yo.getClass().toString(), ErrorConstants.ERROR_LOADING_CATALOG);
//				LoadingPanel.getInstance().hide();
//				
//			}
//		});
		
//		PanelFinderKey PFK=new PanelFinderKey(long1);
//		simplePanel.add(PFK);
		
		
	}

	public void Lanza() {
		
		PG.Go(Catalog);
		PG.Pinta();
		PG.refreshsize();
		
	}
	
	@Override
	public void show() {
		super.show();
		simplePanel.setVerticalScrollPosition((simplePanel.getMaximumVerticalScrollPosition()+1)/2);
		simplePanel.setHorizontalScrollPosition((simplePanel.getMaximumHorizontalScrollPosition()+1)/2);
	}
	
	@Override
	public void center() {
		super.center();
		
	}

}
