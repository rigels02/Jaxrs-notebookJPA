
package org.rb.notebook.repository;

import java.io.Serializable;
import org.rb.notebook.model.NotesBook;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author raitis
 */
public interface  INoteBookRepo extends CrudRepository<NotesBook, Long>{
    
}
