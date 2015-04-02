package countrybuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import docs.SGMLWriter;

public class CountryBuilder {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter country name: ");
		String countryName = scanner.nextLine();
		System.out.print("Enter country population (1950): ");
		long population = Long.parseLong(scanner.nextLine());
		System.out.print("Enter country Base Tax in EU4: ");
		int baseTax = Integer.parseInt(scanner.nextLine());
		long populationPerBaseTax = population / baseTax;
		System.out.print("Enter country nationality name: ");
		String nationality = scanner.nextLine();
		System.out.print("Enter country religion name: ");
		String religion = scanner.nextLine();
		System.out.print("Enter country literacy % (0.0-1.0): ");
		double literacy = Double.parseDouble(scanner.nextLine());
		
		boolean running = true;
		while(running) {
			System.out.println();
			switch(scanner.nextLine()) {
			case "nationality":
				System.out.print("Add new nationality: ");
				nationality = scanner.nextLine();
				break;
			case "new":
				System.out.print("Enter province ID: ");
				int provId = Integer.parseInt(scanner.nextLine());
				System.out.print("Enter province name: ");
				String provName = scanner.nextLine();
				System.out.print("Enter province city name: ");
				String provCityName = scanner.nextLine();
				System.out.print("Enter province base tax: ");
				int provBaseTax = Integer.parseInt(scanner.nextLine());
				long provPopulation = provBaseTax * populationPerBaseTax;
				System.out.print("Enter province resource: ");
				String provResource = scanner.nextLine();
				
				int provDir = provId / 100;
				File provDirObj = new File("res/history/provinces/gen/" + provDir + "/");
				if(!provDirObj.exists()) provDirObj.mkdirs();
				File provFile = new File("res/history/provinces/gen/" + provDir + "/" + provId + ".xml");
				if(!provFile.exists()) {
					provFile.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(provFile);
				SGMLWriter writer = new SGMLWriter(fos);
				
				writer.dataElement("name", provName);
				writer.dataElement("owner", countryName);
				writer.dataElement("city", provCityName);
				writer.dataElement("resource", provResource);
				
				writer.elementStart("populations");
				writer.elementStart("population");
				writer.dataElement("nationality", nationality);
				writer.dataElement("religion", religion);
				writer.dataElement("size", provPopulation + "");
				writer.dataElement("type", "Farmer");
				writer.dataElement("literacy", literacy + "");
				
				writer.elementEnd("population");
				writer.elementEnd("populations");
				
				writer.close();
				
				System.out.println("Province " + provName + " complete.");
				break;
			case "exit":
				running = false;
				break;
			}
		}
	}
}
