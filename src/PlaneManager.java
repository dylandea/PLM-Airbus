import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class PlaneManager {

	public static void main(String[] args) {
		
		//----------DATA----------:
		HashMap<String, String[]> planeList = new HashMap<>();
		HashMap<String, ArrayList<String[]>> partsList = new HashMap<>();
		
		String[] plane1 = {"A320", "conception", "fret"};
		planeList.put("H44/21", plane1);
		ArrayList<String[]> plane1Parts = new ArrayList<>();
		plane1Parts.add(new String[] { "Vis fuselage M17/489, 1000x", "Visserie", "264.55€" });
		plane1Parts.add(new String[] { "Cable d.10 rouge, 500m", "Electricité", "4100.98€" });
		partsList.put("H44/21", plane1Parts);
		
		String[] plane2 = {"A400M", "définition", "militaire"};
		planeList.put("Z57/18", plane2);
		
		String[] plane3 = {"A380", "en service", "transport passager"};
		planeList.put("J97/19", plane3);
		ArrayList<String[]> plane3Parts = new ArrayList<>();
		plane3Parts.add(new String[] { "Siège Y00", "Equipement", "214.87€" });
		plane3Parts.add(new String[] { "Compartiment bagage LR74, blanc", "Equipement", "143.10" });
		plane3Parts.add(new String[] { "Aileron 874MZ", "Composant", "15200.55€" });
		partsList.put("J97/19", plane3Parts);
		
		String[] plane4 = {"A300", "étude de faisabilité", "avion d'affaire"};
		planeList.put("M34/22", plane4);
		
		String[][] availableParts = {
				{ "Vis auto-percante M12/41", "Visserie", "12.39€" },
				{ "Vis fuselage M17/489", "Visserie", "11.55€" },
				{ "Siège X65", "Equipement", "122.87€" }
		};
		//---------END DATA----------
		
				
		System.out.println("Bienvenue dans l'application de gestion du cycle de vie d'avions AIRBUS.");
		Scanner scan = new Scanner(System.in);
		String input = "-1";
		while (!input.equals("5")) {		
			System.out.println("Faites votre choix dans le menu, saisissez le chiffre correspondant :");		
			System.out.println("1 : Afficher tous les avions");
			System.out.println("2 : Afficher tous les avions contenant un mot clé dans le programme");
			System.out.println("3 : Ajouter ou supprimer une pièce pour un avion donné");
			System.out.println("4 : Afficher un avion avec les infos détaillées de chaque pièces");
			System.out.println("5 : Quitter l'application");	
			input = scan.next();		
			while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5")) {
				System.out.println("Votre saisie ne correspond à aucun des choix disponibles, veuillez réessayer:");
				input = scan.next();
			}
			scan.nextLine();
			System.out.println(" ");
			
			switch (input) {
			case "1":
				System.out.print("Liste des avions:");
				System.out.println(displayPlaneList(planeList, partsList));
				break;
			case "2":
				String searchResult = displayPlaneList(searchInPlaneList(planeList, scan), partsList);
				System.out.println(searchResult.equals("") 
						? "Aucun programme ne correspond à votre recherche." 
								: "Voici les avions dont les programmes correspondent à votre recherche :\n" + searchResult);
				break;
			case "3":
				addParts(partsList, planeList, scan, availableParts);
				break;
			case "4":
				System.out.println(displayDetailed(planeList, partsList, scan));
				break;
			default:
				break;
			}
			System.out.println(" ");
			System.out.println("Retour au menu principal...");
			System.out.println(" ");
			
		}
		
		System.out.println("Vous quittez l'application de gestion.");
		scan.close();
		System.exit(0);
	}
	
	public static String displayPlaneList(HashMap<String, String[]>  planeList, HashMap<String, ArrayList<String[]>> partsList) {
		String formattedPlaneList = "";
		for (String key : planeList.keySet()) {
			formattedPlaneList += ("\n" + key + "=" + Arrays.toString(planeList.get(key)) + "\nPièces commandées:");
			if (partsList.get(key) != null) {
				if (partsList.get(key).size() != 0)
					for (int i=0; i<partsList.get(key).size(); i++)
						formattedPlaneList += "\n- " + partsList.get(key).get(i)[0];
				else formattedPlaneList += "\nAucune";
			}
			else formattedPlaneList += "\nAucune";
			formattedPlaneList += "\n------------------";
		}
		return formattedPlaneList;
	}
	
	public static String displayDetailed(HashMap<String, String[]>  planeList, HashMap<String, ArrayList<String[]>> partsList, Scanner scan) {
		String formattedPlaneDetail = "";
		
		System.out.println("Pour obtenir le détail d'un avion:");
		System.out.println("1 - Choisir dans la liste des avions disponibles");
		System.out.println("2 - Rechercher un avion par son programme");
		System.out.println("3 - Retour au menu principal");
		String planeInput = scan.next();
		
			while (!planeInput.equals("1") && !planeInput.equals("2") && !planeInput.equals("3")) {
				System.out.println("Votre saisie ne correspond à aucun des choix disponibles, veuillez réessayer:");
				planeInput = scan.next();
			}
			scan.nextLine();
			System.out.println(" ");
			switch (planeInput) {
			case "1":
				System.out.println("Avions disponibles :");
				for (String key : planeList.keySet()) 
					System.out.println(key + "=" + Arrays.toString(planeList.get(key))); 
				break;
			case "2":
				String searchResultID = "";
				while (searchResultID.equals("")) {
					searchResultID = displayPlaneList(searchInPlaneList(planeList, scan), partsList);
					System.out.println(searchResultID.equals("") 
							? "Aucun programme ne correspond à votre recherche." 
									: "Voici les avions dont les programmes correspondent à votre recherche :\n" + searchResultID);
				}
				break;
			case "3":
				break;
			default:
				break;
			}
		
		System.out.println(" ");
		System.out.println("Saisir tout ou partie de l'ID de l'avion concerné par la commande de pièces :");
		
		String choosenPlaneID = scan.nextLine();
		
		String concatKeys = "";
		
		for (String key : planeList.keySet()) {
			concatKeys += key;
		}
		
		while (!concatKeys.toLowerCase().contains(choosenPlaneID.toLowerCase())) {
			System.out.println("Aucun avion ne contient \"" + choosenPlaneID + "\" dans son ID, veuillez entrer un ID correct:");
			choosenPlaneID = scan.nextLine();
		}
		
		for (String key : planeList.keySet()) {
			if (key.toLowerCase().contains(choosenPlaneID.toLowerCase())) 
				choosenPlaneID = key;
		}
	// et gérer le cas où plusieurs avions correspondent		
		System.out.println(" ");
		System.out.println("Avion choisi : ");
		System.out.println(choosenPlaneID + "=" + Arrays.toString(planeList.get(choosenPlaneID)));
		System.out.print("Pièces commandées : ");
		
		
		if (partsList.containsKey(choosenPlaneID))
			if (partsList.get(choosenPlaneID).size()==0) 
				formattedPlaneDetail += "\nAucune";	
			else
				for (String[] strArr : partsList.get(choosenPlaneID))
					formattedPlaneDetail += "\n-"+ Arrays.toString(strArr) ;
		else formattedPlaneDetail += "\nAucune";
		return formattedPlaneDetail;
	}
	
	public static HashMap<String, String[]> searchInPlaneList(HashMap<String, String[]> planeList, Scanner scan) {
		HashMap<String, String[]> resultSearch = new HashMap<>();
		System.out.println("Saisissez le programme à rechercher :");
		String input = scan.nextLine();
		for (String key : planeList.keySet()) 
			if (planeList.get(key)[0].toLowerCase().contains(input.toLowerCase())) 
				resultSearch.put(key, planeList.get(key));
		return resultSearch;
	}
