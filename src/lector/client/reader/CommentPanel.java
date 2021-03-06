package lector.client.reader;



import java.util.ArrayList;
import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.catalogo.client.File;
import lector.client.reader.hilocomentarios.ReplyDialog;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.TextSelectorClient;
import lector.share.model.client.TypeClient;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class CommentPanel extends Composite {

   // private RichTextArea richTextArea = new RichTextArea();
    private AnnotationClient annotation;
    private VerticalPanel verticalPanel = new VerticalPanel();
    private Button button_1 = new Button("+");
 //   private RichTextArea richTextAreaBoton = new RichTextArea();
    private ArrayList<SelectorPanel> SE;
    private final MenuBar menuBar = new MenuBar(false);
    private MenuItem mntmNewItem;
  //  private MenuItem mntmNewItem_1;
    private final MenuItemSeparator separator = new MenuItemSeparator();
    private MenuItem mntmNewItem_2;
    private Image Imagen;
    private Button button;
    private static boolean Estado;
    private ScrollPanel scrollPanel;
    private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
    private HorizontalPanel PanelTexto;
    private SimplePanel simplePanel;
    private ScrollPanel ScrollPanel;
    private HTMLPanel panel;
    private FocusPanel richTextArea2;
    private DecoratorPanel decoratorPanel_1;
	private int whithPanel;
    

public enum CatalogTipo {
		
		Catalog1("<img src=\"File.gif\" class=\"coment\"  >"), Catalog2("<img src=\"File2.gif\" class=\"coment\" >");
		
		private String Texto;
		
		private CatalogTipo(String A) {
			Texto=A;
		}
		
		public String getTexto() {
			return Texto;
		}
		
	};
	
    
    public CommentPanel(AnnotationClient annotationin, Image originalBook) {

        annotation = annotationin;
        Imagen = originalBook;
        SimplePanel decoratorPanel = new SimplePanel();
        decoratorPanel.setHeight("");
        initWidget(decoratorPanel);

        decoratorPanel.setWidget(verticalPanel);

        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        verticalPanel.add(horizontalPanel);
        horizontalPanel.setHeight("28px");

        String Showbutton= annotation.getComment().toString();
       
        Showbutton=Showbutton.replaceAll("\\<.*?\\>","");
        if (Showbutton.length()>20){
        	Showbutton=Showbutton.substring(0,20);
        	Showbutton=Showbutton+" ...";
        }  
        button = new Button(Showbutton);
        button.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		PopUPEXportacion PEX=MainEntryPoint.getPEX();
        		if (PEX!=null&&PEX.isShowing())
        			PEX.addAlement(new ElementoExportacion(annotation,Imagen));
        	}
        });
        button.setHTML(Showbutton);
        horizontalPanel.add(button);
        button.setEnabled(true);
        button.setVisible(true);
        button.setSize("254px", "30px");
        button.addMouseOutHandler(new MouseOutHandler() {

            public void onMouseOut(MouseOutEvent event) {
                if (SE != null) {
                	for (SelectorPanel SP : SE) {
                		SP.hide();
					}
                }
            }
        });
        button.addMouseOverHandler(new MouseOverHandler() {

            public void onMouseOver(MouseOverEvent event) {
            	MainEntryPoint.hidePopUpSelector();
				MainEntryPoint.hideDENSelector();
            	 if (SE != null) {
                 	for (SelectorPanel SP : SE) {
                 		SP.hide();
 					}
                 }
            	 SE=new ArrayList<SelectorPanel>();
            	 for (TextSelectorClient TS : annotation.getTextSelectors()) {
            		 SelectorPanel SEE = new SelectorPanel(TS.getX().intValue(),
            				 TS.getY().intValue(),
                             Imagen.getAbsoluteLeft(), Imagen.getAbsoluteTop(),
                             TS.getWidth().intValue(),
                             TS.getHeight().intValue());
                     if (!Estado) SEE.show();
                     SE.add(SEE);
				}
               
            }
        });
        button.setStyleName("gwt-ButtonIzquierda");
        button.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
			}
		});
        button.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOver");
			}
		});
        button.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
			}
		});
        button.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
			}
		});

        horizontalPanel.add(button);
//        button.setEnabled(true);
//        button.setVisible(false);
//        button.setSize("254px", "42px");


