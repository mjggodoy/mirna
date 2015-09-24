package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.exception.MiRnaException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de mirDIP
 * 
 * @author Esteban López Camacho
 *
 */
public class MirDIP extends NewMirnaDatabase {

	private  static final String TABLE_NAME = "mirDIP";

	public MirDIP() throws MiRnaException {
		super(TABLE_NAME);
	}

	public void insertInTable(String csvInputFile) throws Exception {

		Connection con = null;
		String line = null;
		String[] tokens = null;

		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement();

			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);

			int count = 0;

			br.readLine();

			while (((line = br.readLine()) != null)) {

				count++;
				System.out.println(count);

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				if (line != null) {

					String microrna = tokens[0].replaceAll("'", "\\\\'");
					String gene_symbol = tokens[1].replaceAll("'", "\\\\'");
					String source = tokens[2].replaceAll("'", "\\\\'");
					String rank = tokens[3].replaceAll("'", "\\\\'");

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ microrna + "','"
							+ gene_symbol + "','"
							+ source + "','"
							+ rank + "')";

					stmt.executeUpdate(query);

				}

			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}

	}

	@Override
	public void processRow(Session session, ResultSet rs) throws Exception {

		String microrna = rs.getString("microrna");
		String geneSymbol = rs.getString("gene_symbol");
		String source = rs.getString("source");
		String rank = rs.getString("rank");

		MiRna miRna = new MiRna();
		miRna.setName(microrna);

		Gene gene = new Gene();
		gene.setName(geneSymbol);

		InteractionData id = new InteractionData();
		id.setRank(rank);
		id.setProvenance("mirDIP (" + source + ")");

		// Inserta MiRna (o recupera su id. si ya existe)
		Object oldMiRna = session.createCriteria(MiRna.class)
				.add( Restrictions.eq("name", miRna.getName()) )
				.uniqueResult();
		if (oldMiRna==null) {
			session.save(miRna);
			session.flush();  // to get the PK
		} else {
			MiRna miRnaToUpdate = (MiRna) oldMiRna;
			miRnaToUpdate.update(miRna);
			session.update(miRnaToUpdate);
			miRna = miRnaToUpdate;
		}

		// Inserta gene (o recupera su id. si ya existe)
		Object oldGene = session.createCriteria(Gene.class)
				.add(Restrictions.eq("name", gene.getName()))
				.uniqueResult();
		if (oldGene == null) {
			session.save(gene);
			session.flush(); // to get the PK
		} else {
			Gene geneToUpdate = (Gene) oldGene;
			geneToUpdate.update(gene);
			session.update(geneToUpdate);
			gene = geneToUpdate;
		}

		id.setMirna_pk(miRna.getPk());
		id.setGene_pk(gene.getPk());	
		session.save(id);
		session.flush();

	}

	public static void main(String[] args) throws Exception {

		MirDIP mirdip = new MirDIP();
		mirdip.insertIntoSQLModel();

	}

}