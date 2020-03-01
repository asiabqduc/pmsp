package net.paramount.framework.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.paramount.common.CommonBeanUtils;
import net.paramount.common.CommonConstants;
import net.paramount.common.ListUtility;
import net.paramount.exceptions.ExecutionContextException;
import net.paramount.exceptions.MspRuntimeException;
import net.paramount.framework.component.ComponentBase;
import net.paramount.framework.entity.ObjectBase;
import net.paramount.framework.repository.BaseRepository;


@Service
public abstract class ServiceImpl<EntityType extends ObjectBase, Key extends Serializable> extends ComponentBase implements IService<EntityType, Key>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7920908481607510076L;

	protected abstract BaseRepository<EntityType, Key> getRepository();

	@PersistenceContext /* (unitName = GlobalConstants.APPLICATION_PERSISTENCE_UNIT) */
	protected EntityManager em;

  /**
   * Get entity with the provided key.
   * 
   * @param id The entity key
   * @return The entity
   */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public EntityType getObject(Key id) {
		EntityType entity = getRepository().getOne(id);
		return entity;
	}

	protected EntityType getOptionalObject(Optional<EntityType> optObject) {
		return getOptional(optObject);
	}

	//////////////////////////Revise and exclude as soon as possible
	protected final Page<EntityType> DUMMY_PAGEABLE = new PageImpl<EntityType>(new ArrayList<EntityType>());
	protected final List<EntityType> DUMMY_LIST = ListUtility.createDataList();

	//protected abstract Page<ClassType> performSearch(String keyword, Pageable pageable);

	protected Pageable createDefaultPageable() {
    PageRequest pageRequest = PageRequest.of(CommonConstants.DEFAULT_PAGE_BEGIN, CommonConstants.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
		return pageRequest;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Page<EntityType> searchObjects(String keyword, Pageable pageable) {
		return performSearch(keyword, pageable);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Page<EntityType> search(String keyword){
		Pageable pageable = this.createDefaultPageable();
		return performSearch(keyword, pageable);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Page<EntityType> search(String keyword, Pageable pageable){
		return performSearch(keyword, pageable);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Page<EntityType> search(Map<String, Object> parameters) {
		String keyword = (String)parameters.get(CommonConstants.PARAM_KEYWORD);
		Pageable pageable = (Pageable)parameters.get(CommonConstants.PARAM_PAGEABLE);
		return performSearch(keyword, pageable);
	}

	protected Page<EntityType> performSearch(String keyword, Pageable pageable){
		throw new MspRuntimeException("Not implemented yet!!!");//DUMMY_PAGEABLE;
	}

	protected List<EntityType> performSearch(Object parameter){
		Object findingResult = null;
		List<EntityType> searchResult = null;
		try {
			findingResult = CommonBeanUtils.callMethod(this.getRepository(), "find", ListUtility.createMap("keyword", parameter), PACKAGE_PREFIX);
			if (findingResult instanceof List) {
				searchResult = (List<EntityType>)findingResult;
			}
		} catch (ExecutionContextException e) {
			log.error(e);
		}
		return (null==searchResult)?DUMMY_LIST:searchResult;
	}

	@Override
	public List<EntityType> search(Object searchParam) {
		return performSearch(searchParam);
	}

	protected EntityType getOptional(Optional<EntityType> optional) {
		return optional.orElse(null);
	}
}