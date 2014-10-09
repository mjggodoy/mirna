package old;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * @author Esteban López Camacho
 * 
 */
public class ExtraeTodoBruto {
	
	private String fileName = "salida";

	private String defaultGraph = null;
	public String endpoint = "http://diwis.imis.athena-innovation.gr:8181/sparql";
//	private String maxTimeOut = "5000";

	private int limit = 5000;
	
	public ExtraeTodoBruto() {
		System.setProperty("http.proxyHost", "proxy.uma.es");
		System.setProperty("http.proxyPort", "3128");
	}

	public void setDefaultGraph(String defaultGraph) {
		this.defaultGraph = defaultGraph;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

//	protected Model executeQuery(String query) {
//
//		// ResultSet rsf = null;
//		Model model = null;
//
//		try {
//
//			Query q = QueryFactory.create(query);
//			QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(
//					endpoint, q);
//			qexec.addParam("timeout", maxTimeOut);
//
//			// rsf = qexec.execSelect();
//			model = qexec.execConstruct();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//
//		}
//
//		return model;
//
//	}

	public void execute() throws IOException {

		// String queryClass = "select distinct ?Concept where {[] a ?Concept}";
		String queryIndividual = "construct {?s ?p ?o} where { ?s ?p ?o. } ";
		// construct {?s ?p ?o} where ?s ?p ?o limit 1000 offset 1000

		int offset = 0;
		boolean ended = false;
		int count = 1;

//		while (!ended) {

			String queryString = queryIndividual.replaceAll("%%limit%%",
					String.valueOf(limit)).replaceAll("%%offset%%",
					String.valueOf(offset));
			System.out.println(queryString);
	
			Query q = QueryFactory.create(queryString, Syntax.syntaxSPARQL);
			QueryExecution qe;
			if (defaultGraph != null) {
				qe = QueryExecutionFactory.sparqlService(endpoint, q, defaultGraph);
			} else {
				qe = QueryExecutionFactory.sparqlService(endpoint, q);
			}
			
//			String rutaFichero = fileName + count + ".rdf";
			String rutaFichero = fileName + "_completo.rdf";
			File file = new File(rutaFichero);
			
			
			Model model = qe.execConstruct();
			
			FileOutputStream fop = new FileOutputStream(file);
			model.write(fop, "RDF/XML") ;
			fop.close();
			
			System.out.println("Tanda numero " + count + " ha recuperado " + model.size() + " resultados.");
//			System.out.println(model.size());
//			System.out.println(model.listStatements().toList().size());
			
			count++;
	
	//		ResultSet results = null;
	//		
	//
	//		while (results == null) {
	//			try {
	//				results = qe.execSelect();
	//			} catch (Exception e) {
	//				e.printStackTrace();
	//				try {
	//					Thread.sleep(10 * 1000);
	//					// System.out.println(queryString);
	//				} catch (InterruptedException e1) {
	//					e1.printStackTrace();
	//				}
	//			}
	//		}
	
			// CONTAR EL NUMERO DE RESULTADOS
	
			long nresults = model.size();
			if (nresults < limit) {
				ended = true;
			} else {
				offset += limit;
			}

		}
//	}
	
	public static void main(String[] args) throws IOException {
		ExtraeTodoBruto extraeTodoBruto = new ExtraeTodoBruto();
		extraeTodoBruto.execute();
		
		
	}
}
