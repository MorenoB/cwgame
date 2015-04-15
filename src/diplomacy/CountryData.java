package diplomacy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import map.Leader;
import map.Organisation;
import map.Province;
import map.SubPopulation;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import docs.SGMLObject;
import economy.Currency;
import economy.CurrencyMarket;
import economy.CurrencyTable;
import economy.ResourceTable;

public class CountryData {
	private final String acronym;
	private final float blue;
	private final Color color;
	private final Currency currency;
	private final CurrencyMarket currencyMarket = new CurrencyMarket();
	private final String economy;
	private final Image flag;
	private final String government;
	private final float green;
	private final long initialPop;
	private final Leader leader;
	private final long money = 0;
	private final double monthlyPopGrowth;
	private final String name;
	private final String officialName;
	private long population = 0;
	private final List<Province> provinces = new ArrayList<>();
	private final float red;
	private final ResourceTable resources;
	
	public CountryData(SGMLObject obj) {
		name = obj.getField("name");
		SGMLObject color = obj.getChild("information").getChild("color");
		int r = color.getInt("red");
		int g = color.getInt("green");
		int b = color.getInt("blue");
		red = r / 255f;
		green = g / 255f;
		blue = b / 255f;
		this.color = new Color(r, g, b);
		
		if(obj.hasChild("information")) {
			SGMLObject information = obj.getChild("information");
			officialName = information.getField("official");
			acronym = information.getField("acronym");
			String currencyName = information.getField("currency");
			Currency currencyVal = CurrencyTable.getCurrency(currencyName);
			if(currencyVal == null) {
				currency = CurrencyTable.getCurrency("USD");
			} else {
				currency = currencyVal;
			}
		} else {
			officialName = name;
			acronym = name;
			currency = CurrencyTable.getCurrency("USD");
		}
		
		if(currency == null) {
			System.err.println("Unidentified currency for a country.");
		}
		
		if(obj.hasChild("politics")) {
			SGMLObject politics = obj.getChild("politics");
			government = politics.getField("government");
			economy = politics.getField("economy");
			/*
			 * leader = new Leader(SGMLReaderUtil.readFromPath("res/history/leaders/" +
			 * politics.getField("leader") + ".xml"));
			 */
		} else {
			government = "nogovernment";
			economy = "noeconomy";
			// leader = null;
		}
		
		leader = null;
		
		if(obj.hasChild("population")) {
			SGMLObject population = obj.getChild("population");
			if(population.hasField("size")) {
				System.out.println(name);
				initialPop = population.getLong("size");
			} else {
				initialPop = 1000;
			}
			monthlyPopGrowth = population.getDouble("growth");
		} else {
			initialPop = 1000;
			monthlyPopGrowth = 1.001d;
		}
		
		resources = new ResourceTable();
		String flagPath = "res/gfx/flags/" + obj.getField("flag");
		File flagPathFile = new File(flagPath);
		Image flagImage = null;
		if(flagPathFile.exists()) {
			
			try {
				flagImage = new Image(flagPath);
				flagImage.setFilter(Image.FILTER_LINEAR);
			} catch(SlickException e) {
				e.printStackTrace();
			}
			
		} else {
			try {
				flagImage = new Image(200, 100);
			} catch(SlickException e) {
				e.printStackTrace();
			}
		}
		
		flag = flagImage;
	}
	
	public String getAcronym() {
		return acronym;
	}
	
	public float getBlue() {
		return blue;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public CurrencyMarket getCurrencyMarket() {
		return currencyMarket;
	}
	
	public String getEconomy() {
		return economy;
	}
	
	public Image getFlag() {
		return flag;
	}
	
	public String getGovernment() {
		return government;
	}
	
	public float getGreen() {
		return green;
	}
	
	public Leader getLeader() {
		return leader;
	}
	
	public long getMoney() {
		return money;
	}
	
	public double getMonthlyPopGrowth() {
		return monthlyPopGrowth;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOfficialName() {
		return officialName;
	}
	
	public long getPopulation() {
		return population;
	}
	
	public List<Province> getProvinces() {
		return provinces;
	}
	
	public float getRed() {
		return red;
	}
	
	public ResourceTable getResources() {
		ResourceTable totalNationalResources = new ResourceTable();
		totalNationalResources.add(resources);
		for(Province prov: provinces) {
			for(Organisation org: prov.getData().getOrganisations()) {
				totalNationalResources.add(org.getResources());
			}
			
			for(SubPopulation subPop: prov.getData().getPopulation().getSubPopulations()) {
				totalNationalResources.add(subPop.getResources());
			}
		}
		return totalNationalResources;
	}
	
	public void setPopulation(long value) {
		population = value;
	}
}
