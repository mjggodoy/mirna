package mirna.integration.exception;

public class ConflictException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ConflictException(Object o1, Object o2) {
		super("Conflicto detectado:\n"+o1+"\n"+o2);
		this.printStackTrace();
	}
	
}
