package fi.soveltia.liferay.gsearch.elasticsearch.internal.query;

import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.search.elasticsearch.query.StringQueryTranslator;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import fi.soveltia.liferay.gsearch.elasticsearch.query.DecayFunctionScoreQueryTranslator;
import fi.soveltia.liferay.gsearch.elasticsearch.query.QueryStringQueryTranslator;
import fi.soveltia.liferay.gsearch.query.DecayFunctionScoreQuery;
import fi.soveltia.liferay.gsearch.query.QueryStringQuery;

/**
 * This is the custom StringQuery translator service.
 *
 * This is working now as a router for all GSearch custom query types. This is
 * not exactly "clean" but this way modifying the standard QueryVisitor class
 * from portal-search has been avoided. 
 * 
 * If it gets a regular StrinQuery object it translates it with the default way.
 * It it gets the custom QueryStringQuery object the translation is made by the
 * custom translator.
 * 
 * @author Petteri Karttunen
 *
 */
@Component(
		immediate=true,
		property = {
			"service.ranking:Integer=100"
		},
		service = StringQueryTranslator.class
	)
public class GSearchStringQueryTranslatorImpl implements StringQueryTranslator {

	@Override
	public QueryBuilder translate(StringQuery stringQuery) {

		// If object is one of the extended types then use custom translator

		if (stringQuery instanceof QueryStringQuery) {

			return _queryStringQueryTranslator.translate((QueryStringQuery) stringQuery);

		} else if (stringQuery instanceof DecayFunctionScoreQuery) {

			return _decayFunctionScoreQueryTranslator.translate((DecayFunctionScoreQuery) stringQuery);
		}

		QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(stringQuery.getQuery());

		if (!stringQuery.isDefaultBoost()) {
			queryStringQueryBuilder.boost(stringQuery.getBoost());
		}

		return queryStringQueryBuilder;
	}

	@Reference
	private DecayFunctionScoreQueryTranslator _decayFunctionScoreQueryTranslator;

	@Reference
	private QueryStringQueryTranslator _queryStringQueryTranslator;

}