// changer scan ID par des chiffres
	public static void addParts(HashMap<String, ArrayList<String[]>> partsList, HashMap<String, String[]> planeList, Scanner scan, String[][] availableParts) {
		System.out.println("Bienvenue dans le service de commande de pièces.");
		System.out.println("Séléction de l'avion concerné:");
		System.out.println("1 - Choisir dans la liste des avions disponibles");
		System.out.println("2 - Rechercher un avion par son programme");
		System.out.println("3 - Quitter le service de commande de pièces");
		String planeInput = scan.next();
		
			while (!planeInput.equals("1") && !planeInput.equals("2") && !planeInput.equals("3")) {
				System.out.println("Votre saisie ne correspond à aucun des choix disponibles, veuillez réessayer:");
				planeInput = scan.next();
			}
			scan.nextLine();
			System.out.println(" ");
			switch (planeInput) {
			case "1":
				System.out.println("Avions disponibles :");
				for (String key : planeList.keySet()) 
					System.out.println(key + "=" + Arrays.toString(planeList.get(key))); 
				break;
			case "2":
				String searchResultID = "";
				while (searchResultID.equals("")) {
					searchResultID = displayPlaneList(searchInPlaneList(planeList, scan), partsList);
					System.out.println(searchResultID.equals("") 
							? "Aucun programme ne correspond à votre recherche." 
									: "Voici les avions dont les programmes correspondent à votre recherche :\n" + searchResultID);
				}
				break;
			case "3":
				break;
			default:
				break;
			}
		
		System.out.println(" ");
		System.out.println("Saisir tout ou partie de l'ID de l'avion concerné par la commande de pièces :");
		
		String choosenPlaneID = scan.nextLine();
		
		String concatKeys = "";
		
		for (String key : planeList.keySet()) {
			concatKeys += key;
		}
		
		while (!concatKeys.toLowerCase().contains(choosenPlaneID.toLowerCase())) {
			System.out.println("Aucun avion ne contient \"" + choosenPlaneID + "\" dans son ID, veuillez entrer un ID correct:");
			choosenPlaneID = scan.nextLine();
		}
		
		for (String key : planeList.keySet()) {
			if (key.toLowerCase().contains(choosenPlaneID.toLowerCase())) 
				choosenPlaneID = key;
		}
	// et gérer le cas où plusieurs avions correspondent		
		System.out.println(" ");
		System.out.println("Avion choisi : ");
		System.out.println(choosenPlaneID + "=" + Arrays.toString(planeList.get(choosenPlaneID)));
		System.out.println("Pièces déjà commandées : ");
		
		int numOfOptions = 3;
		if (partsList.containsKey(choosenPlaneID))
			if (partsList.get(choosenPlaneID).size()==0) {
				System.out.println("Aucune");
				numOfOptions = 2;
			}
				
			else
				for (String[] strArr : partsList.get(choosenPlaneID))
					System.out.println("- "+strArr[0]);
		else {
			System.out.println("Aucune");
			numOfOptions = 2;
		}
		
		System.out.println(" ");
		
			
			if (numOfOptions==3) {
				System.out.println("Souhaitez-vous ajouter ou supprimer une pièce à cet avion ?");
				System.out.println("1 - Ajouter");
				System.out.println("2 - Supprimer");
				System.out.println("3 - Retour au menu principal");
			} else {
				System.out.println("Aucune pièce commandée pour cet avion. Souhaitez-vous ajouter une pièce ?");
				System.out.println("1 - Ajouter");
				System.out.println("2 - Retour au menu principal");
			}
			
			String addOrDeleteInput2 = scan.next();
			while (!addOrDeleteInput2.equals(numOfOptions + "")) {
				if (numOfOptions==3) {
					while (!addOrDeleteInput2.equals("1") && !addOrDeleteInput2.equals("2") && !addOrDeleteInput2.equals("3")) {
						System.out.println("Votre saisie ne correspond à aucun des choix disponibles");
						addOrDeleteInput2 = scan.next();
					}
				} else {
					while (!addOrDeleteInput2.equals("1") && !addOrDeleteInput2.equals("2")) {
						System.out.println("Votre saisie ne correspond à aucun des choix disponibles");
						addOrDeleteInput2 = scan.next();
					}
				}
				
				scan.nextLine();
				System.out.println(" ");
				
				if (numOfOptions==3) {
				switch (addOrDeleteInput2) {
				case "1":
					System.out.println("Choisissez une pièce parmi la liste des pièces disponibles à l'achat:");
					
					for (int i=0; i<availableParts.length; i++) 
						System.out.println(i+1 + " - " + Arrays.toString(availableParts[i]));
					
					int orderedPartInput = scan.nextInt();
	//gérer les lettres				
					while (orderedPartInput < 1 || orderedPartInput > availableParts.length) {
						System.out.println("Votre saisie ne correspond à aucun des choix disponibles, veuillez réessayer:");
						orderedPartInput = scan.nextInt();
					}
					scan.nextLine();
					if (partsList.containsKey(choosenPlaneID))
					    partsList.get(choosenPlaneID).add(availableParts[orderedPartInput-1]);
					else {
						partsList.put(choosenPlaneID, new ArrayList<>());
						partsList.get(choosenPlaneID).add(availableParts[orderedPartInput-1]);
					}
					System.out.println(" ");
					System.out.println("Pièce ajoutée avec succès !");
					System.out.println("Souhaitez-vous ajouter ou supprimer une pièce à cet avion ?");
					System.out.println("1 - Ajouter");
					System.out.println("2 - Supprimer");
					System.out.println("3 - Retour au menu principal");
					addOrDeleteInput2 = scan.next();
					break;
				case "2":
					System.out.println("Liste des pièces en cours de commande pour cet avion:");
					
					for (int i=0; i<partsList.get(choosenPlaneID).size(); i++) 
						System.out.println(i+1 + " - " + Arrays.toString(partsList.get(choosenPlaneID).get(i)));
					
					System.out.println("Saisir le chiffre correspondant à la pièce à supprimer:");
					int orderPartInput = scan.nextInt();
	//gérer les lettres				
					while (orderPartInput < 1 || orderPartInput > partsList.get(choosenPlaneID).size()) {
						System.out.println("Votre saisie ne correspond à aucun des choix disponibles, veuillez réessayer:");
						orderPartInput = scan.nextInt();
					}
					scan.nextLine();
					
						
					partsList.get(choosenPlaneID).remove(orderPartInput-1);
					
					System.out.println(" ");
					System.out.println("Pièce supprimée avec succès !");
					
					if (partsList.get(choosenPlaneID).size()==0) {
						numOfOptions=2;
						System.out.println("Aucune pièce commandée pour cet avion. Souhaitez-vous ajouter une pièce ?");
						System.out.println("1 - Ajouter");
						System.out.println("2 - Retour au menu principal");
					} 
					else {
						System.out.println("Souhaitez-vous ajouter ou supprimer une pièce ?");
						System.out.println("1 - Ajouter");
						System.out.println("2 - Supprimer");
						System.out.println("3 - Retour au menu principal");
					}
					
					addOrDeleteInput2 = scan.next();
					break;
				default:
					break;
				}} else {
					switch (addOrDeleteInput2) {
					case "1":
						System.out.println("Choisissez une pièce parmi la liste des pièces disponibles à l'achat:");
						
						for (int i=0; i<availableParts.length; i++) 
							System.out.println(i+1 + " - " + Arrays.toString(availableParts[i]));
						
						int orderedPartInput = scan.nextInt();
		//gérer les lettres				
						while (orderedPartInput < 1 || orderedPartInput > availableParts.length) {
							System.out.println("Votre saisie ne correspond à aucun des choix disponibles, veuillez réessayer:");
							orderedPartInput = scan.nextInt();
						}
						scan.nextLine();
						if (partsList.containsKey(choosenPlaneID))
						    partsList.get(choosenPlaneID).add(availableParts[orderedPartInput-1]);
						else {
							partsList.put(choosenPlaneID, new ArrayList<>());
							partsList.get(choosenPlaneID).add(availableParts[orderedPartInput-1]);
						}
						System.out.println(" ");
						System.out.println("Pièce ajoutée avec succès !");
						numOfOptions=3;
						System.out.println("Souhaitez-vous ajouter ou supprimer une pièce à cet avion ?");
						System.out.println("1 - Ajouter");
						System.out.println("2 - Supprimer");
						System.out.println("3 - Retour au menu principal");
						addOrDeleteInput2 = scan.next();
						break;
					
					default:
						break;
					}
				}
				
			}
			return;	
	}
	
}
