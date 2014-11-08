package lector.client.controler.catalogo;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.ConstantsError;
import lector.client.controler.catalogo.client.EntityCatalogElements;
import lector.client.controler.catalogo.client.File;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeCategoryClient;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;


public class ElementKey extends Composite{
	
	private EntityCatalogElements Entidad;
	private ButtonKey Mas;
	private ButtonKey Label;
	private VerticalPanelEspacial NextBotones;
	private enum State {Open,Close};
	private State Actual;
	//private Image LargeP;
	private Image Compact;
	private Button NoSOns;
	private VerticalPanel verticalPanel;
	private Label Others;
	private boolean Selected;
	private ButtonKey Actulizador;
	private ArrayList<ElementKey> Otros;
	private String StlieOld;
	private VerticalPanel verticalPanel_1;
	private HorizontalPanel PanelOcultable;
	private Button BotonUp;
	private Button BotonDown;
	//private ElementKey Yo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	public static Finder finderAct;
	private HorizontalPanel horizontalPanel_2;
	private AbsolutePanel absolutePanel;
	private HorizontalPanel horizontalPanel_3;
	private Button Icon;
	private HorizontalPanel horizontalPanel_4;
	private SimplePanel Large11;
	private SimplePanel Large22;
	private SimplePanel LargeNN;
	private HorizontalPanel horizontalPanel_5;
	
