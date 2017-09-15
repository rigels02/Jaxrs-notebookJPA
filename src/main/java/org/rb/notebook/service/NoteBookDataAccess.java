package org.rb.notebook.service;

import java.util.ArrayList;
import java.util.List;
import org.rb.notebook.model.Header;
import org.rb.notebook.model.NotesBook;
import org.rb.notebook.repository.INoteBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author raitis
 */
@Service
//@Scope(value="prototype")
public class NoteBookDataAccess implements IRWFile<NotesBook>{
    
     @Autowired
    //IRWFile<NotesBook> repo;
     INoteBookRepo repo;

    @Override
    public NotesBook find(long id, Class<NotesBook> cl) throws Exception {
       //return repo.find(id, cl);
         Iterable<NotesBook> books = repo.findAll();
         for (NotesBook book : books) {
            if(book.getId()==id){
              return book;
            }
        }
        return null; 
    }

    
    
    @Override
    public List<NotesBook> findAll(Class<NotesBook> cl) throws Exception {
        
         Iterable<NotesBook> books = repo.findAll();
         List<NotesBook> noteBooks= new ArrayList<>();
         for (NotesBook book : books) {
            noteBooks.add(book);
        }
         return noteBooks;
    }

    @Override
    public List<Header> getHlist() {
      List<Header> headers= new ArrayList<>();
      Iterable<NotesBook> books =  repo.findAll();
      for (NotesBook book : books) {
          Header header = new Header();
          header.copyHeader(book);
            headers.add(header);
        }
      return headers;
    }

    @Override
    public void persist(NotesBook data) throws Exception {
        repo.save(data);
       
    }

    
    @Override
    public  void persistAll(List<NotesBook> data) throws Exception {
        
      repo.save(data);
     
    }

    @Override
    public  NotesBook edit(NotesBook data) throws Exception {
     return repo.save(data);
    }

    @Override
    public  boolean remove(NotesBook data) throws Exception {
        repo.delete(data);
        return true;
    }

    @Override
    public  boolean remove(long id) throws Exception {
        repo.delete(id);
        return true;
    }
     
     
}
