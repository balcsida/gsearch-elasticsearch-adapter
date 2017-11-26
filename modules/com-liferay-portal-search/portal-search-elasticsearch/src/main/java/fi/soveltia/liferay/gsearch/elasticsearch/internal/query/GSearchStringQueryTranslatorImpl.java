package fi.soveltia.liferay.gsearch.elasticsearch.internal.query;

import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.search.elasticsearch.query.StringQueryTranslator;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import fi.soveltia.liferay.gsearch.elasticsearch.query.QueryStringQueryTranslator;
import fi.soveltia.liferay.gsearch.query.QueryStringQuery;

/**
 * This is the custom StringQuery translator service.
 * 
 * With higher service ranking this takes precedence over the default one.
 * 
 * If it gets a regular StrinQuery object it translates it with the default way.
 * It it gets the custom QueryStringQuery object the translation is made by
 * the custom translator.
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
			
			// If object is of extended type then use custom translator
			
			if (stringQuery instanceof QueryStringQuery) {

				return _queryStringQueryTranslator.translate((QueryStringQuery)stringQuery);
			}

			QueryStringQueryBuilder queryStringQueryBuilder =
				QueryBuilders.queryStringQuery(stringQuery.getQuery());

			if (!stringQuery.isDefaultBoost()) {
				queryStringQueryBuilder.boost(stringQuery.getBoost());
			}
			
			return queryStringQueryBuilder;
		}

		@Reference(unbind = "-")
		protected void setQueryStringQueryTranslator(QueryStringQueryTranslator queryStringQueryTranslator) {

			_queryStringQueryTranslator = queryStringQueryTranslator;
		}
		
		@Reference
		private QueryStringQueryTranslator _queryStringQueryTranslator;

	}
