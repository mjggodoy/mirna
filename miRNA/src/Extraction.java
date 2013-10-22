import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class Extraction {
	
	private String endpoint;
	private String maxTimeOut = "5000";
	
	public Extraction (String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	
	protected Model executeQuery(String query) {
		
		System.setProperty("http.proxyHost", "proxy.uma.es");
		System.setProperty("http.proxyPort", "3128");
		
//		ResultSet rsf = null;
		Model model = null;
		
		try {
			
			Query q = QueryFactory.create(query) ;
			QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(endpoint, q);
			qexec.addParam("timeout", maxTimeOut);
				
//			rsf = qexec.execSelect();
			model = qexec.execConstruct();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return model;
		
	}

}