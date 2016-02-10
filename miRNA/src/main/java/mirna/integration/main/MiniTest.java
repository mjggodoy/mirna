package mirna.integration.main;

import java.util.StringTokenizer;

public class MiniTest {
	
	public static void main(String[] args) {
		
//		List<Integer> miniLista = new ArrayList<Integer>(2);
//		miniLista.add(34);
//		miniLista.add(23);
//		System.out.println(miniLista);
//		miniLista.add(13);
//		System.out.println(miniLista);
//		
//		List<Integer>[][] foo = new ArrayList[4][4];
//		foo[1][1]=new ArrayList<Integer>();
//		foo[1][1].add(1);
//		System.out.println(foo[1][1]);
//		
//		
//		System.out.println("===");
//		List<List<Integer>> lista1 = new ArrayList<List<Integer>>();
//		lista1.add(new ArrayList<Integer>());
//		lista1.get(0).add(34);
//		List<List<Integer>> lista2 = new ArrayList<List<Integer>>();
//		lista2.add(new ArrayList<Integer>(lista1.get(0)));
//		lista2.get(0).add(69);
//		System.out.println(lista1);
//		System.out.println(lista2);
		
		String line = "Me llamo PUA\ty soy  muy bonito";
//		for (int i=0; line.charAt(i)!='\0' && line.charAt(i)!=')'; i++) {
//			char p = line.charAt(i);
//			System.out.println("PUA: " + p);
//		}
		StringTokenizer st = new StringTokenizer(line);
		while (st.hasMoreTokens()) {
			System.out.println(st.nextToken());
		}
		
		
		
	}

}
