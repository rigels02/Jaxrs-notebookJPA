
package org.rb.notebook.json.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.rb.notebook.model.Note;
import org.rb.notebook.model.NotesBook;

/**
 *
 * @author raitis
 */
public class JsonJUT {
    
    public JsonJUT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    private List<Note> createNotes(){
    List<Note> notes =  new ArrayList<>();
    //Calendar.getInstance().set(2017, 3, 23);
        
    for(int i=1; i<= 3; i++){
        Note note = new Note("Note_"+i, new GregorianCalendar(2017, 3+i, 23).getTime(), "Content_"+i);
        notes.add(note);
    }
    return notes;
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testCopy() {
        
        NotesBook book1 = new NotesBook(createNotes(), "Book_1");
        book1.setModTime(new GregorianCalendar().getTime());
        System.out.println("Notes: "+book1.toString());
        NotesBookJ book2= new NotesBookJ();
        book2.copyNotesBookJ(book1);
        System.out.println("NotesJson: "+book2.toString());
        assertEquals(book1.toString(), book2.toString());
    }
}
