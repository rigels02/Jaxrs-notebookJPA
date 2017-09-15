
package org.rb.notebook.json.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import org.rb.notebook.model.Note;

/**
 *
 * @author raitis
 */

public class NoteJ implements Serializable{
   
    @JsonIgnore
    private long id;
    
    private String title;
    private String note;
    private Date modTime;

   

    public NoteJ() {
       
        title="";
        note="";
    }

    public NoteJ(String title,Date modTime,String content) {
        this.title=title;
        this.modTime= modTime;
        this.note = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    
    public String getNote() {
        return note;
    }

    public void setNote(String content) {
        this.note = content;
    }


    public void copyNoteJ(Note other){
     this.id= other.getId();
     this.modTime= other.getModTime();
     this.note= other.getNote();
     this.title= other.getTitle();
    }
    
    @Override
    public String toString() {
        return "Note{" + "title=" + title + ", modTime=" + modTime + ", content=" + note + '}';
    }

    
}
