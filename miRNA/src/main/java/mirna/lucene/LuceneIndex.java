package mirna.lucene;

import mirna.api.model.Hairpin;
import mirna.api.repo.HairpinRepository;
import mirna.lucene.model.LuceneElement;
import mirna.lucene.model.LuceneResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ar.ArabicAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Esteban on 06/06/2016.
 */
public class LuceneIndex {

	private String indexPath;

	public LuceneIndex() {
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("imirna.properties"));
			indexPath = props.getProperty("imirna.lucene.index_folder");
			if (indexPath==null || indexPath.isEmpty()) {
				indexPath = System.getProperty("java.io.tmpdir")+"/imirna-index";
				File indexFolder = new File(indexPath);
				indexFolder.mkdirs();
			}
			System.out.println("INDEX PATH = "+indexPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	@Autowired
//	private HairpinRepository repository;

	public void create() {

		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.44.23:3306/mirna", "mirna", "mirna");

			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();

//			final Tokenizer source = new StandardTokenizer(matchVersion, reader);
//			TokenStream result = new StandardFilter(matchVersion, source);
//			int flags = WordDelimiterFilter.SPLIT_ON_NUMERICS
//					| WordDelimiterFilter.GENERATE_NUMBER_PARTS
//					| WordDelimiterFilter.GENERATE_WORD_PARTS;
//			result = new WordDelimiterFilter(result, flags, null);

			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

			IndexWriter writer = new IndexWriter(dir, iwc);
			System.out.println("Indexing to directory '" + indexPath + "'...");

			indexMirna(writer, conn);
			indexPhenotype(writer, conn);
			indexEnvironmentalFactor(writer, conn);
			indexGene(writer, conn);
			indexSnp(writer, conn);
			indexBiologicalProcess(writer, conn);
			indexPubmedDocument(writer, conn);
			indexTranscript(writer, conn);
			indexProtein(writer, conn);

			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void indexMirna(IndexWriter writer, Connection conn) throws Exception {

//		PageRequest page = new PageRequest(0, 20);
//		Page<Hairpin> hairpins = repository.findAll(page);

		String sql = "select * from mirna.mirna2";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Document d = new Document();

			String pk = rs.getString("pk");
			String id = rs.getString("id");
			String accNumber = rs.getString("accession_number");
			String previousId = rs.getString("previous_id");
			String type = "MiRNA ("+rs.getString("type")+")";
			String all = id;

			d.add(new StringField("pk", pk, Field.Store.YES));
			d.add(new TextField("name", id, Field.Store.YES));

			if (accNumber != null) {
				all += " "+accNumber;
				d.add(new TextField("accession_number", accNumber, Field.Store.YES));
			}

			if (previousId != null) {
				all += " "+previousId;
				d.add(new TextField("previous_id", previousId, Field.Store.YES));
			}

			d.add(new StringField("type", type, Field.Store.YES));
			d.add(new TextField("all", all, Field.Store.YES));

			writer.addDocument(d);
		}
	}

	private void indexPhenotype(IndexWriter writer, Connection conn) throws Exception {
		String sql = "select * from mirna.disease";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Document d = new Document();

			String pk = rs.getString("pk");
			String name = rs.getString("name");
			String diseaseClass = rs.getString("disease_class");
			String all = name;

//			if (diseaseClass != null) name += "("+diseaseClass+")";
			d.add(new StringField("pk", pk, Field.Store.YES));
			d.add(new TextField("name", name, Field.Store.YES));
			if (diseaseClass != null) {
				all += " "+diseaseClass;
				d.add(new TextField("disease_class", diseaseClass, Field.Store.YES));
			}
			d.add(new StringField("type", "Phenotype", Field.Store.YES));
			d.add(new TextField("all", all, Field.Store.YES));

			writer.addDocument(d);
		}
	}

