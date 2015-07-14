package lector.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import lector.share.model.UserApp;

@Path("atnote")
public class ServicesRest  extends RemoteServiceServlet {

	private String PERSISTENCE_UNIT_NAME = "System";
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	private static final String DATA_DIRECTORY = "data";
	
	@Context 
	ServletContext context;
	
	
	@Resource
	UserTransaction userTransaction;
	
		@Path("users")
		@GET
		public String doGreet() {
			
			if (isActiveUsersModule())
			{
			
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
			
		//	return "Hola Mundo en mi servicio de Prueba";
			}
			return "-1";
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
			
			
			/**
			 * Genera la carpeta de upload
			 * 
			 * @return
			 */
			private boolean isActiveUsersModule() {
				String[] route = context.getRealPath("/").split(
						Pattern.quote(File.separator));
				// String[] route2=uri.getPath().split(Pattern.quote(File.separator));
				// String[] route =
				// getServletContext().getRealPath("").split(Pattern.quote(File.separator));
				StringBuffer uploadFolderSb = new StringBuffer();
				// uploadFolderSb.append(File.separator);
				for (int i = 0; i < route.length - 2; i++) {
					uploadFolderSb.append(route[i]);
					uploadFolderSb.append(File.separator);
				}

				uploadFolderSb.append("docroot");
				uploadFolderSb.append(File.separator + DATA_DIRECTORY + File.separator
						+ "modules" + File.separator);
				String uploadFolder = uploadFolderSb.toString();

				java.io.File directorio = new File(uploadFolder);
				directorio.mkdirs();
				
				java.io.File LogModule = new File(uploadFolder+"users.module");
				return LogModule.exists();
			}
}
