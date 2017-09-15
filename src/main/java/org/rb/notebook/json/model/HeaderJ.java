
package org.rb.notebook.json.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.rb.notebook.model.Header;

/**
 * V3
 * @author raitis
 */
public class HeaderJ implements Serializable{

    //unique id for header, user is not responsible
    @JsonIgnore
    private long id;
   
    private String title;
    
   //User is not responsible about modTime
    private Date modTime;    

    public HeaderJ() {
        this.title="";
        
    }

    public HeaderJ(long id, String title, Date modTime) {
        this.id = id;
        this.title = title;
        this.modTime = modTime;
    }

    public HeaderJ(long id, String title) {
        this.id = id;
        this.title = title;
       
    }
    public HeaderJ(String title) {
      this.title = title;
       
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

    public void copyHeader(Header other){
     this.id = other.getId();
     this.title= other.getTitle();
     this.modTime= other.getModTime();
    }
    
   
    @Override
    public String toString() {
        return "Header{" + "id=" + id +", title=" + title + ", modTime=" + modTime + '}';
    }

    
    
}
