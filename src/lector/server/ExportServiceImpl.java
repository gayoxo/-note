package lector.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

import lector.client.book.reader.ExportService;
import lector.client.controler.Constants;
import lector.share.model.Annotation;
import lector.share.model.ExportObject;
import lector.share.model.GeneralException;
import lector.share.model.Professor;
import lector.share.model.ProfessorNotFoundException;

import lector.share.model.Template;
import lector.share.model.TemplateCategory;
import lector.share.model.TemplateCategoryNotFoundException;
import lector.share.model.TemplateNotFoundException;

import lector.share.model.client.TemplateCategoryClient;
import lector.share.model.client.TemplateClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ExportServiceImpl extends RemoteServiceServlet implements
		ExportService {

	private EntityManager em;
	private String PERSISTENCE_UNIT_NAME = "System";
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	@Resource
	UserTransaction userTransaction;

	@Override
	public void saveTemplate(TemplateClient templateClient)
			throws GeneralException {
		try {
			Template template;
			if (templateClient.getId() != null) {
				template = findTemplate(templateClient.getId());
				remplaceCamps(templateClient, template);

			} else {
				Professor professor = findProfessor(templateClient
						.getProfessor());
				template = new Template(templateClient.getName(),
						templateClient.getModifyable() ? (short) 1 : 0,
						professor);
				professor.getTemplates().add(template);
			}
			saveTemplate(template);

		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException pnfe) {
			pnfe.printStackTrace();
		}

	}

	private void remplaceCamps(TemplateClient templateClient, Template t) {
		t.setName(templateClient.getName());
		if (templateClient.getModifyable())
			t.setModifyable((short) 1);
		else
			t.setModifyable((short) 0);

	}

	private Professor findProfessor(Long id) throws ProfessorNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Professor a = entityManager.find(Professor.class, id);
		if (a == null) {
			throw new ProfessorNotFoundException(
					"Professor not found in method loadProfessorById");
		}
		entityManager.close();
		return a;
	}

	private TemplateCategory findTemplateCategory(Long id)
			throws TemplateCategoryNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		TemplateCategory a = entityManager.find(TemplateCategory.class, id);
		entityManager.close();
		return a;
	}

	private Template findTemplate(Long id) throws TemplateNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Template a = entityManager.find(Template.class, id);
		if (a == null) {
			throw new TemplateNotFoundException(
					"Template not found in method loadTemplateById");
		}
		entityManager.close();
		return a;
	}

	private void saveTemplate(Template template) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (template.getId() == null) {
				entityManager.persist(template);
				entityManager.merge(template.getProfessor());
			} else {
				entityManager.merge(template);
			}
			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction); // TODO utilizar
															// m�todo de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	@Override
	public TemplateClient loadTemplateById(Long id)
			throws TemplateNotFoundException, GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Template> list;

		String sql = "SELECT r FROM Template r WHERE r.id=" + id;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadTemplateById: ", e)
			throw new GeneralException("Exception in method loadTemplateById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadTemplateById: ", e)
			throw new TemplateNotFoundException(
					"Template not found in method loadTemplateById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		Template template = list.get(0);
		TemplateClient tClient = ServiceManagerUtils
				.produceTemplateClient(template);
		TemplateGenerator.Start(tClient, template);
		return tClient;
	}

	@Override
	public List<TemplateClient> getTemplates() throws GeneralException,
			TemplateNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Template> list;

		String sql = "SELECT r FROM Template r";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getTemplates:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new TemplateNotFoundException(
					"Template not found in method getTemplates");

		}

		List<TemplateClient> templateClients = new ArrayList<TemplateClient>();
		for (int i = 0; i < list.size(); i++) {
			Template template = list.get(i);
			TemplateClient tClient = ServiceManagerUtils
					.produceTemplateClient(template);
			TemplateGenerator.Start(tClient, template);
			templateClients.add(tClient);
		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return templateClients;
	}

	@Override
	public void deleteTemplate(Long templateId) throws GeneralException {
		try {
		Template template = findTemplate(templateId);
		for (TemplateCategory hijos : template.getCategories()) {
			deleteTemplateCategory(hijos.getId());
		}
		
		EntityManager entityManager = emf.createEntityManager();

		template = entityManager.find(Template.class, templateId);
			userTransaction.begin();
			template.getProfessor().getTemplates().size();
			template.getProfessor().getTemplates().remove(template);
			entityManager.merge(template.getProfessor());
			template.setProfessor(null);
			entityManager.remove(template);
			entityManager.flush();
			// /entityManager.merge(annotation.getActivity());
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Exception in method deleteAnnotationById" + e.getMessage(),
					e.getStackTrace());
		}

	}

	@Override
	public void saveTemplateCategory(
			TemplateCategoryClient templateCategoryClient)
			throws GeneralException {
		TemplateCategory father = null;
		Template template;
		TemplateCategory templateCategory = null;
		try {
			template = findTemplate(templateCategoryClient.getTemplate()
					.getId());
			if (templateCategoryClient.getId() != null) {
				templateCategory = findTemplateCategory(templateCategoryClient
						.getId());
				updateTemplateCategory(templateCategoryClient, templateCategory);
			} else {
				templateCategory = new TemplateCategory(
						templateCategoryClient.getName(), father, template);
			}

			if (templateCategoryClient.getFather() != null) {
				father = findTemplateCategory(templateCategoryClient
						.getFather().getId());
				templateCategory.setFather(father); 
				father.getSubCategories().add(templateCategory);
				templateCategory.setOrderInFather(father.getSubCategories().size());
				
			} else {
				template.getCategories().add(templateCategory);
				templateCategory.setOrderInFather(template.getCategories().size());
				
			}
			saveTemplateCategory(templateCategory);
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (TemplateCategoryNotFoundException tcnfe) {
			tcnfe.printStackTrace();
		}

	}

	private void updateTemplateCategory(
			TemplateCategoryClient templateCategoryClient,
			TemplateCategory templateCategory) {
		templateCategory.setName(templateCategoryClient.getName());

	}

	public void saveTemplateCategory(TemplateCategory templateCategory)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (templateCategory.getId() == null) {
				entityManager.persist(templateCategory);
				
			} else {
				entityManager.merge(templateCategory);
			}
			if (templateCategory.getFather() != null) {
				entityManager.merge(templateCategory.getFather());
			}
			entityManager.merge(templateCategory.getTemplate());
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}

	//
	// Template template = loadTemplateById(templateId);
	// TemplateCategory category = loadTemplateCategoryById(categoryId);
	// removeCategoryFromParent(fromFatherId, categoryId, templateId);
	// addNewFatherToCategory(toFatherId, categoryId);
	//
	// if (fromFatherId.equals(Constants.TEMPLATEID)) {
	// template = loadTemplateById(templateId);
	// updateOrderToLeftBrothers(template.getCategories(), category.getOrder());
	// } else {
	// TemplateCategory fromFatherCategory =
	// loadTemplateCategoryById(fromFatherId);
	// updateOrderToLeftBrothers(fromFatherCategory.getSubCategories(),
	// category.getOrder());
	// }
	// if (toFatherId.equals(Constants.TEMPLATEID)) {
	// updateSelfOrder(template.getCategories().size(), categoryId);
	// addChildToTemplate(categoryId, templateId);
	// } else {
	// TemplateCategory fatherCategory = loadTemplateCategoryById(toFatherId);
	// updateSelfOrder(fatherCategory.getSubCategories().size(),
	// categoryId);
	// addChildToCategory(categoryId, toFatherId);
	// }

	@Override
	public void moveCategory(Long toFatherId, Long categoryId, Long templateId) {
		Template template;
		try {
			template = findTemplate(templateId);
			TemplateCategory templateCategory = findTemplateCategory(categoryId);
			TemplateCategory newFather = findTemplateCategory(toFatherId);

			if (newFather == null) {
				//No distingo que el destino sea null porque no se puede mover dentro de el template, para eso es el cambio de pesos.
				if (!template.getCategories().contains(templateCategory)) {
					template.getCategories().add(templateCategory);

				}
				TemplateCategory fromFather = templateCategory.getFather();
				fromFather.getSubCategories().remove(templateCategory);
				templateCategory.setFather(newFather);
				persistObjectsForCategoryMove(template, null, fromFather);
				templateCategory.setOrderInFather(template.getCategories().size());
				saveTemplateCategory(templateCategory);
				reorderChildren(fromFather.getSubCategories());
				

			} else {
				if (!newFather.getSubCategories().contains(templateCategory)) {
					newFather.getSubCategories().add(templateCategory);
				}
				TemplateCategory fromFather = templateCategory.getFather();
				if (fromFather==null)
				{
					template=removeCategorieFromTemplate(template, templateCategory);
					templateCategory.setFather(newFather);
					persistObjectsForCategoryMove(template, newFather, null);
					reorderChildren(template.getCategories());
					templateCategory.setOrderInFather(newFather.getSubCategories().size());
					saveTemplateCategory(templateCategory);
				}
				else
				{
					fromFather.getSubCategories().remove(templateCategory);
					templateCategory.setFather(newFather);
					persistObjectsForCategoryMove(template, newFather, fromFather);
					reorderChildren(fromFather.getSubCategories());
					templateCategory.setOrderInFather(newFather.getSubCategories().size());
					saveTemplateCategory(templateCategory);
				}
				
			}

		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (TemplateCategoryNotFoundException tcnfe) {
			tcnfe.printStackTrace();
		} catch (GeneralException e) {
			e.printStackTrace();
		}

	}

	// private void updateOrderToLeftBrothers(List<TemplateCategory> categories,
	// int leavingWeight) {
	// // Collections.sort(categories);
	// updateOrder(categories, leavingWeight);
	// }

	// private void updateOrder(List<TemplateCategory> categories, int weight) {
	//
	// for (int i = weight - 1; i < categories.size(); i++) {
	// categories.get(i).setOrder(categories.get(i).getOrder() - 1);
	//
	// }
	//
	// }

	private Template removeCategorieFromTemplate(Template template,
			TemplateCategory templateCategory) {
			TemplateCategory found=null;
			for (TemplateCategory candidate : template.getCategories()) {
				if (candidate.getId().equals(templateCategory.getId()))
					{
					found=candidate;
					break;
					}
				
			}
			if (found!=null)
				template.getCategories().remove(found);
			else System.err.println("Error intentas borrar un template category que en la base de datos no existe en el template");
			return template;
		
	}

	@Override
	public void deleteTemplateCategory(Long templateCategoryId)
			throws GeneralException {
		try {
			TemplateCategory templateCategory = findTemplateCategory(templateCategoryId);
			TemplateCategory Father=templateCategory.getFather();
			if (Father==null)
			{
				Template tenTemplate= templateCategory.getTemplate();
				tenTemplate.getCategories().remove(templateCategory);
				saveTemplate(tenTemplate);
				reorderChildren(tenTemplate.getCategories());
			}else
			{
				Father.getSubCategories().remove(templateCategory);
				saveTemplateCategory(templateCategory.getFather());
				reorderChildren(Father.getSubCategories());
				
			}
			if (templateCategory.getSubCategories().size()!=0)
				for (TemplateCategory hijos : templateCategory.getSubCategories()) {
					deleteTemplateCategory(hijos.getId());
				}
		EntityManager entityManager = emf.createEntityManager();


			userTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM TemplateCategory s WHERE s.id="
							+ templateCategoryId).executeUpdate();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Exception in method deleteTemplateCategoryById"
							+ e.getMessage(), e.getStackTrace());
		}
		
	}

	@Override
	public Template removeCategoriesFromTemplate(Long templateId,
			List<Long> categoriesIds) {
	
		return null;
	}

	@Override
	public void swapCategoryWeight(Long movingCategoryId, Long staticCategoryId) {
		try {
		TemplateCategory T1 = findTemplateCategory(movingCategoryId);
		TemplateCategory T2=findTemplateCategory(staticCategoryId);
		Integer T1O=T1.getOrderInFather();
		Integer T2O=T2.getOrderInFather();
		T2.setOrderInFather(T1O);
		T1.setOrderInFather(T2O);
		saveTemplateCategory(T1);
		saveTemplateCategory(T2);
		} catch (TemplateCategoryNotFoundException e) {
			e.printStackTrace();
		} catch (GeneralException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<TemplateClient> getTemplatesByIds(List<Long> ids) {
		List<TemplateClient> templateClients = new ArrayList<TemplateClient>();
		for (int i = 0; i < ids.size(); i++) {
			Template template;
			try {
				template = findTemplate(ids.get(i));
				TemplateClient tClient = ServiceManagerUtils
						.produceTemplateClient(template);
				TemplateGenerator.Start(tClient, template);
				templateClients.add(tClient);
			} catch (TemplateNotFoundException e) {
				e.printStackTrace();
			}

		}

		return templateClients;
	}

	private void persistObjectsForCategoryMove(Template template,
			TemplateCategory toCategory, TemplateCategory fromCategory) {
		int flag = 1;
		if (toCategory == null) {
			flag = 2;
		}
		if (fromCategory == null) {
			flag = 3;
		}
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();

			switch (flag) {
			case 1:
				entityManager.merge(toCategory);
				entityManager.merge(fromCategory);
				break;
			case 2:
				entityManager.merge(template);
				entityManager.merge(fromCategory);
				break;
			default:
				entityManager.merge(template);
				entityManager.merge(toCategory);
				break;
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction); // TODO utilizar
															// m�todo de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	private void reorderChildren(List<TemplateCategory> list) {
		if (list.size()>0)
			quickSort(list,0,list.size()-1);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setOrderInFather(i+1);
			try {
				saveTemplateCategory(list.get(i));
			} catch (GeneralException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	private int partition(List<TemplateCategory> list, int left, int right)
	{
	      int i = left, j = right;
	      TemplateCategory tmp;
	      int pivot = list.get((left + right) / 2).getOrderInFather();
	      while (i <= j) {
	            while (list.get(i).getOrderInFather() < pivot)
	                  i++;
	            while (list.get(j).getOrderInFather() > pivot)
	                  j--;
	            if (i <= j) {
	                  tmp = list.get(i);
	                  list.set(i,list.get(j));
	                  list.set(j,tmp);
	                  i++;
	                  j--;
	            }
	      };
	     
	      return i;
	}
	 
	private void quickSort(List<TemplateCategory> list, int left, int right) {
	      int index = partition(list, left, right);
	      if (left < index - 1)
	            quickSort(list, left, index - 1);
	      if (index < right)
	            quickSort(list, index, right);
	}
}
