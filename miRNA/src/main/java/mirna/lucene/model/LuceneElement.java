package mirna.lucene.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Esteban on 06/06/2016.
 */
public class LuceneElement {

	private String name;
	private String type;

	public LuceneElement() {}

	public LuceneElement(String name, String type) {
		this.name = name;
		this.type = type;
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
