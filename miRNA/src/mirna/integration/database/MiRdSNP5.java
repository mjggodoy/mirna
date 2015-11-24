package mirna.integration.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mirna.integration.beans.Disease;
import mirna.integration.beans.SNP;
import mirna.integration.beans.nToM.SnpHasDisease;
import mirna.integration.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP5 extends MiRdSNP {

	private static final String TABLE_NAME = "miRdSNP5";

	public MiRdSNP5() throws MiRnaException { super(TABLE_NAME); }

	public void insertInTable(String csvInputFile) throws Exception {

		Connection con = null;
		String line = null;
		String[] tokens = null, tokens2 = null;

		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement(); 

			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);

			int count = 0;

			br.readLine();
			br.readLine();
			br.readLine();



			while (((line = br.readLine()) != null)) {

				count++;
				System.out.println(count);

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				String chromosome = tokens[0];
				String position_initial = tokens[1];
				String position_final = tokens[2];
				String disease_SNP = tokens[3];
				tokens2 = StringUtils.splitPreserveAllTokens(disease_SNP, ":");
				String SNP = tokens2[0];
				String disease = tokens2[1].replaceAll("'", "\\\\'");
				String orientation = tokens[5];

				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ chromosome + "','"
						+ position_initial + "','"
						+ position_final + "','"
						+ SNP + "','"
						+ disease + "','"
						+ orientation + "')";

				stmt.executeUpdate(query);
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

	public void processRow(Session session, ResultSet rs) throws Exception{

		String chromosome = nullifyField(rs.getString("chromosome").toLowerCase().trim());
		String position = nullifyField(rs.getString("start").toLowerCase().trim());
		String snp_name = nullifyField(rs.getString("snp").toLowerCase().trim());
		String disease_name = nullifyField(rs.getString("disease").toLowerCase().trim());
		String orientation = nullifyField(rs.getString("orientation").toLowerCase().trim());

		String[] diseaseTokens = StringUtils.splitPreserveAllTokens(disease_name, "|");
		List<Disease> diseaseList = new ArrayList<Disease>();
		for (String token : diseaseTokens) {
			Disease disease = new Disease();
			disease.setName(token);
			diseaseList.add(disease);
		}

		SNP snp = new SNP();
		snp.setChromosome(chromosome);
		snp.setSnp_id(snp_name);
		snp.setPosition(position);
		snp.setOrientation(orientation);
		if (!createdObject(chromosome, snp_name, position, orientation)){	
			snp = null;
		}

		if(snp !=null){
			Object oldSnp = session.createCriteria(SNP.class)
					.add( Restrictions.eq("snp_id", snp.getSnp_id()))
					.uniqueResult();
			if (oldSnp==null) {
				session.save(snp);
				session.flush();  // to get the PK
			} else {
				SNP snpToUpdate = (SNP) oldSnp;
				snpToUpdate.update(snp);
				session.update(snpToUpdate);
				snp = snpToUpdate;
			}
		}

		for(Disease disease : diseaseList){

			Object oldDisease = session.createCriteria(Disease.class)
					.add( Restrictions.eq("name", disease.getName()) )
					.uniqueResult();
			if (oldDisease==null) {
				session.save(disease);
				session.flush();  // to get the PK
				//System.out.println("Salvo ESTE disease:");
				//System.out.println(snp);

			} else {
				Disease diseaseToUpdate = (Disease) oldDisease;
				diseaseToUpdate.update(disease);
				session.update(diseaseToUpdate);
				disease = diseaseToUpdate;
				//System.out.println("Recupero ESTE disease:");
				//System.out.println(snp);
			}

			if(snp !=null){
				SnpHasDisease snpHasDisease = new SnpHasDisease(snp.getPk(), disease.getPk());
				Object oldSnphasDisease = session.createCriteria(SnpHasDisease.class)
						.add( Restrictions.eq("snpPk", snp.getPk()) )
						.add( Restrictions.eq("diseasePk", disease.getPk()) )
						.uniqueResult();
				if (oldSnphasDisease==null) {
					session.save(snpHasDisease);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		MiRdSNP5  miRdSNP5= new MiRdSNP5();
		miRdSNP5.insertIntoSQLModel();
	}

}