package mirna.lucene.model;

import java.util.List;

/**
 * Created by Esteban on 08/06/2016.
 */
public class LuceneResult {

	private List<LuceneElement> elements;
	private int totalResults;

	public LuceneResult(List<LuceneElement> elements, int totalResults) {
		this.totalResults = totalResults;
		this.elements = elements;
	}

	public List<LuceneElement> getElements() {
		return elements;
	}

	public int getTotalResults() {
		return totalResults;
	}

}
