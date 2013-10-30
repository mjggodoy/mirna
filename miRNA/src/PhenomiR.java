import java.io.*;
import java.util.StringTokenizer;

import beans.PhenomirBean;

public class PhenomiR {

	public static void main(String args[]) throws FileNotFoundException {

		FileReader fr = new FileReader("/Users/mariajesus/Desktop/NewSearchingLine/phenomir1.0.txt");
		BufferedReader br = new BufferedReader(fr);

		PhenomirBean phenomir = new PhenomirBean();

		//			 phenomir.setPhenomicid(sc.next());
		//			String pua = phenomir.getPhenomicid();

		String line;
		int count;
		try {
			
			boolean linea1 = true;

			while((line=br.readLine())!=null) {
				
				StringTokenizer st = new StringTokenizer(line, "\t");
				
				if (linea1) {
					line=br.readLine();
					System.out.println(line);
					System.out.println(st.countTokens());
					linea1 = false;
				}

				

				if (st.countTokens() == 10) {
					String field1=st.nextToken();
//					System.out.println(field1);
					String field2=st.nextToken();
//					System.out.println(field2);
					String field3=st.nextToken();
//					System.out.println(field3);
					String field4=st.nextToken();
//					System.out.println(field4);
					String field5=st.nextToken();
//					System.out.println(field5);
					String field6=st.nextToken();
//					System.out.println(field6);
					String field7=st.nextToken();
//					System.out.println(field7);
					String field8=st.nextToken();
//					System.out.println(field8);
					String field9=st.nextToken();
//					System.out.println(field9);
					String field10=st.nextToken();
//					System.out.println(field10);
				} else {
//					System.out.println(line);
				}

				
			}

		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
	