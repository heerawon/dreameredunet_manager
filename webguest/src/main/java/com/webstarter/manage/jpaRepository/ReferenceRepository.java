package com.webstarter.manage.jpaRepository;

import com.webstarter.manage.jpaEntity.ReferenceVO;
import com.webstarter.manage.jpaEntity.ReferenceListVO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReferenceRepository extends JpaRepository<ReferenceVO, Long>{
    public List<ReferenceListVO> findByIsDel(Long isDel, Sort sort);
    public Optional<ReferenceVO> findByIdAndIsDel(Long id, Long isDel);
}
