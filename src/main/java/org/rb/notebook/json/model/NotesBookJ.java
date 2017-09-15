
package org.rb.notebook.json.model;

import java.util.ArrayList;
import java.util.List;
import org.rb.notebook.model.Note;
import org.rb.notebook.model.NotesBook;

/**
 * NotesBook for Json format in file
 * Json format does not contains all fields (not Id fields).
 * These fields are marked as @JsonIgnore. In the meantime,
 * these fields are needed in restful Json response.
 * Therefore, separate classes defined for Json data in file.
 * @author raitis
 */

public class NotesBookJ extends HeaderJ {
    
    private List<NoteJ> notes;

    public NotesBookJ() {
        super();
        notes= new ArrayList<>();
                
    }

    public NotesBookJ(String title) {
       super(title);
       notes= new ArrayList<>();
    }

    public NotesBookJ(List<NoteJ> notes,String title) {
        super(title);
        this.notes = notes;
    }

    public List<NoteJ> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteJ> notes) {
        this.notes = notes;
    }

    public void copyNotesBookJ(NotesBook other){
     super.copyHeader(other);
        for (Note onote : other.getNotes()) {
         NoteJ tnote = new NoteJ();
         tnote.copyNoteJ(onote);
            this.notes.add(tnote);
        }
    }
    
    @Override
    public String toString() {
        return "NotesBook{"+super.toString() + "notes=" + notes + '}';
    }
    
}