	private void indexEnvironmentalFactor(IndexWriter writer, Connection conn) throws Exception {
		String sql = "select * from mirna.environmental_factor";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Document d = new Document();

			String pk = rs.getString("pk");
			String name = rs.getString("name");
			String all = name;

			d.add(new StringField("pk", pk, Field.Store.YES));
			d.add(new TextField("name", name, Field.Store.YES));
			d.add(new StringField("type", "Environmental Factor", Field.Store.YES));
			d.add(new TextField("all", all, Field.Store.YES));

			writer.addDocument(d);
		}
	}

	private void indexGene(IndexWriter writer, Connection conn) throws Exception {
		String sql = "select * from mirna.gene";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Document d = new Document();

			String pk = rs.getString("pk");
			String name = rs.getString("name");
			String all = name;

			d.add(new StringField("pk", pk, Field.Store.YES));
			d.add(new TextField("name", name, Field.Store.YES));
			d.add(new StringField("type", "Gene", Field.Store.YES));
			d.add(new TextField("all", all, Field.Store.YES));

			writer.addDocument(d);
		}
	}

	private void indexSnp(IndexWriter writer, Connection conn) throws Exception {
		String sql = "select * from mirna.snp";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Document d = new Document();

			String pk = rs.getString("pk");
			String name = rs.getString("snp_id");
			String all = name;

			d.add(new StringField("pk", pk, Field.Store.YES));
			d.add(new TextField("name", name, Field.Store.YES));
			d.add(new StringField("type", "SNP", Field.Store.YES));
			d.add(new TextField("all", all, Field.Store.YES));

			writer.addDocument(d);
		}
	}

	private void indexBiologicalProcess(IndexWriter writer, Connection conn) throws Exception {
		String sql = "select * from mirna.biological_process";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Document d = new Document();

			String pk = rs.getString("pk");
			String name = rs.getString("name");
			String all = name;

			d.add(new StringField("pk", pk, Field.Store.YES));
			d.add(new TextField("name", name, Field.Store.YES));
			d.add(new StringField("type", "Biological Process", Field.Store.YES));
			d.add(new TextField("all", all, Field.Store.YES));

			writer.addDocument(d);
		}
	}

	private void indexPubmedDocument(IndexWriter writer, Connection conn) throws Exception {
		String sql = "select * from mirna.pubmed_document";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Document d = new Document();

			String pk = rs.getString("pk");
			String name = rs.getString("id");
			String all = name;

			d.add(new StringField("pk", pk, Field.Store.YES));
			d.add(new TextField("name", name, Field.Store.YES));
			d.add(new StringField("type", "Pubmed Document", Field.Store.YES));
			d.add(new TextField("all", all, Field.Store.YES));

			writer.addDocument(d);
		}
	}

	private void indexTranscript(IndexWriter writer, Connection conn) throws Exception {
		String sql = "select * from mirna.transcript";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {

			String pk = rs.getString("pk");
			String name = rs.getString("id");
			String all = name;

			if (name!=null) {

				Document d = new Document();

				d.add(new StringField("pk", pk, Field.Store.YES));
				d.add(new TextField("name", name, Field.Store.YES));
				d.add(new StringField("type", "Transcript", Field.Store.YES));
				d.add(new TextField("all", all, Field.Store.YES));

				writer.addDocument(d);

			}
		}
	}

	private void indexProtein(IndexWriter writer, Connection conn) throws Exception {
		String sql = "select * from mirna.protein";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Document d = new Document();

			String pk = rs.getString("pk");
			String name = rs.getString("uniprot_id");
			String all = name;

			d.add(new StringField("pk", pk, Field.Store.YES));
			d.add(new TextField("name", name, Field.Store.YES));
			d.add(new StringField("type", "Protein", Field.Store.YES));
			d.add(new TextField("all", all, Field.Store.YES));

			writer.addDocument(d);
		}
	}

	public LuceneResult search(String input) {
		return search(input, 10, 1);
	}

	public LuceneResult search(String input, int pageSize, int page) {

		LuceneResult result = null;
		page++;
//		System.out.println("Searching: "+input);
		int maxResults = page*pageSize;

		try {

			List<LuceneElement> elements = new ArrayList<>();

			Directory dir = FSDirectory.open(Paths.get(indexPath));

			if (!DirectoryReader.indexExists(dir)) create();

			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);

			Analyzer analyzer = new ArabicAnalyzer();
			QueryParser parser = new QueryParser("all", analyzer);

			Query query = parser.parse(input);

			TopDocs results = searcher.search(query, maxResults);
			ScoreDoc[] hits = results.scoreDocs;

			int numTotalHits = results.totalHits;

			for (int i=1; i<=hits.length; i++) {
				if ( (i > (pageSize*(page-1))) && (i <= page*pageSize) ) {
					Document doc = searcher.doc(hits[i-1].doc);
					int pk = Integer.valueOf(doc.get("pk"));
					elements.add(new LuceneElement(pk, doc.get("name"), doc.get("type")));
				}
			}

			result = new LuceneResult(elements, numTotalHits);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
		LuceneIndex luceneIndex = new LuceneIndex();
		luceneIndex.create();
//		luceneIndex.search("hsa");
//		luceneIndex.search("7a");
//		luceneIndex.search("hsa let 7a", 5, 3);
	}

}
