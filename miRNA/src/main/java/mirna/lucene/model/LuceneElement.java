package mirna.lucene.model;

/**
 * Created by Esteban on 06/06/2016.
 */
public class LuceneElement {

	private int pk;
	private String name;
	private String type;

	public LuceneElement() {}

	public LuceneElement(int pk, String name, String type) {
		this.pk = pk;
		this.name = name;
		this.type = type;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
