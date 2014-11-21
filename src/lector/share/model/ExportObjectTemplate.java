package lector.share.model;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ExportObjectTemplate extends ExportObject implements Serializable, IsSerializable{

	private int profundidad;
	private String Text;
	
	public ExportObjectTemplate() {
		super();
	}
	
	public ExportObjectTemplate(int profundidadin, String Textin) {
		profundidad=profundidadin;
		Text=Textin;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}
	
	
}
