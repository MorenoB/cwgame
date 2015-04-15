package countrybuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import docs.SGMLWriter;

public class CountryInfoBuilder {
	public static Scanner scanner;
	public static SGMLWriter writer;
	
	public static void enter(String name) {
		System.out.print("Enter the " + name + ": ");
		String input = scanner.nextLine();
		writer.dataElement(name, input);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.print("Enter the country name: ");
		scanner = new Scanner(System.in);
		String countryName = scanner.nextLine();
		File outputFile = new File("res/common/countries/" + countryName + ".xml");
		if(!outputFile.exists()) {
			outputFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(outputFile);
		writer = new SGMLWriter(fos);
		writer.elementStart("country");
		enter("name");
		enter("flagPath");
		writer.elementStart("information");
		enter("officialName");
		enter("acronym");
		writer.elementEnd("information");
		writer.elementEnd("country");
		writer.close();
	}
}
