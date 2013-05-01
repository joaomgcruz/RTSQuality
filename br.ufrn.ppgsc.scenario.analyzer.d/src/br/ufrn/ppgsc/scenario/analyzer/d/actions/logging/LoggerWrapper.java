package br.ufrn.ppgsc.scenario.analyzer.d.actions.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore;

public abstract class LoggerWrapper {

	static {
		FileHandler fh = null;
		try {
			fh = new FileHandler("C:/Eclipse/EclipseJuno/workspace/br.ufrn.ppgsc.scenario.analyzer.d/log.txt", true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		fh.setFormatter(new SimpleFormatter());
		Logger.getLogger("br").setUseParentHandlers(false);
		Logger.getLogger("br").addHandler(fh);
	}

	@ScenarioIgnore
	public static Logger getInstance(String name) {
		return Logger.getLogger(name);
	}

}