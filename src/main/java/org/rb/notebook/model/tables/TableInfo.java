package org.rb.notebook.model.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *Created on 15-Sep-17.
 * V 2.0
 * Changes:
 *  selected removed out
 *  column "name" is required to have unique values
 * @author raitis
 */
@Entity
@Table(name = "MTable")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries(
        {
    @NamedQuery(name = "TableInfo.findAll", query = "select p from TableInfo p"),
    @NamedQuery(name = "TableInfo.findAllById", query = "select p from TableInfo p where p.id = :id")
        
}
)
public class TableInfo implements Serializable{

     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)        
    long id;

@NotBlank
@Column(unique = true)
private String name;

@Temporal(TemporalType.TIMESTAMP)
@NotNull
private Date modTime;
/**
 * 15Sep2017 removed.
 */
// private boolean selected;

    public TableInfo() {
    }

    public TableInfo(String name, Date modTime) {
        this.name = name;
        this.modTime = modTime;
    }

    public TableInfo(TableInfo other){
     this.id=other.getId();
     this.name= other.getName();
     this.modTime= other.getModTime();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    

    /***
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    ****/
    
    @Override
    public String toString() {
        return "TableInfo{name=" + name + ", modTime=" + modTime + '}';
    }

    



}
