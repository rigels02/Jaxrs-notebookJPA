package org.rb.notebook.controller;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rb.notebook.json.model.NotesBookJ;
import org.rb.notebook.model.Header;
import org.rb.notebook.model.Note;
import org.rb.notebook.model.NotesBook;
import org.rb.notebook.service.NoteBookDataAccess;
import org.rb.notebook.service.TaskExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author raitis
 */
@RestController
@RequestMapping("/notesbook")
//@Scope(value="request")
//@CrossOrigin(origins = "*",allowedHeaders = "*")
public class NotesBookController {
    
    @Autowired
    NoteBookDataAccess noteBookDA;
    
    @Autowired
    TaskExecutorService myServ;
    
     @RequestMapping(path = "hi",method = RequestMethod.GET)
     public ResponseEntity<String> handShake(){
       return new ResponseEntity<>("NotesBookController31082017" , HttpStatus.OK);
     }
     
    @RequestMapping(path = "headers",method = RequestMethod.GET)
    public ResponseEntity<List<Header>> getHeaders() {
        
        List<Header> headers = noteBookDA.getHlist();
        
        return new ResponseEntity<>(headers , HttpStatus.OK);
    }
    
    
    /**
     * Get NoteBook with name 'root'.
     * @return 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<NotesBook> getNotesBook(){
    
            List<NotesBook> books;
        try {
            books = noteBookDA.findAll(NotesBook.class);
        } catch (Exception ex) {
            Logger.getLogger(NotesBookController.class.getName()).log(Level.SEVERE, null, ex);
             return new ResponseEntity<>(new NotesBook(),HttpStatus.NO_CONTENT);
        }
        NotesBook owerBook = null;
       // NotesBookJ bookJson= null;
        for (NotesBook book : books) {
            if(book.getTitle().equals("root")){
              owerBook= book;
              //bookJson = new NotesBookJ();
              //bookJson.copyNotesBookJ(book);
             
              break;
            }
        }
        if(owerBook==null){
            return new ResponseEntity<>(new NotesBook() , HttpStatus.NO_CONTENT);
        }
        System.out.println("--- "+Thread.currentThread().getName()+":"+Thread.currentThread().getId());
        
        //if(bookJson!=null)
        //       myServ.saveNotesBookAsJson(bookJson);
        return new ResponseEntity<>(owerBook , HttpStatus.OK);
    
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> postBook(@RequestBody NotesBook book) {
        List<Header> headers = noteBookDA.getHlist();
        for (Header header : headers) {
           if(header.getTitle().equals("root")){
               try {
                   noteBookDA.remove(header.getId());
                   break;
               } catch (Exception ex) {
                   Logger.getLogger(NotesBookController.class.getName()).log(Level.SEVERE, null, ex);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
               }
           }
        }
        book.setTitle("root");
        try {
            noteBookDA.persist(book);
        } catch (Exception ex) {
            Logger.getLogger(NotesBookController.class.getName()).log(Level.SEVERE, null, ex);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        /**
         * Save posted notes in file as Json formated data.
         * It will be done by task executor and separate thread.
         **/
        NotesBookJ bookJson= new NotesBookJ();
        bookJson.copyNotesBookJ(book);
        myServ.saveNotesBookAsJson(bookJson);
        //---------//
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> remove() {
        List<Header> headers = noteBookDA.getHlist();
        for (Header header : headers) {
            if(header.getTitle().equals("root")){
                try {
                    noteBookDA.remove(header.getId());
                    break;
                } catch (Exception ex) {
                    Logger.getLogger(NotesBookController.class.getName()).log(Level.SEVERE, null, ex);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
