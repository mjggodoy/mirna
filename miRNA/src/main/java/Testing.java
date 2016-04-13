
public class Testing {

	public static void main(String[] args) {

		
		String method = "Quantitative real-time pcr";
		if(method.contains("pcr")){
			
			method = method.replace("pcr", "pcr".toUpperCase());
			System.out.println(method);
			
		}

		
		
	}

}
