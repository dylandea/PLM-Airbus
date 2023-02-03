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
		planeList.put("H442", plane1);
		ArrayList<String[]> plane1Parts = new ArrayList<>();
		plane1Parts.add(new String[] { "Vis fuselage M17/489, 1000x", "Visserie", "264.55€" });
		plane1Parts.add(new String[] { "Cable d.10 rouge, 500m", "Electricité", "4100.98€" });
		partsList.put("H442", plane1Parts);

		String[] plane2 = {"A400M", "définition", "militaire"};
		planeList.put("Z571", plane2);

		String[] plane3 = {"A380", "en service", "transport passager"};
		planeList.put("J979", plane3);
		ArrayList<String[]> plane3Parts = new ArrayList<>();
		plane3Parts.add(new String[] { "Siège Y00", "Equipement", "214.87€" });
		plane3Parts.add(new String[] { "Compartiment bagage LR74, blanc", "Equipement", "143.10" });
		plane3Parts.add(new String[] { "Moteur aileron 874MZ", "Composant", "15200.55€" });
		partsList.put("J979", plane3Parts);

		String[] plane4 = {"A300", "étude de faisabilité", "avion d'affaire"};
		planeList.put("M346", plane4);

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
						? "\nAucun programme ne correspond à votre recherche." 
								: "\nVoici les avions dont le programme correspond à votre recherche :" + searchResult);
				break;
			case "3":
				orderParts(partsList, planeList, scan, availableParts);
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
		if (planeList.size() == 0) {
			return "";
		} else {
		String formattedPlaneList = "";
		formattedPlaneList += "\n------------------------------------";
		for (String key : planeList.keySet()) {
			formattedPlaneList += ("\n" + key + " = " + Arrays.toString(planeList.get(key)) + "\nPièces commandées:");
			if (partsList.get(key) != null) {
				if (partsList.get(key).size() != 0)
					for (int i=0; i<partsList.get(key).size(); i++)
						formattedPlaneList += "\n- " + partsList.get(key).get(i)[0];
				else formattedPlaneList += "\nAucune";
			}
			else formattedPlaneList += "\nAucune";
			formattedPlaneList += "\n------------------------------------";
		}
		return formattedPlaneList;
		}
	}

	public static String displayDetailed(HashMap<String, String[]>  planeList, HashMap<String, ArrayList<String[]>> partsList, Scanner scan) {
		String formattedPlaneDetail = "";

		System.out.println("Pour obtenir le détail d'un avion, saisissez le chiffre correspondant:");

		String choosenPlaneID = planeSelection(scan, planeList, partsList);

		System.out.println(" ");
		System.out.println("Infos détaillées de l'avion " + choosenPlaneID +" : ");
		System.out.println(choosenPlaneID + " = " + Arrays.toString(planeList.get(choosenPlaneID)));
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
		for (String key : planeList.keySet()) {
			String[] originalPlaneData = planeList.get(key);
			if (originalPlaneData[0].toLowerCase().contains(input.toLowerCase())) {	
				//mise en couleur
				String redTextColor = "\u001B[31m";
				String resetTextColor = "\u001B[0m";
				String program = originalPlaneData[0];
				int index = program.toLowerCase().indexOf(input.toLowerCase());
				String coloredResult = program.substring(0, index) 
						+ redTextColor + program.substring(index, index+input.length()) 
						+ resetTextColor 
						+ program.substring(index+input.length(), program.length());
				String[] planeDataCopy = originalPlaneData.clone();
				planeDataCopy[0] = coloredResult;
				//stockage dans un hashmap des avions correspondant
				resultSearch.put(key, planeDataCopy);
			}
		}

		return resultSearch;
	}
	// changer scan ID par des chiffres ?
	public static void orderParts(HashMap<String, ArrayList<String[]>> partsList, HashMap<String, String[]> planeList, Scanner scan, String[][] availableParts) {
		System.out.println("Bienvenue dans le service de commande de pièces.");
		System.out.println("Séléction de l'avion concerné par la commande:");
	
		String choosenPlaneID = planeSelection(scan, planeList, partsList);
				
		System.out.println(" ");
		System.out.println("Avion choisi : ");
		System.out.println(choosenPlaneID + " = " + Arrays.toString(planeList.get(choosenPlaneID)));
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


			switch (addOrDeleteInput2) {
			case "1":
				System.out.println("Choisissez une pièce parmi la liste des pièces disponibles à l'achat:");

				for (int i=0; i<availableParts.length; i++) 
					System.out.println(i+1 + " - " + Arrays.toString(availableParts[i]));
				System.out.println(availableParts.length+1 + " - Annuler");

				int choosenPartInput = handleUnexpectedInputForIntegerInARange(1,availableParts.length+1,scan);

				if (choosenPartInput==availableParts.length+1) {
					System.out.println(" ");
					System.out.println("Commande annulée.");
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

				} else {
					if (partsList.containsKey(choosenPlaneID))
						partsList.get(choosenPlaneID).add(availableParts[choosenPartInput-1]);
					else {
						partsList.put(choosenPlaneID, new ArrayList<>());
						partsList.get(choosenPlaneID).add(availableParts[choosenPartInput-1]);
					}
					System.out.println(" ");
					System.out.println("Pièce ajoutée avec succès !");
					numOfOptions=3;
					System.out.println("Souhaitez-vous ajouter ou supprimer une pièce à cet avion ?");
					System.out.println("1 - Ajouter");
					System.out.println("2 - Supprimer");
					System.out.println("3 - Retour au menu principal");
				}

				addOrDeleteInput2 = scan.next();
				break;
			case "2":
				System.out.println("Liste des pièces en cours de commande pour cet avion:");

				for (int i=0; i<partsList.get(choosenPlaneID).size(); i++) 
					System.out.println(i+1 + " - " + Arrays.toString(partsList.get(choosenPlaneID).get(i)));
				System.out.println((partsList.get(choosenPlaneID).size()+1) + " - Annuler");
				System.out.println("Saisir le chiffre correspondant à la pièce à supprimer:");
				
				
				int orderPartInput = handleUnexpectedInputForIntegerInARange(1, partsList.get(choosenPlaneID).size()+1, scan);
		
				if(orderPartInput==partsList.get(choosenPlaneID).size()+1) {
					System.out.println(" ");
					System.out.println("Suppression annulée.");
				} else {
					partsList.get(choosenPlaneID).remove(orderPartInput-1);

					System.out.println(" ");
					System.out.println("Pièce supprimée avec succès !");
				}
				
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
			} 

		}
		return;	
	}

	public static String planeSelection(Scanner scan, HashMap<String, String[]> planeList, HashMap<String, ArrayList<String[]>> partsList) {
		System.out.println("1 - Choisir dans la liste des avions disponibles");
		System.out.println("2 - Rechercher un avion par son programme");
		System.out.println("3 - Retour au menu principal");
		
		int planeInput = handleUnexpectedInputForIntegerInARange(1,3,scan);

		System.out.println(" ");
		switch (planeInput) {
		case 1:
			System.out.println("Avions disponibles :");
			for (String key : planeList.keySet()) 
				System.out.println(key + " = " + Arrays.toString(planeList.get(key))); 
			break;
		case 2:
			String searchResultID = "";
			while (searchResultID.equals("")) {
				searchResultID = displayPlaneList(searchInPlaneList(planeList, scan), partsList);
				System.out.println(searchResultID.equals("") 
						? "\nAucun programme ne correspond à votre recherche." 
								: "\nVoici les avions dont le programme correspond à votre recherche :" + searchResultID);
			}
			break;
		case 3:
			break;
		default:
			break;
		}

		System.out.println(" ");
		System.out.println("Saisir tout ou partie de l'ID de l'avion concerné par la commande de pièces :");

		String choosenPlaneID = scan.nextLine();

		String concatKeys = "";

		for (String key : planeList.keySet()) {
			concatKeys += key+" ";
		}

		while (!concatKeys.toLowerCase().contains(choosenPlaneID.toLowerCase())) {
			System.out.println("Aucun avion ne contient \"" + choosenPlaneID + "\" dans son ID, veuillez entrer un ID correct:");
			choosenPlaneID = scan.nextLine();
		}

		for (String key : planeList.keySet()) {
			if (key.toLowerCase().contains(choosenPlaneID.toLowerCase())) 
				choosenPlaneID = key;
		}
		//la boucle précédente renvoie la première clé qui correspond, mais ne gère pas le cas où plusieurs avions correspondent.
		//ce sera à améliorer une prochaine fois...
		return choosenPlaneID;
	}
	
	public static int handleUnexpectedInputForIntegerInARange(int rangeMin, int rangeMax, Scanner scan) {
		//Gestion saisies inattendues quand on attend un CHIFFRE ENTIER dans une plage précise:				
		//une première gestion pour les lettres:
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.println("Votre saisie ne correspond à aucun des choix disponibles, veuillez réessayer:");
		}
		//initialisation d'une variable au résultat du scan
		int input = scan.nextInt();	
		//puis gestion des chiffres saisies s'ils sont en dehors de la plage
		while (input < rangeMin || input > rangeMax) {
			System.out.println("Votre saisie ne correspond à aucun des choix disponibles, veuillez réessayer:");
			//puis re-gestion des Lettres à l'intérieur de la gestion des Chiffres:
			while (!scan.hasNextInt()) {
				scan.next();
				System.out.println("Votre saisie ne correspond à aucun des choix disponibles, veuillez réessayer:");
			}
			input = scan.nextInt();
		}
		scan.nextLine();
		return input;
	}
}
