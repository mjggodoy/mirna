package mirna.database.mirdip;

import java.sql.ResultSet;

import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.exception.MiRnaException;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de mirDIP
 * 
 * @author Esteban López Camacho
 *
 */
public class MirDIPMiRNAInteractionData extends MirDIP {


	public MirDIPMiRNAInteractionData() throws MiRnaException {
		super();
		super.selectQuery = "SELECT microrna, gene_symbol, source, rank from %s LIMIT 10";
	}

	@Override
	public void processRow(Session session, ResultSet rs) throws Exception {

		String microrna = rs.getString("microrna");
		String geneSymbol = rs.getString("gene_symbol");
		String source = rs.getString("source");
		String rank = rs.getString("rank");
		
		System.out.println(microrna + " " + geneSymbol + " " + source + " " + rank);

		/*MiRna miRna = new MiRna();
		miRna.setName(microrna);

		Gene gene = new Gene();
		gene.setName(geneSymbol);

		InteractionData id = new InteractionData();
		id.setRank(rank);
		id.setProvenance("mirDIP (" + source + ")");*/

		/*// Inserta MiRna (o recupera su id. si ya existe)
		Object oldMiRna = session.createCriteria(MiRna.class)
				.add( Restrictions.eq("name", miRna.getName()) )
				.uniqueResult();
		if (oldMiRna==null) {
			session.save(miRna);
			session.flush();  // to get the PK
		} else {
			miRna = (MiRna) oldMiRna;
		}

		// Inserta gene (o recupera su id. si ya existe)
		Object oldGene = session.createCriteria(Gene.class)
				.add(Restrictions.eq("name", gene.getName()))
				.uniqueResult();
		if (oldGene == null) {
			session.save(gene);
			session.flush(); // to get the PK
		} else {
			gene = (Gene) oldGene;
		}

		id.setMirna_pk(miRna.getPk());
		id.setGene_pk(gene.getPk());	
		session.save(id);
*/
	}	

	public static void main(String[] args) throws Exception {

		MirDIPMiRNAInteractionData mirdip = new MirDIPMiRNAInteractionData();
		mirdip.insertIntoSQLModel();

	}

}