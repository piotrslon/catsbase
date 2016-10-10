package com.testcatname.catbase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.testcatname.catbase.DAO.CatDAO;
import com.testcatname.catsbase.model.Cat;

public class Interface  {

	static Scanner sc = new Scanner(System.in); 
	
	public static void main(String[] args) {
		
		Cat cat = new Cat();
		//Alpha cAlpha = new Alpha();  
		CatDAO catDAO = new CatDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Pattern dataRexgex = Pattern.compile("[0-3]?[0-9].[0-1]?[0-9].([0-9]){4}");
		String bornDateSc;
		String catNameSc;
		Integer catsIndex = 0;
		boolean lit = true;
		String choice = "";
		
		do {
			//menu
			System.out.println(
					"------------------------------\n- press 1, to add cat\n- press 2, to show cats\n- press x, to close program\n------------------------------");
			choice = getUserInput();
			do {
				if (choice.equalsIgnoreCase("1")) {
					//add cat
					do {
						System.out.println("Give cats name: ");
						catNameSc = getUserInput();
						lit = Pattern.matches("[a-zA-Z]+[0-9]*", catNameSc);
						if (lit)
							cat.setName(catNameSc);
					} while (!lit);

					do {
						System.out.println("Give cat born date in format 'dd.MM.yyyy'");
						bornDateSc = getUserInput();
						if (dataRexgex.matcher(bornDateSc).matches()) {
							try {
								cat.setBirthDate(sdf.parse(bornDateSc));
							} catch (ParseException pe) {
								System.out.println("Smf wrong with dataformat! example data: '01.01.2000'");
							}
						}
					} while (cat.getBirthDate() == null);

					do {
						try {
							System.out.println("Give cat weight: eg. '4.56'");
							cat.setWeight(Float.valueOf(getUserInput()));
						} catch (NumberFormatException nfe) {
							//nfe.printStackTrace();
							System.out.println("Smf wrong with weightformat '4.56'");
						}
					} while (cat.getWeight() == null);

					System.out.println("Give cat guardians name");
					cat.setGuardianName(getUserInput());
					
					catDAO.addCat(cat);

				} else if (choice.equalsIgnoreCase("2")) {
					//list cat
					List<Cat> cats = catDAO.getCats();
					if (cats.size() == 0) {
						System.out.println("Cats base is empty");
					} else {
						System.out.println("Choose your cat: ");
						for (int i=0;i<cats.size();i++){
							System.out.println(i+1+" - "+cats.get(i).getName());
						}
						
						do {
							try {
								catsIndex = Integer.valueOf(getUserInput())-1;
								//show cat
								System.out.println(cats.get(catsIndex).tellYourName());
								continue;
							} catch (NumberFormatException nfe) {
								System.out.println("Only numbers");
							} catch (IndexOutOfBoundsException iob) {
								System.out.println("Try again");
							} 
						} while (catsIndex != 0);
					}
				} else if (choice.equalsIgnoreCase("x")) {
					break;
				} else {
					System.out.println("u must choose one option: '1, 2 or x'");
				}
			} while (choice == null);
		} while (!choice.equalsIgnoreCase("x"));
		
	}

	public static String getUserInput() {
		return sc.nextLine().trim();
	}
	
}
