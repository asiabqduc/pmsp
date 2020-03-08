package net.paramount.css.service.impl;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.paramount.css.repository.general.ItemTypeRepository;
import net.paramount.css.service.general.ItemTypeService;
import net.paramount.entity.general.ItemType;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.repository.BaseRepository;
import net.paramount.framework.service.GenericServiceImpl;

@Service
public class ItemTypeServiceImpl extends GenericServiceImpl<ItemType, Long> implements ItemTypeService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6623847070381520873L;

	@Inject
	private ItemTypeRepository repository;

	@Override
	protected BaseRepository<ItemType, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected Page<ItemType> performSearch(String keyword, Pageable pageable) {
		return this.repository.search(keyword, pageable);
	}

	@Override
	public ItemType getByCode(String code) throws ObjectNotFoundException {
		return this.repository.findByCode(code);
	}

	@Override
	public ItemType getByName(String name) throws ObjectNotFoundException {
		return this.repository.findByName(name);
	}
}
