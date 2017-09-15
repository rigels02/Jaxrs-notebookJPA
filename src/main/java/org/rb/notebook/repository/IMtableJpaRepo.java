package org.rb.notebook.repository;

import java.io.Serializable;
import java.util.List;
import org.rb.notebook.model.tables.MTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author raitis
 */
public interface IMtableJpaRepo extends JpaRepository<MTable, Long>{
    
    /**
     * 
     * @param name must be unique
     * @return 
     */
    MTable findByName(@Param("name") String name);
    
   public List<MTable> findAllByOrderByIdAsc();
    
}
