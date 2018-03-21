package fi.soveltia.liferay.gsearch.elasticsearch.bundle;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * This component blacklists the default Elasticsearch adapter.
 * 
 * @author Petteri Karttunen
 *
 */
@Component(
	immediate = true
)
public class DefaultAdapterBundleHandler {

	@Activate
	protected void activate(ComponentContext componentContext) throws Exception {

		_bundleContext = componentContext.getBundleContext();

		for (Bundle bundle : _bundleContext.getBundles()) {
			_processBundle(bundle);
		}

		_bundleContext.addBundleListener(_bundleListener);
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		bundleContext.removeBundleListener(_bundleListener);
	}

	private void _processBundle(Bundle bundle) throws Exception {

		String symbolicName = bundle.getSymbolicName();

		if (symbolicName.equals(DEFAULT_ADAPTER_SYMBOLIC_NAME)) {

			if (_log.isInfoEnabled()) {
				_log.info("Uninstalling the standard adapter" + bundle);
			}
			
			bundle.uninstall();
		}
	}

	private BundleContext _bundleContext;

	private final BundleListener _bundleListener = new BundleListener() {

		@Override
		public void bundleChanged(BundleEvent bundleEvent) {
			if (bundleEvent.getType() != BundleEvent.RESOLVED) {
				return;
			}

			_log.info("Running GSearch bundle listener.");
			
			try {
				_processBundle(bundleEvent.getBundle());
			} catch (Exception e) {
				ReflectionUtil.throwException(e);
			}
		}
	};

	// This is the root of the name of the default adapter to be blacklisted

	private static final String DEFAULT_ADAPTER_SYMBOLIC_NAME = "com.liferay.portal.search.elasticsearch";
	
	private static final Log _log = LogFactoryUtil.getLog(DefaultAdapterBundleHandler.class);
}