	public ElementKey(EntityCatalogElements ent) {
		
		Entidad=ent;
		Selected=false;
	//	Yo=this;
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(horizontalPanel);
//		horizontalPanel.setSize("", "");
		
		verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(verticalPanel);
		
		Otros = new ArrayList<ElementKey>();
		Otros.add(this);
		
		HorizontalPanel BotonT = new HorizontalPanel();
		verticalPanel.add(BotonT);
		BotonT.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		BotonT.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		BotonT.setSize("100%", "100%");
		
		horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		BotonT.add(horizontalPanel_2);
		horizontalPanel_2.setHeight("100%");
		
		BotonUp = new Button("<img src=\"BotonesTemplate/Arriba.gif\" alt=\"<-\">");
		horizontalPanel_2.add(BotonUp);
		horizontalPanel_2.setCellVerticalAlignment(BotonUp, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_2.setCellHorizontalAlignment(BotonUp, HasHorizontalAlignment.ALIGN_CENTER);
		BotonUp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TypeCategoryClient EC=(TypeCategoryClient)Entidad.getFatherIdCreador();
				int i=0;
				int resultado=0;
				for (EntryClient hijos : EC.getChildren()) {
					if (hijos.getId().equals(Entidad.getEntry().getId()))
					{
						resultado=i;
					}
					i++;
				}
				if (resultado==0)
					{
					Window.alert("Top");
					}
				else
				{
					if (EC.getId().equals(Constants.CATALOGID))
					{
						EntryClient Aux	= EC.getCatalog().getEntries().get(resultado-1);
						EC.getCatalog().getEntries().set(resultado-1, EC.getCatalog().getEntries().get(resultado));
						EC.getCatalog().getEntries().set(resultado, Aux);
						saveCatalog(EC.getCatalog());
					}else{
					EntryClient Aux=EC.getChildren().get(resultado-1);
					EC.getChildren().set(resultado-1, EC.getChildren().get(resultado));
					EC.getChildren().set(resultado, Aux);
					saveEntry(EC);
					}
				}
			}

			private void saveEntry(EntryClient entrysave) {
				if (entrysave instanceof TypeCategoryClient)
				bookReaderServiceHolder.updateTypeCategory((TypeCategoryClient)entrysave, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						((FinderKeys)finderAct).RefrescaLosDatos();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ConstantsError.ERROR_ON_MOVE);
						((FinderKeys)finderAct).RefrescaLosDatos();
					}
				});
				
			}

			private void saveCatalog(CatalogoClient catalog) {
bookReaderServiceHolder.updateCatalog(catalog, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						((FinderKeys)finderAct).RefrescaLosDatos();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ConstantsError.ERROR_ON_MOVE);
						((FinderKeys)finderAct).RefrescaLosDatos();
					}
				});
			}


		});
		BotonUp.setSize("100%", "100%");
		
		BotonUp.setStyleName("gwt-ButtonCenterContinuoIzqEnd");
		BotonUp.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqEnd");
			}
		});
		BotonUp.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqEndOver");
			}
		});
		BotonUp.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqEndPush");
			}
		});
		BotonUp.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqEnd");
			}
		});
		
		BotonDown = new Button("<img src=\"BotonesTemplate/Abajo.gif\" alt=\"<-\">");
		horizontalPanel_2.add(BotonDown);
		horizontalPanel_2.setCellVerticalAlignment(BotonDown, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_2.setCellHorizontalAlignment(BotonDown, HasHorizontalAlignment.ALIGN_CENTER);
		BotonDown.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TypeCategoryClient EC=(TypeCategoryClient)Entidad.getFatherIdCreador();
				int i=0;
				int resultado=0;
				for (EntryClient hijos : EC.getChildren()) {
					if (hijos.getId().equals(Entidad.getEntry().getId()))
					{
						resultado=i;
					}
					i++;
				}
				if (resultado==EC.getChildren().size()-1)
					{
					Window.alert("Botton");
					}
				else
				{
					if (EC.getId().equals(Constants.CATALOGID))
					{
						EntryClient Aux	= EC.getCatalog().getEntries().get(resultado+1);
						EC.getCatalog().getEntries().set(resultado+1, EC.getCatalog().getEntries().get(resultado));
						EC.getCatalog().getEntries().set(resultado, Aux);
						saveCatalog(EC.getCatalog());
					}else{
					EntryClient Aux=EC.getChildren().get(resultado+1);
					EC.getChildren().set(resultado+1, EC.getChildren().get(resultado));
					EC.getChildren().set(resultado, Aux);
					saveEntry(EC);
					}
				}
			}

			private void saveEntry(EntryClient entrysave) {
				if (entrysave instanceof TypeCategoryClient)
				bookReaderServiceHolder.updateTypeCategory((TypeCategoryClient)entrysave, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						((FinderKeys)finderAct).RefrescaLosDatos();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ConstantsError.ERROR_ON_MOVE);
						((FinderKeys)finderAct).RefrescaLosDatos();
					}
				});
				
			}

			private void saveCatalog(CatalogoClient catalog) {
bookReaderServiceHolder.updateCatalog(catalog, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						((FinderKeys)finderAct).RefrescaLosDatos();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ConstantsError.ERROR_ON_MOVE);
						((FinderKeys)finderAct).RefrescaLosDatos();
					}
				});
			}


		});
		BotonDown.setSize("100%", "100%");
		BotonDown.setStyleName("gwt-ButtonCenterContinuoIzq");
		BotonDown.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzq");
			}
		});
		BotonDown.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqOver");
			}
		});
		BotonDown.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqPush");
			}
		});
		BotonDown.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzq");
			}
		});
		
		horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_4.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		BotonT.add(horizontalPanel_4);
		horizontalPanel_4.setSize("100%", "100%");
		
		Icon = new Button("");
		horizontalPanel_4.add(Icon);
		Icon.setStyleName("gwt-ButtonCenterContinuoNoBorder");
		Icon.setSize("100%", "100%");
		
		Label = new ButtonKey("New button",this);
		horizontalPanel_4.add(Label);
		Label.setSize("100%", "100%");
		//		Label.setStyleName("gwt-ButtonIzquierdaMIN");
		//		Label.addMouseOutHandler(new MouseOutHandler() {
		//			public void onMouseOut(MouseOutEvent event) {
		//				if (!Selected)
		//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaMIN");
		//			}
		//		});
		//		Label.addMouseOverHandler(new MouseOverHandler() {
		//			public void onMouseOver(MouseOverEvent event) {
		//				if (!Selected)
		//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOverMIN");
		//			}
		//		});
		//		Label.addMouseDownHandler(new MouseDownHandler() {
		//			public void onMouseDown(MouseDownEvent event) {
		//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPushMIN");
		//			}
		//		});
		//		Label.addMouseUpHandler(new MouseUpHandler() {
		//			public void onMouseUp(MouseUpEvent event) {
		//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaMIN");
		//			}
		//		});
				
				Label.setStyleName("gwt-ButtonCenterContinuoDoble");
				
						Label.addMouseDownHandler(new MouseDownHandler() {
							public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoDoblePush");
							}
						});
						
						Label.addMouseOutHandler(new MouseOutHandler() {
							public void onMouseOut(MouseOutEvent event) {
								if (!Selected)
									((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoDoble");
						}
						});
						
						Label.addMouseOverHandler(new MouseOverHandler() {
							public void onMouseOver(MouseOverEvent event) {
								if (!Selected)
									((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoDobleOver");
							
						}
	});
						
						Label.addMouseUpHandler(new MouseUpHandler() {
						public void onMouseUp(MouseUpEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoDoble");
						}
	});
		
		Mas = new ButtonKey("-",this);
		horizontalPanel_4.add(Mas);
		Mas.setSize("48px", "100%");
		Mas.setStyleName("gwt-ButtonDerechaMIN");
		
		
		NoSOns = new Button(" ");
		horizontalPanel_4.add(NoSOns);
		NoSOns.setSize("48px", "100%");
		NoSOns.setStyleName("gwt-ButtonDerecha");
		
		NoSOns.setVisible(false);
		Mas.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent event) {
						if (Actual==State.Close)
						{
					//	LargeP.setVisible(true);
							PanelOcultable.setVisible(true);
						Large11.setVisible(true);
						Large22.setVisible(true);
						//NextBotones.setVisible(true);
						Mas.setText("-");
						Actual=State.Open;
						Compact.setVisible(false);			
						}else 
						{
						//	LargeP.setVisible(false);
							PanelOcultable.setVisible(false);
							Large11.setVisible(false);
							Large22.setVisible(false);
							//NextBotones.setVisible(false);
							Mas.setText("+");
							Actual=State.Close;
							Compact.setVisible(true);	
						}
						if (Entidad instanceof File)
							isAFile();
						
					}
				});
		
		
		Mas.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaMIN");
			}
		});
		Mas.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
					((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOverMIN");
			}
		});
		Mas.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPushMIN");
			}
		});
		Mas.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaMIN");
			}
		});
		
		Others = new Label("Press to show others");
		Others.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (StlieOld!=null&&!StlieOld.isEmpty())
				{
				Label.setStyleName(StlieOld);

				for (ElementKey It : Otros) {
					It.setStyleNameLabelOld();
				}
					}
			}
		});
		Others.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				if (StlieOld!=null&&!StlieOld.isEmpty())
					{
					Label.setStyleName(StlieOld);

					for (ElementKey It : Otros) {
						It.setStyleNameLabelOld();
					}
						}
			}
		});
		Others.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				StlieOld=Label.getStyleName();
			//	Label.setStyleName("gwt-ButtonIzquierdaSelectMIN");
				for (ElementKey It : Otros) {
					It.setStyleNameLabelNew();
				}
			}
		});
		Others.setVisible(false);
		Others.setStyleName("gwt-LabelShowMore");
		
		verticalPanel.add(Others);
		
		Actulizador = new ButtonKey("Actualizacion",this);;

		verticalPanel.add(Actulizador);
		Actulizador.setVisible(false);
		Actual=State.Open;
		
		verticalPanel_1 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_1);
		
		
		Large11 = new SimplePanel();
		Large11.setStyleName("backgroundKeyUp");
		verticalPanel_1.add(Large11);
		Large11.setSize("11px", "7px");
		
		horizontalPanel_5 = new HorizontalPanel();
		horizontalPanel_5.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.add(horizontalPanel_5);
		horizontalPanel_5.setHeight("100%");
		
		
