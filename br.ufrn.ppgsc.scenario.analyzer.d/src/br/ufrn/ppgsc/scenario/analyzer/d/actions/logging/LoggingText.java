package br.ufrn.ppgsc.scenario.analyzer.d.actions.logging;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import br.ufrn.ppgsc.scenario.analyzer.d.aspects.IActionAnnotation;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimePerformanceData;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeQAData;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeReliabilityData;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeRobustnessData;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeSecurityData;

public class LoggingText implements IActionAnnotation {

	private static Logger logger;
	
	public LoggingText() {
		if (logger == null) {
			FileHandler fh = null;
			try {
				fh = new FileHandler("log.txt");
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			fh.setFormatter(new SimpleFormatter());
			
			logger = Logger.getLogger(LoggingText.class.getName());
			logger.setUseParentHandlers(false);
			logger.addHandler(fh);
		}
	}
	
	public void execute(RuntimeQAData<? extends Annotation> metadata) {
		if (metadata instanceof RuntimePerformanceData) {
			RuntimePerformanceData data = (RuntimePerformanceData) metadata;
	
			String log = "@Performance(" + data.getAnnotation().name() + ", " + data.getAnnotation().limit_time() +
					")\n\tin: " + metadata.getMethod().getDeclaringClass().getName() + "." +
					data.getMethod().getName()	+ "\n\tTime: " + data.getLastTime() + "ms\n";
			
			if (data.getLastTime() > data.getAnnotation().limit_time())
				logger.warning(log);
			else
				logger.info(log);
		}
		else if (metadata instanceof RuntimeSecurityData) {
			RuntimeSecurityData data = (RuntimeSecurityData) metadata;
			
			String log = "@Security(" + data.getAnnotation().name() + ")\n\tin:" +
					metadata.getMethod().getDeclaringClass().getName() + "." +
					data.getMethod().getName() + "\n";
			
			logger.info(log);
		}
		else if (metadata instanceof RuntimeReliabilityData) {
			RuntimeReliabilityData data = (RuntimeReliabilityData) metadata;
			
			String log = "@Reliability(" + data.getAnnotation().name() + ", " +
					data.getAnnotation().failure_rate() + ")\n\tin:" +
					metadata.getMethod().getDeclaringClass().getName() + "." +
					data.getMethod().getName() + "\n\tFail: " + data.isFail() + "\n";
			
			logger.info(log);
		}
		else if (metadata instanceof RuntimeRobustnessData) {
			RuntimeRobustnessData data = (RuntimeRobustnessData) metadata;
			
			String log = "@Robustness(" + data.getAnnotation().name() + ")\n\tin:" +
					metadata.getMethod().getDeclaringClass().getName() + "." +
					data.getMethod().getName() + "\n\tFail: " +
					(data.getException() == null ? false : true) + "\n";
			
			logger.info(log);
		}
			
	}

}
