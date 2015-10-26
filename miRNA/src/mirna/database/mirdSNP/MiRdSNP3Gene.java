package mirna.database.mirdSNP;
import java.sql.ResultSet;

import org.hibernate.Session;

import mirna.beans.Gene;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP3Gene extends MiRdSNP3 {

	public MiRdSNP3Gene() throws MiRnaException {
		super(); 
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception{

		String gene_id = rs.getString("gene");
		//System.out.println(gene_id);
		Gene gene = new Gene();
		gene.setName(gene_id);
		session.save(gene);
		
	}

}