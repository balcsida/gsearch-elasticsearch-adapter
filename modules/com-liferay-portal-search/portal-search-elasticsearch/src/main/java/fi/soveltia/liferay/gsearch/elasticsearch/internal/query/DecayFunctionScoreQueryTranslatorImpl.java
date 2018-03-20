package fi.soveltia.liferay.gsearch.elasticsearch.internal.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.DecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.exp.ExponentialDecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.gauss.GaussDecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.lin.LinearDecayFunctionBuilder;
import org.osgi.service.component.annotations.Component;

import fi.soveltia.liferay.gsearch.elasticsearch.query.DecayFunctionScoreQueryTranslator;
import fi.soveltia.liferay.gsearch.query.DecayFunctionScoreQuery;

/**
 * GSearch Decay Funtion Score query translator impl.
 * 
 * @author Petteri Karttunen
 *
 */
@Component(
	immediate=true,
	service = DecayFunctionScoreQueryTranslator.class
)
public class DecayFunctionScoreQueryTranslatorImpl implements DecayFunctionScoreQueryTranslator {

	public QueryBuilder translate(DecayFunctionScoreQuery query) {

		DecayFunctionBuilder decayFunctionBuilder;

		if (query.getFunctionType().equals("exp")) {

			decayFunctionBuilder = new ExponentialDecayFunctionBuilder(query.getFieldName(), query.getOrigin(),
					query.getScale());
		} else if (query.getFunctionType().equals("linear")) {

			decayFunctionBuilder = new LinearDecayFunctionBuilder(query.getFieldName(), query.getOrigin(),
					query.getScale());
		} else {

			decayFunctionBuilder = new GaussDecayFunctionBuilder(query.getFieldName(), query.getOrigin(),
					query.getScale());
		}
		
		decayFunctionBuilder.setOffset(query.getOffset());
		decayFunctionBuilder.setDecay(query.getDecay());
		decayFunctionBuilder.setWeight(query.getWeight());

		FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(decayFunctionBuilder);

		functionScoreQueryBuilder.boost(query.getBoost());
		
		if (query.getBoostMode() != null) {
			functionScoreQueryBuilder.boostMode(query.getBoostMode());
		}

		if (query.getScoreMode() != null) {
			functionScoreQueryBuilder.scoreMode(query.getScoreMode());
		}

		if (query.getMaxBoost() != null) {
			functionScoreQueryBuilder.maxBoost(query.getMaxBoost());
		}

		if (query.getMinScore() != null) {
			functionScoreQueryBuilder.setMinScore(query.getMinScore());
		}

		return functionScoreQueryBuilder;
	}
}
