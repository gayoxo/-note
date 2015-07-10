package lector.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import lector.share.model.UserApp;

@Path("atnote")
public class ServicesRest  extends RemoteServiceServlet {

	private String PERSISTENCE_UNIT_NAME = "System";
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	
	
	
	@Resource
	UserTransaction userTransaction;
	
		@Path("users")
		@GET
		public String doGreet() {
			/*
			try{
				EntityManager entityManager = emf.createEntityManager();
			List<UserApp> list=new ArrayList<UserApp>();

			String sql = "SELECT r FROM UserApp r";
				list = entityManager.createQuery(sql).getResultList();
			
			if (list == null) {
				list=new ArrayList<UserApp>();
				

			}
			if (entityManager.isOpen()) {
				entityManager.close();
			}

			Collection<UserJson> col=new ArrayList<UserJson>();
			
			for (UserApp student : list) {
				col.add(new UserJson(student.getEmail(), student.getPassword()));
			}
			
			Gson gson = new Gson();
			String jsonString = gson.toJson(col);
			
			return jsonString;
			} catch (Exception e) {
				e.printStackTrace();
				return e.toString();
			}
			*/
			return "Hola Mundo en mi servicio de Prueba";
		}
		
			@Path("getStructure/{colId_id}")
			@GET
			public String doStructure(
					@PathParam("colId_id") String colId_id) {
				
				if (colId_id!=null)
					return colId_id;
				/*
				try{
					EntityManager entityManager = emf.createEntityManager();
				List<UserApp> list=new ArrayList<UserApp>();

				String sql = "SELECT r FROM UserApp r";
					list = entityManager.createQuery(sql).getResultList();
				
				if (list == null) {
					list=new ArrayList<UserApp>();
					

				}
				if (entityManager.isOpen()) {
					entityManager.close();
				}

				Collection<UserJson> col=new ArrayList<UserJson>();
				
				for (UserApp student : list) {
					col.add(new UserJson(student.getEmail(), student.getPassword()));
				}
				
				Gson gson = new Gson();
				String jsonString = gson.toJson(col);
				
				return jsonString;
				} catch (Exception e) {
					e.printStackTrace();
					return e.toString();
				}
				*/
				return "Hola Mundo en mi servicio de Prueba";
			}
}
