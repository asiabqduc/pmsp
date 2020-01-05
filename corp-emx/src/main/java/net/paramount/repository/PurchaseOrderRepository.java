package net.paramount.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.paramount.entity.emx.PurchaseOrder;
import net.paramount.framework.repository.BaseRepository;

@Repository
public interface PurchaseOrderRepository extends BaseRepository<PurchaseOrder, Long> {
	Optional<PurchaseOrder> findByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.info) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<PurchaseOrder> search(@Param("keyword") String keyword, Pageable pageable);
}
