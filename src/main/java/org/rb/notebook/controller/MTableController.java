package org.rb.notebook.controller;

import java.util.List;
import javax.validation.Valid;
import org.rb.notebook.model.tables.MTable;
import org.rb.notebook.model.tables.TableInfo;

import org.rb.notebook.service.MTableDataAccess;
import org.rb.notebook.service.TaskExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author raitis
 */
@RestController
@RequestMapping("mtables")
public class MTableController {
    
    @Autowired
     MTableDataAccess tablesDA;
    
    @Autowired
    TaskExecutorService myServ;
    
     @RequestMapping(path = "hi",method = RequestMethod.GET)
     public ResponseEntity<String> handShake(){
       return new ResponseEntity<>("MTableController15092017" , HttpStatus.OK);
     }
 
     @RequestMapping(path = "info",method = RequestMethod.GET)
    public ResponseEntity<List<TableInfo>> getTableInfoLst() {
        
        List<TableInfo> ti = tablesDA.findTableInfoAll();
        
        return new ResponseEntity<>(ti , HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MTable>> getMTablesAll() {
        
        List<MTable> tables = tablesDA.findMTableAll();
        
        return new ResponseEntity<>(tables , HttpStatus.OK);
    }
    @RequestMapping(path="{id}" , method = RequestMethod.GET)
    public ResponseEntity<MTable> getMTableById(@PathVariable("id") long id) {
        
       return new ResponseEntity<>((MTable) tablesDA.findById(id), HttpStatus.OK);
    }
    
    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<MTable> postTable(
            @Valid MTable table,
             BindingResult bindResult) {
        
        if(bindResult.hasFieldErrors()){
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MTable stable = tablesDA.saveMTable(table);
       return new ResponseEntity<>(stable, HttpStatus.CREATED);
    }
    
    
    
    @RequestMapping(path="{id}" , method = RequestMethod.PUT)
    public ResponseEntity<MTable> putTableById(@PathVariable("id") long id, 
            @Valid MTable table,
            BindingResult bindResult) {
        
        if(bindResult.hasFieldErrors()){
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        MTable ntable = tablesDA.findById(id);
        if(ntable == null)
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        ntable.copyFieldsNotId(table);
        MTable result = tablesDA.saveMTable(ntable);
       return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    
    @RequestMapping(path="{id}" , method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteMTableById(@PathVariable("id") long id) {
        
       MTable ntable = tablesDA.findById(id);
        if(ntable == null)
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        
       tablesDA.delete(id);
       return new ResponseEntity<>( HttpStatus.OK);
    }
    
    @RequestMapping( method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteAll() {
        
       tablesDA.deleteAll();
       return new ResponseEntity<>( HttpStatus.OK);
    }
}
