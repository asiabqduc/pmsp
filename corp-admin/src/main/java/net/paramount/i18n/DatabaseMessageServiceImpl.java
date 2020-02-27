/**
 * 
 */
package net.paramount.i18n;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.ibm.icu.text.MessageFormat;

import net.paramount.common.CommonUtility;
import net.paramount.common.ListUtility;

/**
 * @author ducbq
 *
 */
@Component("persistenceMessageSource")
public class DatabaseMessageServiceImpl implements PersistenceMessageService/*MessageSource */{
	@Inject
	private LanguageEntityRepository messageSourceRepository;

	/**
	 * Try to resolve the message. Return default message if no message was found.
	 * @param code the message code to look up, e.g. 'calculator.noRateSet'.
	 * MessageSource users are encouraged to base message names on qualified class
	 * or package names, avoiding potential conflicts and ensuring maximum clarity.
	 * @param args an array of arguments that will be filled in for params within
	 * the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
	 * or {@code null} if none
	 * @param defaultMessage a default message to return if the lookup fails
	 * @param locale the locale in which to do the lookup
	 * @return the resolved message if the lookup was successful, otherwise
	 * the default message passed as a parameter (which may be {@code null})
	 * @see #getMessage(MessageSourceResolvable, Locale)
	 * @see java.text.MessageFormat
	 */
	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return resolveMessage(code, args, locale);
	}

	/**
	 * Try to resolve the message. Treat as an error if the message can't be found.
	 * @param code the message code to look up, e.g. 'calculator.noRateSet'.
	 * MessageSource users are encouraged to base message names on qualified class
	 * or package names, avoiding potential conflicts and ensuring maximum clarity.
	 * @param args an array of arguments that will be filled in for params within
	 * the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
	 * or {@code null} if none
	 * @param locale the locale in which to do the lookup
	 * @return the resolved message (never {@code null})
	 * @throws NoSuchMessageException if no corresponding message was found
	 * @see #getMessage(MessageSourceResolvable, Locale)
	 * @see java.text.MessageFormat
	 */
	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return resolveMessage(code, args, locale);
	}

	/**
	 * Try to resolve the message using all the attributes contained within the
	 * {@code MessageSourceResolvable} argument that was passed in.
	 * <p>NOTE: We must throw a {@code NoSuchMessageException} on this method
	 * since at the time of calling this method we aren't able to determine if the
	 * {@code defaultMessage} property of the resolvable is {@code null} or not.
	 * @param resolvable the value object storing attributes required to resolve a message
	 * (may include a default message)
	 * @param locale the locale in which to do the lookup
	 * @return the resolved message (never {@code null} since even a
	 * {@code MessageSourceResolvable}-provided default message needs to be non-null)
	 * @throws NoSuchMessageException if no corresponding message was found
	 * (and no default message was provided by the {@code MessageSourceResolvable})
	 * @see MessageSourceResolvable#getCodes()
	 * @see MessageSourceResolvable#getArguments()
	 * @see MessageSourceResolvable#getDefaultMessage()
	 * @see java.text.MessageFormat
	 */
	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		String message = null;
		for (String code : resolvable.getCodes()) {
			message = resolveMessage(code, resolvable.getArguments(), locale);
			if (message != null) {
				return message;
			}
		}
		return null;
	}

	private String resolveMessage(String code, Object[] args, Locale locale) {
		MessageSourceEntity messageSourceEntity = messageSourceRepository.findByKeyAndLocale(code, getProcessingLocaleString(locale));
		String resolvedMessage = (null != messageSourceEntity)?messageSourceEntity.getContent():code;
		if (CommonUtility.isNotEmpty(args)) {
			resolvedMessage = MessageFormat.format(messageSourceEntity.getContent(), args);
		}
		return resolvedMessage; 
	}

	@Override
	public void saveMessage(String key, String content, Locale locale) {
		MessageSourceEntity messageEntity = this.messageSourceRepository.findByKeyAndLocale(key, getProcessingLocaleString(locale));
		if (null==messageEntity) {
			messageEntity = MessageSourceEntity.builder()
					.key(key)
					.content(content)
					.locale(getProcessingLocaleString(locale))
					.build();
		} else {
			messageEntity.setContent(content);
		}
		messageSourceRepository.save(messageEntity);
	}

	@Override
	public List<MessageSourceEntity> getMessages(Locale locale) {
		return this.messageSourceRepository.findByLocale(getProcessingLocaleString(locale));
	}

	private String getProcessingLocaleString(Locale locale) {
		return locale.getLanguage();
	}

	@Override
	public Map<String, String> getMessagesMap(Locale locale) {
		List<MessageSourceEntity> messages = this.getMessages(locale);
		Map<String, String> messagesMap = ListUtility.createMap();
		for (MessageSourceEntity mse :messages) {
			messagesMap.put(mse.getKey(), mse.getContent());
		}
		return messagesMap;
	}

}
