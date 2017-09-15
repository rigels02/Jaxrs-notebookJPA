
package org.rb.notebook.model.tables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.Valid;


/**
 * Created on 27-Feb-17.
 * @author raitis
 */
@Entity
@NamedQueries(
        {
    @NamedQuery(name = "MTable.findAll", query = "select p from MTable p"),
    @NamedQuery(name = "MTable.findAllById", query = "select p from MTable p where p.id = :mid")
        
}
)
public class MTable extends TableInfo implements Serializable{
    
  
    
    //@ElementCollection
    @OneToMany(cascade = CascadeType.ALL /*, fetch = FetchType.EAGER*/)
    @Valid
    private List<TableData> data= new ArrayList<>();

    public MTable() {
    }

   

    public List<TableData> getData() {
        return data;
    }

    public void setData(List<TableData> data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long Id) {
        this.id = Id;
    }

    @Override
    public String toString() {
        return "MTable{"+super.toString()+", data=" + data + '}';
    }

    /**
     * copy all fields from other except Id
     * @param other 
     */
    public void copyFieldsNotId(MTable other){
     super.setName(other.getName());
     super.setModTime(other.getModTime());
     this.setData(other.getData());
    }
   
    
}
