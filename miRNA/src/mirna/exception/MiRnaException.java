package mirna.exception;

public class MiRnaException extends Exception {
		
	private static final long serialVersionUID = 1L;

	public MiRnaException(String message) {
		super(message);
		this.printStackTrace();
	}

}
