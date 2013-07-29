package br.ufrn.dimap.taskanalyzer.history;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

import de.regnis.q.sequence.QSequenceDifferenceBlock;
import de.regnis.q.sequence.line.QSequenceLineCache;
import de.regnis.q.sequence.line.diff.QDiffGenerator;
import de.regnis.q.sequence.line.diff.QDiffGeneratorFactory;
import de.regnis.q.sequence.line.diff.QDiffManager;
import de.regnis.q.sequence.line.diff.QDiffSequenceGenerator;
import de.regnis.q.sequence.line.diff.QDiffUniGenerator;

public class TestTrackerDiffUniGenerator extends QDiffSequenceGenerator implements QDiffGeneratorFactory {

	// Constants ==============================================================

	public static final String TYPE = "unified";

	// Static =================================================================

	public static void setup() {
		QDiffManager.registerDiffGeneratorFactory(new TestTrackerDiffUniGenerator(), QDiffUniGenerator.TYPE);
	}

	// Fields =================================================================

	private Map<Object,Object> myGeneratorsCache;

	// Setup ==================================================================

	public TestTrackerDiffUniGenerator(Map<Object,Object> properties, String header) {
		super(initProperties(properties), header);
	}

	private TestTrackerDiffUniGenerator() {
		super(null, null);
	}

	// Implemented ============================================================

	public void generateDiffHeader(String item, String leftInfo, String rightInfo, Writer output) throws IOException {
	}

	protected void processBlock(QSequenceDifferenceBlock[] segment, QSequenceLineCache sourceLines, QSequenceLineCache targetLines,
	                            String encoding, Writer output) throws IOException {
		for (int i = 0; i < segment.length; i++) {
			QSequenceDifferenceBlock block = segment[i];
			for (int j = block.getLeftFrom(); j <= block.getLeftTo(); j++) {
				String line = printLine(sourceLines.getLine(j), encoding);
				print("        <br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedLine>", output);
				print(getEOL(), output);
				print("          "+(new XStream()).toXML(new Date()), output);
				print(getEOL(), output);
				print("          <revision>0</revision>", output);
				print(getEOL(), output);
				print("          <author></author>", output);
				print(getEOL(), output);
				print("          <line>"+line+"</line>", output);
				print(getEOL(), output);
				print("          <lineNumber>-"+(j+1)+"</lineNumber>", output);
				print(getEOL(), output);
				print("        </br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedLine>", output);
				print(getEOL(), output);
			}
			for (int j = block.getRightFrom(); j <= block.getRightTo(); j++) {
				String line = printLine(targetLines.getLine(j), encoding);
				print("        <br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedLine>", output);
				print(getEOL(), output);
				print("          "+(new XStream()).toXML(new Date()), output);
				print(getEOL(), output);
				print("          <revision>"+"0"+"</revision>", output);
				print(getEOL(), output);
				print("          <author>"+""+"</author>", output);
				print(getEOL(), output);
				print("          <line>"+line+"</line>", output);
				print(getEOL(), output);
				print("          <lineNumber>+"+(j+1)+"</lineNumber>", output);
				print(getEOL(), output);
				print("        </br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedLine>", output);
				print(getEOL(), output);
			}
		}
		print("      </changedLines>", output);
		print(getEOL(), output);
		print("    </br.ufrn.taskanalyser.framework.miner.ClassUpdates>", output);
		print(getEOL(), output);
	}

	public QDiffGenerator createGenerator(@SuppressWarnings("rawtypes") Map properties) {
		if (myGeneratorsCache == null) {
			myGeneratorsCache = new HashMap<Object,Object>();
		}
		QDiffGenerator generator = (QDiffGenerator)myGeneratorsCache.get(properties);
		if (generator != null) {
			return generator;
		}
		generator = new QDiffUniGenerator(properties, null);
		myGeneratorsCache.put(properties, generator);
		return generator;
	}

	@SuppressWarnings("unchecked")
	private static Map<Object,Object> initProperties(Map<Object,Object> properties) {
		if (properties == null || !properties.containsKey(QDiffGeneratorFactory.GUTTER_PROPERTY)) {
			properties = new HashMap<Object,Object>(properties == null ? Collections.EMPTY_MAP : properties);
			properties.put(QDiffGeneratorFactory.GUTTER_PROPERTY, "3");
		}
		return properties;
	}
}
