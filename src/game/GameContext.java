package game;

import java.util.Calendar;
import java.util.GregorianCalendar;

import logger.Logger;
import map.ProvinceScanner;
import politics.GlobalPoliticalContext;
import diplomacy.Countries;
import diplomacy.Country;
import docs.SGMLObject;
import docs.SGMLReaderUtil;

public class GameContext {
	public static final Calendar calendar = new GregorianCalendar();
	public static final String definePath = "res/common/Defines.xml";
	public static final SGMLObject defines = SGMLReaderUtil.readFromPath(definePath).getChild("defines");
	public static final GlobalPoliticalContext globalContext = new GlobalPoliticalContext("res/politics/");
	public static boolean paused = true;
	public static Country playerCountry;
	public static final ProvinceScanner provinceScanner = new ProvinceScanner(false);
	public static final int thresholdOfClock = defines.getInt("clockThreshold");
	
	public static void init() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Logger.get().close();
			}
		});
		
		SGMLObject date = defines.getChild("date");
		calendar.set(Calendar.YEAR, date.getInt("year"));
		calendar.set(Calendar.MONTH, date.getInt("month") - 1);
		calendar.set(Calendar.DAY_OF_MONTH, date.getInt("day"));
		Countries.init();
		GameContext.provinceScanner.scan();
	}
}
