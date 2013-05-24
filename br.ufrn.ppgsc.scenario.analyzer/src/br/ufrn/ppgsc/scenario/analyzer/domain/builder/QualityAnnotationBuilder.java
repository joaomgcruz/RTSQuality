package br.ufrn.ppgsc.scenario.analyzer.domain.builder;

import java.lang.annotation.Annotation;
import java.util.List;

import br.ufrn.ppgsc.scenario.analyzer.dao.GenericDAO;
import br.ufrn.ppgsc.scenario.analyzer.domain.QualityAnnotation;
import br.ufrn.ppgsc.scenario.analyzer.service.DatabaseService;

public class QualityAnnotationBuilder {

	public static QualityAnnotation build(Annotation annotation) {
		QualityAnnotation qa = new QualityAnnotation();
		qa.setName(annotation.annotationType().getSimpleName());
		GenericDAO<br.ufrn.ppgsc.scenario.analyzer.domain.QualityAnnotation> dao = new DatabaseService<br.ufrn.ppgsc.scenario.analyzer.domain.QualityAnnotation>().getGenericDAO();
		List<QualityAnnotation> qas = dao.readAll(qa.getClass());
		for (QualityAnnotation qualityAnnotation : qas) {
			if (qualityAnnotation.getName().equals(qa.getName())) {
				return qualityAnnotation;
			}
		}
		return null;
	}

}
