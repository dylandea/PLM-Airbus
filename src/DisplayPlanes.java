import java.util.Arrays;
import java.util.HashMap;


public class DisplayPlanes {
	public static void main(String[] args) {
	//	HashMap<String[], ArrayList<String[]>> listPlanes = new HashMap<>();
		
		HashMap<String, String[]> listPlanes = new HashMap<>();
		
		String[] plane1 = {"A320", "conception", "fret"};
		listPlanes.put("H44/21", plane1);
		
		String[] plane2 = {"A400M", "définition", "militaire"};
		listPlanes.put("Z57/18", plane2);
		
		String[] plane3 = {"A380", "en service", "transport passager"};
		listPlanes.put("J97/19", plane3);
		
		String[] plane4 = {"A300", "étude de faisabilité", "avion d'affaire"};
		listPlanes.put("M34/22", plane4);
				
		for (String key : listPlanes.keySet()) 
			System.out.println(key+"="+Arrays.toString(listPlanes.get(key)));
		
	}
}
