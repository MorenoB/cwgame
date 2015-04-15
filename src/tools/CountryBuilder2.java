package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import util.StringSanitizer;
import docs.SGMLWriter;

public class CountryBuilder2 {
	private static final String BASE_PATH = "res/history/provinces/gen2/";
	private int baseTax;
	public String countryName;
	public double currentLiteracy;
	public String currentNationality;
	public String currentReligion;
	private final List<ProvInfo> provinces = new ArrayList<>();
	public long totalPopulation;
	
	private void calcBaseTax() {
		baseTax = 0;
		for(ProvInfo prov: provinces) {
			baseTax += prov.baseTax;
		}
	}
	
	public void finish() throws Exception {
		genDirectories();
		calcBaseTax();
		
		for(ProvInfo prov: provinces) {
			int provDirIndex = prov.id / 100;
			String provFilePath = BASE_PATH + provDirIndex + "/" + prov.id + ".xml";
			File provFile = new File(provFilePath);
			if(!provFile.exists()) {
				provFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(provFile);
			SGMLWriter writer = new SGMLWriter(fos);
			writeProv(writer, prov);
			writer.close();
		}
	}
	
	private void genDirectories() {
		for(int i = 0; i < 30; i++) {
			File dir = new File(BASE_PATH + i + "/");
			if(!dir.exists()) {
				dir.mkdirs();
			}
		}
		
	}
	
	private void writeProv(SGMLWriter writer, ProvInfo prov) {
		long popPerBT = totalPopulation / baseTax;
		writer.dataElement("name", prov.provName);
		writer.dataElement("owner", countryName);
		writer.dataElement("city", prov.cityName);
		writer.dataElement("resource", prov.resource);
		writer.elementStart("populations");
		writer.elementStart("population");
		writer.dataElement("nationality", prov.nationality);
		writer.dataElement("religion", prov.religion);
		writer.dataElement("size", (popPerBT * prov.baseTax) + "");
		writer.dataElement("type", "Farmer");
		writer.dataElement("literacy", prov.literacy + "");
		writer.elementEnd("population");
		writer.elementEnd("populations");
	}
	
	public static void main(String[] args) throws Exception {
		CountryBuilder2 cb2 = new CountryBuilder2();
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		
		System.out.print("Enter country name: ");
		cb2.countryName = StringUtils.capitalize(nextLine(scanner));
		System.out.print("Enter country population (Nov 1949): ");
		cb2.totalPopulation = Long.parseLong(nextLine(scanner));
		System.out.println("Now, please use the nationality, religion and literacy commands.");
		
		while(running) {
			String input = nextLine(scanner).toLowerCase();
			switch(input) {
				case "exit":
					running = false;
					continue;
				case "finish":
					cb2.finish();
					break;
				case "new":
					ProvInfo newProv = new ProvInfo();
					newProv.literacy = cb2.currentLiteracy;
					newProv.religion = cb2.currentReligion;
					newProv.nationality = cb2.currentNationality;
					System.out.print("Enter province id: ");
					newProv.id = Integer.parseInt(nextLine(scanner));
					System.out.print("Enter province name: ");
					newProv.provName = StringUtils.capitalize(nextLine(scanner));
					System.out.print("Enter city name: ");
					newProv.cityName = StringUtils.capitalize(nextLine(scanner));
					System.out.print("Enter city resource: ");
					newProv.resource = StringUtils.capitalize(nextLine(scanner));
					System.out.print("Enter city base tax: ");
					newProv.baseTax = Integer.parseInt(nextLine(scanner));
					cb2.provinces.add(newProv);
					break;
				case "literacy":
					cb2.currentLiteracy = Double.parseDouble(nextLine(scanner));
					System.out.println("New literacy rate set to " + cb2.currentLiteracy);
					break;
				case "religion":
					cb2.currentReligion = StringUtils.capitalize(nextLine(scanner));
					System.out.println("New religion set to " + cb2.currentReligion);
					break;
				case "nationality":
					cb2.currentNationality = StringUtils.capitalize(nextLine(scanner));
					System.out.println("New nationality set to " + cb2.currentNationality);
					break;
			}
		}
	}
	
	private static String nextLine(Scanner scanner) {
		return StringSanitizer.sanitize(scanner.nextLine());
	}
	
	private static final class ProvInfo {
		public int baseTax;
		public String cityName;
		public int id;
		public double literacy;
		public String nationality;
		public String provName;
		public String religion;
		public String resource;
	}
}
