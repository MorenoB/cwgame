package logger;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements AutoCloseable, Closeable {
	private static final Logger INSTANCE = new Logger("log.txt");
	private LogLevel minLogLevel = LogLevel.NONE;
	private final BufferedWriter writer;
	
	public Logger(String path) {
		writer = internalInitFunc(path);
	}
	
	@Override
	public void close() {
		try {
			writer.close();
		} catch(IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public void critical(String msg) {
		log(msg, LogLevel.CRITICAL);
	}
	
	public void debug(String msg) {
		log(msg, LogLevel.DEBUG);
	}
	
	public LogLevel getMinLogLevel() {
		return minLogLevel;
	}
	
	public void info(String msg) {
		log(msg, LogLevel.INFO);
	}
	
	public void log(String msg) {
		this.log(msg, LogLevel.DEBUG);
	}
	
	public void log(String msg, LogLevel level) {
		if(level.ordinal() <= minLogLevel.ordinal()) {
			try {
				writer.write(msg);
				if(!msg.endsWith("\n")) {
					writer.write("\n");
				}
			} catch(IOException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public void log(String[] logArray, boolean logSysSep) {
		String sysSep = System.getProperty("line.separator");
		for(String element: logArray) {
			log(element);
			if(logSysSep) {
				log(sysSep);
			}
		}
	}
	
	public void setMinLogLevel(LogLevel value) {
		minLogLevel = value;
	}
	
	public static Logger get() {
		return INSTANCE;
	}
	
	private static BufferedWriter internalInitFunc(String path) {
		try {
			File file = new File(path);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			return bufferedWriter;
		} catch(IOException exception) {
			exception.printStackTrace();
		}
		
		return null;
	}
}