//
//        richTextAreaBoton.setHTML(annotation.getComment().toString());
//        richTextAreaBoton.setSize("254px", "38px");
//        horizontalPanel.add(richTextAreaBoton);
//        richTextAreaBoton.setEnabled(false);
//        richTextAreaBoton.setVisible(true);


        button_1.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (button_1.getText().contentEquals("+")) {
                    //verticalPanel.add(richTextArea);
                	decoratorPanel_1.setVisible(true);
                    menuBar.setVisible(true);
                    simplePanel.setVisible(true);
                    if (panel.getOffsetHeight()>174)
                    {
                    	ScrollPanel.setHeight("174px");
                    //	Window.alert("Tama�o reducido");
                    }
                    if (panel.getOffsetHeight()<1)
                    {
                    	ScrollPanel.setHeight("20px");
                    //	Window.alert("Tama�o reducido");
                    } 
                    	//  button.setVisible(true);
//                    richTextAreaBoton.setVisible(false);
                    button_1.setText("-");
                } else {
                    // verticalPanel.remove(richTextArea);
                	decoratorPanel_1.setVisible(false);
                   // button.setVisible(false);
//                    richTextAreaBoton.setVisible(true);
//                    richTextAreaBoton.setSize("254px", "38px");
                    button_1.setText("+");
                    menuBar.setVisible(false);
                    simplePanel.setVisible(false);
//                    horizontalPanel.clear();
//                    horizontalPanel.add(button);
////                    horizontalPanel.add(richTextAreaBoton);
//                    horizontalPanel.add(button_1);

                }
            }
        });
        button_1.setStyleName("gwt-ButtonDerecha");
        button_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
        button_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOver");
			}
		});
        button_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPush");
			}
		});
        button_1.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
        horizontalPanel.add(button_1);
        button_1.setSize("52px", "30px");
        
        decoratorPanel_1 = new DecoratorPanel();
        verticalPanel.add(decoratorPanel_1);
        decoratorPanel_1.setWidth("");
        
        //        richTextArea.setHTML(annotation.getComment().toString());
        //        richTextArea.setHeight("177px");
        //        verticalPanel.add(richTextArea);
        //        richTextArea.setEnabled(false);
                
                richTextArea2 = new FocusPanel();
                decoratorPanel_1.setWidget(richTextArea2);
                richTextArea2.setSize("297px", "100%");
                ScrollPanel = new ScrollPanel();
                richTextArea2.setWidget(ScrollPanel);
                ScrollPanel.setSize("297px", "100%");
                
                panel = new HTMLPanel(annotation.getComment().toString());
                panel.setStyleName("Blancoopaco");
                ScrollPanel.setWidget(panel);
                panel.setSize("100%", "100%");
                decoratorPanel_1.setVisible(false);
                richTextArea2.addClickHandler(new ClickHandler() {

                    public void onClick(ClickEvent event) {
                    	MainEntryPoint.hidePopUpSelector();
				MainEntryPoint.hideDENSelector();
                        if (annotation.isEditable()||(annotation.getCreator().getId().equals(ActualState.getUser().getId())||(ActualState.getUser() instanceof ProfessorClient)))
                        	{
                        	TextComentEdit TCE = new TextComentEdit(annotation,SE);
                        	TCE.center();
                        	}
                        else {
                        	TextComentNoEdit TCE= new TextComentNoEdit(annotation, SE);
                        	TCE.center();
                        }
                        

                    }
                });
                
                        richTextArea2.addMouseOutHandler(new MouseOutHandler() {
                
                            public void onMouseOut(MouseOutEvent event) {
                            	if (!Estado){
                            		 if (SE != null) {
                                      	for (SelectorPanel SP : SE) {
                                      		SP.hide();
                      					}
                                      }
                            	}
                            }
                        });
                        
                                richTextArea2.addMouseOverHandler(new MouseOverHandler() {
                        
                                    public void onMouseOver(MouseOverEvent event) {
                                    	if (!Estado){
                                    		 if (SE != null) {
                                              	for (SelectorPanel SP : SE) {
                                              		SP.hide();
                              					}
                                              }
                                    		 SE=new ArrayList<SelectorPanel>();
                                        	 for (TextSelectorClient TS : annotation.getTextSelectors()) {
                                        		 SelectorPanel SEE = new SelectorPanel(TS.getX().intValue(),
                                        				 TS.getY().intValue(),
                                                         Imagen.getAbsoluteLeft(), Imagen.getAbsoluteTop(),
                                                         TS.getWidth().intValue(),
                                                         TS.getHeight().intValue());
                                                 if (!Estado) SEE.show();
                                                 SE.add(SEE);
                            				}
                                        }
                                    }
                                });
//        richTextArea2.setVisible(false);
        
        simplePanel = new SimplePanel();
        verticalPanel.add(simplePanel);
        simplePanel.setSize("306px", "45px");
        
        scrollPanel = new ScrollPanel();
        scrollPanel.setStyleName("Blancoopaco");
        simplePanel.setWidget(scrollPanel);
        scrollPanel.setAlwaysShowScrollBars(true);
        simplePanel.setVisible(false);
        scrollPanel.setSize("", "50px");
        PanelTexto= new HorizontalPanel();
        PanelTexto.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        scrollPanel.setWidget(PanelTexto);
        PanelTexto.setSize("447px", "31px");
        
        verticalPanel.add(menuBar);
        menuBar.setWidth("306px");
        
        mntmNewItem = new MenuItem("New item", false, new Command() {
        	public void execute() {
        	ReplyDialog RD=new ReplyDialog(null, annotation);
        	RD.center();
        	}
        });
        mntmNewItem.setHTML("Reply");
        menuBar.addItem(mntmNewItem);
        
