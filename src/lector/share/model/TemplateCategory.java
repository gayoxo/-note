package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "template_category")
public class TemplateCategory implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne
	private TemplateCategory father;
	@OneToMany(mappedBy = "father", cascade = CascadeType.ALL)
	private List<TemplateCategory> subCategories = new LinkedList<TemplateCategory>();
	@Transient
	private ArrayList<Long> annotationsIds = new ArrayList<Long>();

	
	@ManyToOne
	@JoinColumn
	private Template template;

	private Integer OrderInFather;

	public TemplateCategory() {
		annotationsIds = new ArrayList<Long>();
	}

	public TemplateCategory(String name,
			ArrayList<TemplateCategory> subCategories,
			ArrayList<Long> annotationsIds, TemplateCategory father,
			Template template) {
		super();
		this.name = name;
		this.subCategories = subCategories;
		this.annotationsIds = annotationsIds;
		this.father = father;
		this.template = template;
	}

	public TemplateCategory(String name,TemplateCategory father, Template template) {
		super();
		this.name = name;
		this.father = father;
		this.template = template;

	}

	
	/**
	 * @param name
	 * @param father
	 * @param template
	 * @param orderInFather
	 */
	public TemplateCategory(String name, TemplateCategory father,
			Template template, Integer orderInFather) {
		super();
		this.name = name;
		this.father = father;
		this.template = template;
		OrderInFather = orderInFather;
	}

	public ArrayList<Long> getAnnotationsIds() {
		return annotationsIds;
	}

	public void setAnnotationsIds(ArrayList<Long> annotationsIds) {
		this.annotationsIds = annotationsIds;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TemplateCategory getFather() {
		return father;
	}

	public void setFather(TemplateCategory father) {
		this.father = father;
	}

	public List<TemplateCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<TemplateCategory> subCategories) {
		this.subCategories = subCategories;
	}

	/**
	 * @return the orderInFather
	 */
	public Integer getOrderInFather() {
		return OrderInFather;
	}

	/**
	 * @param orderInFather the orderInFather to set
	 */
	public void setOrderInFather(Integer orderInFather) {
		OrderInFather = orderInFather;
	}

	
}
