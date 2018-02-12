package fi.soveltia.liferay.gsearch.elasticsearch.internal.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch.internal.util.ResourceUtil;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;

/**
 * This class is responsible for creating the querySuggestion mapping when index
 * is (re)created.
 * 
 * See also CompanyIndexFactory.
 * 
 * @author Petteri Karttunen
 *
 */
public class GSearchQuerySuggestionTypeFactory {

	public void createGSearchQuerysuggestionMapping(CreateIndexRequestBuilder createIndexRequestBuilder) {

		String mappings = ResourceUtil.getResourceAsString(getClass(), 
				GSEARCH_QUERYSUGGESTION_MAPPING_FILE_NAME);

		_log.info("Creating GSearch querySuggestion mapping.");

		createIndexRequestBuilder.addMapping(GSEARCH_QUERYSUGGESTION_MAPPING_TYPE, mappings);

	}

	private static final Log _log = LogFactoryUtil.getLog(GSearchQuerySuggestionTypeFactory.class);

	private static final String GSEARCH_QUERYSUGGESTION_MAPPING_FILE_NAME = 
			"/META-INF/mappings/gsearch-querysuggestion-mapping.json";

	private static final String GSEARCH_QUERYSUGGESTION_MAPPING_TYPE = "querySuggestion";
}