//        mntmNewItem_1 = new MenuItem("New item", false, new Command() {
//        	public void execute() {
//        		Window.alert("Borrar");
//        	}
//        });
        menuBar.setVisible(false);
//        mntmNewItem_1.setEnabled(false);
//        mntmNewItem_1.setHTML("Delete");
//        menuBar.addItem(mntmNewItem_1);
        
        menuBar.addSeparator(separator);
        
        mntmNewItem_2 = new MenuItem("New item", false, (Command) null);
        mntmNewItem_2.setStyleName("gwt-MenuItemPanel");
        menuBar.addItem(mntmNewItem_2);
       
        

//tocado        
       mntmNewItem_2.setText(annotation.getCreator().getFirstName()+ " " + annotation.getCreator().getLastName().charAt(0)  + ". --- " +DateTimeFormat.getShortDateFormat().format(annotation.getCreatedDate()));


//        bookReaderServiceHolder.getFilesByIds(annotation.getFileIds(),
//				new AsyncCallback<ArrayList<FileDB>>() {
//
//        		
//					public void onFailure(Throwable caught) {
//
//					}
//
//					public void onSuccess(ArrayList<FileDB> result) {
					//	System.out.println(annotation.getComment());
       					
       List<TypeClient> result = annotation.getTags();
						whithPanel=0;
						for (int i = 0; i < result.size(); i++) {
						TypeClient T=result.get(i);
						if (T.getCatalog().getId().equals(ActualState.getReadingactivity().getCloseCatalogo().getId()))
						{
							ButtonTipo B=new ButtonTipo(new File(T, T.getCatalog(), null),CatalogTipo.Catalog1.getTexto(),PanelTexto);
							B.addClickHandler(new ClickHandler() {
								
								public void onClick(ClickEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonCenterPequ");
									
								}
							});
						
				        	B.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonCenterPequPush");
								}
							});
							

				        	B.addMouseUpHandler(new MouseUpHandler() {
								public void onMouseUp(MouseUpEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonCenterPequ");
							}
						});
							

//				        	B.addMouseOverHandler(new MouseOverHandler() {
//								public void onMouseOver(MouseOverEvent event) {
//									
//									((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
//								
//							}
//						});

				        	B.setStyleName("gwt-ButtonCenterPequ");
//							if (annotation.isEditable()) {
//								B.addClickHandler(new ClickHandler() {
//								
//								public void onClick(ClickEvent event) {
//									ButtonTipo Yo=(ButtonTipo)event.getSource();
//									Yo.getPertenezco().remove(Yo);
//									
//								}
//								});
//							}
				        	int TamAdd=B.getText().length()*10+19;
//				        	System.out.println(TamAdd);
				        	B.setWidth(TamAdd+"px");
				        	whithPanel=whithPanel+(TamAdd);
//				        	System.out.println(whithPanel+"px");
				        	PanelTexto.setWidth(whithPanel+"px");
							PanelTexto.add(B);
						}
							else{
								ButtonTipo B=new ButtonTipo(new File(T, T.getCatalog(), null),CatalogTipo.Catalog2.getTexto(),PanelTexto);
//								if (annotation.isEditable()) {
								B.addClickHandler(new ClickHandler() {
									
									public void onClick(ClickEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenterPequ");
										
									}
								});
							
					        	B.addMouseDownHandler(new MouseDownHandler() {
									public void onMouseDown(MouseDownEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenterPequPush");
									}
								});
								

					        	B.addMouseUpHandler(new MouseUpHandler() {
									public void onMouseUp(MouseUpEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenterPequ");
								}
							});
								

//					        	B.addMouseOverHandler(new MouseOverHandler() {
//									public void onMouseOver(MouseOverEvent event) {
//										
//										((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
//									
//								}
//							});

					        	B.setStyleName("gwt-ButtonCenterPequ");
//									B.addClickHandler(new ClickHandler() {
//
//										public void onClick(ClickEvent event) {
//											ButtonTipo Yo = (ButtonTipo) event
//													.getSource();
//											Yo.getPertenezco().remove(Yo);
//
//										}
//									});
//								}
					        	int TamAdd=B.getText().length()*10+19;
//					        	System.out.println(TamAdd);
					        	B.setWidth(TamAdd+"px");
					        	whithPanel=whithPanel+(TamAdd);
//					        	System.out.println(whithPanel+"px");
					        	PanelTexto.setWidth(whithPanel+"px");
								PanelTexto.add(B);
							}
						}
//					}
//				});


    }
    
    public static void setEstado(boolean estado) {
		Estado = estado;
	}
    
    public void hideSelectorPanel() {
		for (SelectorPanel SP : SE) {
			SP.hide();
		}
    	
	}
}