//		Large = new VerticalPanel();
//		horizontalPanel.add(Large);
//		horizontalPanel.setCellHorizontalAlignment(Large, HasHorizontalAlignment.ALIGN_CENTER);
//		Large.setSize("40px", "100%");
//		
//		LargeP = new Image("ArbolLine.jpg");
//		horizontalPanel.add(LargeP);
//				LargeP.setVisible(true);
//				LargeP.setSize("35px", "100%");
		
		Compact = new Image("ArbolLineChico2.png");
		horizontalPanel_5.add(Compact);
		Compact.setSize("13px", "34px");
		Compact.setVisible(false);
		
		PanelOcultable = new HorizontalPanel();
		horizontalPanel_5.add(PanelOcultable);
		PanelOcultable.setStyleName("backgroundKey");
		PanelOcultable.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		PanelOcultable.setHeight("100%");
		
		absolutePanel = new AbsolutePanel();
		PanelOcultable.add(absolutePanel);
		absolutePanel.setSize("7px", "100%");
		
		horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		absolutePanel.add(horizontalPanel_3);
		horizontalPanel_3.setSize("7px", "100%");
		
		LargeNN = new SimplePanel();
		horizontalPanel_3.add(LargeNN);
		LargeNN.setSize("7px", "20px");
		
		NextBotones = new VerticalPanelEspacial(this);
		PanelOcultable.add(NextBotones);
		NextBotones.setVisible(true);
		NextBotones.setSize("100%", "");
		
		Large22 = new SimplePanel();
		Large22.setStyleName("backgroundKeyDown");
		verticalPanel_1.add(Large22);
		Large22.setSize("11px", "7px");
		
		
	}

	protected void setStyleNameLabelOld() {
		Label.setStyleName(StlieOld);
		
	}

	protected void setStyleNameLabelNew() {
		StlieOld=Label.getStyleName();
		Label.setStyleName("gwt-ButtonCenterContinuoDobleSelect");
		LLamada_En_Cadena();
		
	}

	private void LLamada_En_Cadena() {
		setOpen();
		Widget VPContenedorW =getParent();
		if (VPContenedorW instanceof VerticalPanelEspacial)
		{
			VerticalPanelEspacial VPContenedor =(VerticalPanelEspacial)VPContenedorW;
			VPContenedor.getEK().setOpen();
			VPContenedor.getEK().LLamada_En_Cadena();
		
		}
		
	}

	private void setOpen() {
		//LargeP.setVisible(true);
		PanelOcultable.setVisible(true);
		Large11.setVisible(true);
		Large22.setVisible(true);
		//NextBotones.setVisible(true);
		Mas.setText("-");
		Actual=State.Open;
		Compact.setVisible(false);	
		if (Entidad instanceof File)
			isAFile();
		
		
	}

	public void addClickButtonMas(ClickHandler Clic) {
		Actulizador.addClickHandler(Clic);
		
		
		
	}
	
	public void addClickButton(ClickHandler Clic) {
		Label.addClickHandler(Clic);
		
	}

	public EntityCatalogElements getEntidad() {
		return Entidad;
	}

	public void removeItems() {
		NextBotones.clear();
		
	}
	
	public void setHTML(String S,String Text) {
		Label.setHTML(Text);
		Label.setStyleName("gwt-ButtonCenterContinuoDoble");
		Label.setSize("100%", "100%");
		Icon.setHTML("<img src=\""+ S +"\">");
	}

	public void addItem(ElementKey a) {
		NextBotones.add(a);
		
	}

	public void setText(String catalogName) {
		Label.setHTML(catalogName);
		
	}

	public void isAFile() {
		Mas.setVisible(false);
		NoSOns.setVisible(true);
		Compact.setVisible(false);
		//LargeP.setVisible(false);
		PanelOcultable.setVisible(false);
		Large11.setVisible(false);
		Large22.setVisible(false);
	//	NextBotones.setVisible(false);
		
	}
	
	public ButtonKey getMas() {
		return Mas;
	}
	
	public ButtonKey getLabel() {
		return Label;
	}
	
	public boolean isSelected() {
		return Selected;
	}
	
	public void setSelected(boolean selected) {
		Selected = selected;
	}
	
	public ButtonKey getActulizador() {
		return Actulizador;
	}
	
	public Label getOthers() {
		return Others;
	}

//	public void setlabelStyle(String string) {
//		Label.setStyleName(string);
//		for (ElementKey It : Otros) {
//			It.getLabel().setStyleName(string);
//		}
//		
//	}
	public ArrayList<ElementKey> getOtros() {
		return Otros;
	}
	
	public void setOtros(ArrayList<ElementKey> otros) {
		Otros = otros;
	}
	
	public static void setFinderAct(Finder finderAct) {
		ElementKey.finderAct = finderAct;
	}
	
	public static Finder getFinderAct() {
		return finderAct;
	}
	
	public void setBotonUpState(boolean botonUp) {
		BotonUp.setEnabled(botonUp);
		BotonUp.setVisible(botonUp);
		
	}
	
	public void setBotonDownState(boolean botonDown) {
		BotonDown.setEnabled(botonDown);
		BotonDown.setVisible(botonDown);
	}
}
