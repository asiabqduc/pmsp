/**
 * 
 */
package net.paramount.comm.comp;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import net.paramount.common.CommonConstants;
import net.paramount.common.ListUtility;
import net.paramount.css.service.config.ConfigurationService;
import net.paramount.entity.config.Configuration;
import net.paramount.entity.config.ConfigurationDetail;
import net.paramount.exceptions.CryptographyException;
import net.paramount.framework.component.CompCore;
import net.paramount.global.GlobalConstants;
import net.paramount.security.GlobalCryptogramRepository;
import net.paramount.security.base.Cryptographer;

/**
 * @author ducbq
 *
 * Global communication repository manager
 */
@Component
public class EmailConfigurationHelper extends CompCore {
	private static final long serialVersionUID = -2433918570834008630L;

	@Inject
	private ConfigurationService configurationService;

	public void configureEmailService() {
		Configuration config = null;
		try {
			if (false == configurationService.isExistsByName(GlobalConstants.CONFIG_ENTRY_NAME_EMAIL)) {
				config = buildEmailConfiguration();
				configurationService.save(config);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	private Configuration buildEmailConfiguration() throws CryptographyException {
		GlobalCryptogramRepository globalCryptogramRepository = GlobalCryptogramRepository.getInstance();
		Cryptographer defaultCryptographer = globalCryptogramRepository.getDefaultCryptographer();
		Configuration emailConfig = Configuration.builder()
				.group(GlobalConstants.CONFIG_GROUP_APP)
				.name(GlobalConstants.CONFIG_ENTRY_NAME_EMAIL)
				.value("Application email configuration")
				.build();

		List<ConfigurationDetail> configDetails = ListUtility.createDataList(
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_HOST).value("smtp.gmail.com").build(),
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_PORT).value("587").build(),
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_USER_NAME).value(defaultCryptographer.encode("duc.bui.appdev@gmail.com", GlobalCryptogramRepository.SECRET_KEY)).build(),
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_PASSWORD).value(defaultCryptographer.encode("zaq1@#EDC", GlobalCryptogramRepository.SECRET_KEY)).build(),
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_START_TLS_ENABLE).value("true").build(), 
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_AUTH).value("true").build(),
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_TRANSPORT_PROTOCOL).value("smtp").build(), 
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_TRANSPORT_PROTOCOLX).value("smtp").build(),
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_DEBUG).value("false").build(), 
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_SSL_TRUST).value("smtp.gmail.com").build(),
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_START_TLS_REQUIRED).value("true").build(),
				ConfigurationDetail.builder().name(GlobalConstants.CONFIG_EMAIL_ENCODING).value(CommonConstants.ENCODING_NAME_UTF8).build());

		emailConfig.addConfigurationDetails(configDetails);
		return emailConfig;
	}
}