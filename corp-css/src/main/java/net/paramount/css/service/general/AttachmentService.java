package net.paramount.css.service.general;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import net.paramount.domain.entity.Attachment;
import net.paramount.framework.model.SearchParameter;
import net.paramount.framework.service.GenericService;

@Transactional
public interface AttachmentService extends GenericService<Attachment, Long> {
	Optional<Attachment> getByName(String name);
	/**
	 * Get one Attachments with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Attachments
	 */
	Page<Attachment> getObjects(SearchParameter searchParameter);
}
