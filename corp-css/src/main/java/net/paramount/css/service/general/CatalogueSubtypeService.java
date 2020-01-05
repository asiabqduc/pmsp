package net.paramount.css.service.general;

import java.util.Optional;

import org.springframework.data.domain.Page;

import net.paramount.entity.general.CatalogueSubtype;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.model.SearchParameter;
import net.paramount.framework.service.GenericService;

public interface CatalogueSubtypeService extends GenericService<CatalogueSubtype, Long> {

	/**
	 * Get one Catalogue with the provided name.
	 * 
	 * @param code
	 *            The CatalogueSubtype name
	 * @return The CatalogueSubtype
	 * @throws ObjectNotFoundException
	 *             If no such Catalogue exists.
	 */
	Optional<CatalogueSubtype> getOne(String name) throws ObjectNotFoundException;

	/**
	 * Get one Catalogues with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Catalogues
	 */
	Page<CatalogueSubtype> getObjects(SearchParameter searchParameter);

}
