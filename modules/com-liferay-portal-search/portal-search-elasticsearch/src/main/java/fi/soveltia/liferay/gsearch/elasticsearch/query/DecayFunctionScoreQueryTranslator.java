package fi.soveltia.liferay.gsearch.elasticsearch.query;

import org.elasticsearch.index.query.QueryBuilder;

import fi.soveltia.liferay.gsearch.query.DecayFunctionScoreQuery;

/**
 * GSearch Closer the Better query type translator service.
 * 
 * @author Petteri Karttunen
 *
 */
public interface DecayFunctionScoreQueryTranslator {

	public QueryBuilder translate(DecayFunctionScoreQuery query);

}
