package testing.economy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import logger.Logger;
import map.Province;
import map.ProvinceScanner;
import util.StringSanitizer;
import economy.CurrencyTable;
import economy.ProvinceMarket;
import economy.ResourceTable;

public class EconomyTest {
	public static void main(String[] args) throws Exception {
		CurrencyTable.init();
		ResourceTable.init();
		ProvinceScanner provScan = new ProvinceScanner(true);
		provScan.scan();
		boolean running = true;
		Scanner scanner = new Scanner(System.in);
		
		List<Province> provinces = new ArrayList<>();
		for(Province prov: provScan.getProvinces()) {
			if(!prov.isWater() && prov.getData().hasHistory()) {
				provinces.add(prov);
			}
		}
		
		int step = 0;
		while(running) {
			System.out.println("Enter input:");
			String input = StringSanitizer.sanitize(scanner.next().toLowerCase());
			switch(input) {
				case "next":
					int i = 0;
					final int l = provinces.size();
					double growthRate = 1.001d;
					
					for(i = 0; i < l; i++) {
						Province prov = provinces.get(i);
						ProvinceMarket market = prov.getMarket();
						prov.updateResources();
						prov.updatePopulation(growthRate);
						market.resetData();
						market.addProducedResources();
						market.consumeOwnedResources();
						market.addExcessResourcesToSupply();
					}
					
					for(i = 0; i < l; i++) {
						Province prov = provinces.get(i);
						ProvinceMarket market = prov.getMarket();
						market.addNeeds();
						market.calculatePrices();
					}
					
					for(i = 0; i < l; i++) {
						Province prov = provinces.get(i);
						Logger.get().log(
								"Resources for province " + prov.getData().getName() + " (" + prov.getId()
								+ ") during step " + step + ".");
						ResourceTable localResources = prov.getLocalResources();
						Logger.get().log(localResources.toString());
						Logger.get().log(prov.getData().getPopulation().getSubPopulationString(), false);
						Logger.get().log("\n");
					}
					
					Logger.get().log("\n");
					Logger.get().log("\n");
					
					break;
				case "exit":
					running = false;
					System.out.print(running);
					break;
				default:
					System.out.println(input);
					break;
			}
			
			step++;
		}
		
		Logger.get().close();
	}
}
