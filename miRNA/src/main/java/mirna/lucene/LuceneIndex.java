package mirna.lucene;

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

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esteban on 06/06/2016.
 */
public class LuceneIndex {

	private final String indexPath;

	public LuceneIndex() {
		indexPath = "C:/temp/mirna";
	}

	public void execute() {

		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.44.23:3306/mirna", "mirna", "mirna");

			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

			IndexWriter writer = new IndexWriter(dir, iwc);
			System.out.println("Indexing to directory '" + indexPath + "'...");

			indexMirna(writer, conn);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void indexMirna(IndexWriter writer, Connection conn) throws Exception {
		String sql = "select pk, id from mirna.mirna2";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Document d = new Document();
			d.add(new StringField("pk", rs.getString("pk"), Field.Store.YES));
			d.add(new TextField("name", rs.getString("id"), Field.Store.YES));
			d.add(new StringField("type", "mirna", Field.Store.YES));
			writer.addDocument(d);
		}
	}

	public LuceneResult search(String input) {
		return search(input, 10, 1);
	}

	public LuceneResult search(String input, int pageSize, int page) {

		int maxResults = 100;
		LuceneResult result = null;

		System.out.println("Searching: "+input);

		try {

			List<LuceneElement> elements = new ArrayList<>();

			Directory dir = FSDirectory.open(Paths.get(indexPath));

			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);

			Analyzer analyzer = new ArabicAnalyzer();
			QueryParser parser = new QueryParser("name", analyzer);

			Query query = parser.parse(input);

			TopDocs results = searcher.search(query, maxResults);
			ScoreDoc[] hits = results.scoreDocs;

			int numTotalHits = results.totalHits;
			System.out.println(numTotalHits + " total matching documents");

			for (int i=1; i<=hits.length; i++) {

				if ( (i > (pageSize*(page-1))) && (i <= page*pageSize) ) {
					Document doc = searcher.doc(hits[i-1].doc);
					System.out.println(i+": "+doc.get("name") + " : " + hits[i-1].score);
					elements.add(new LuceneElement(doc.get("name"), doc.get("type")));
				}
			}

			result = new LuceneResult(elements, hits.length);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
		LuceneIndex luceneIndex = new LuceneIndex();
		//luceneIndex.execute();
		//luceneIndex.search("hsa");
		//luceneIndex.search("7a");
		luceneIndex.search("hsa let 7a", 5, 3);
	}

}
