
package org.rb.notebook.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.rb.notebook.model.tables.MTable;
import org.rb.notebook.model.tables.TableData;
import org.rb.notebook.model.tables.TableInfo;
import org.rb.notebook.repository.IMtableJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author raitis
 */
@Service
public class MTableDataAccess {
    
  
    @Autowired
      IMtableJpaRepo jparepo;
    
    public List<TableInfo> findTableInfoAll(){
    
        Iterable<MTable> tables = jparepo.findAllByOrderByIdAsc(); //findAll();
        List<TableInfo> tiLst= new ArrayList<>();
        for (MTable table : tables) {
            TableInfo ti = new TableInfo(table);
            tiLst.add(ti);
        }
        return tiLst;
    }
    
    private void sortTableDataById(List<TableData> data){
    data.sort(new Comparator<TableData>() {
        @Override
        public int compare(TableData o1, TableData o2) {
        if(o1.getId()==o2.getId()) return 0;
                  if(o1.getId()<o2.getId()) return -1;
                  return 1; 
        }
    });
    }
    
    @Transactional
    public List<MTable> findMTableAll(){
        Iterable<MTable> tables = jparepo.findAllByOrderByIdAsc(); //findAll();
        List<MTable> tables1= new ArrayList<>();
        
        for (MTable table : tables) {
            /**
             * get related data, works together with @Transactional
             * otherwise, lazy access exception occurs with no session.
             */
           table.getData().size();// get related data, works together with @Transactional
           
           sortTableDataById(table.getData());
           
           tables1.add(table);
        }
        return tables1;
    }
    /**
     * find MTable by id
     * @param id
     * @return Mtable or null if not found
     */
    @Transactional
    public MTable findById(long id){
        MTable mTable = jparepo.findOne(id);
        
        sortTableDataById(mTable.getData());
     return mTable;
    }
    
    @Transactional
    public MTable findByName(String name){
      MTable mTable = jparepo.findByName(name);
        sortTableDataById(mTable.getData());
     return mTable;
    }
    
    /**
     * 
     * @param table
     * @return saved MTable
     */
    public MTable saveMTable(MTable table){
      return jparepo.save(table);
    }
    public boolean exists(long id){
     return jparepo.exists(id);
    }
    public void delete(long id){
     jparepo.delete(id);
    }
    public void deleteAll(){
     jparepo.deleteAll();
    }
}
