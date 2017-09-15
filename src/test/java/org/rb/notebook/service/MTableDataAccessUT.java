
package org.rb.notebook.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.rb.notebook.model.tables.MTable;
import org.rb.notebook.model.tables.TableData;
import org.rb.notebook.model.tables.TableInfo;
import org.rb.notebook.repository.IMtableJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author raitis
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MTableDataAccessUT {
    
    
    @Autowired
    private IMtableJpaRepo repo;
    
    @Autowired
    private MTableDataAccess tableDA;
    
    
    @Before
    public void before(){
        repo.deleteAll();
    }

    private List<MTable> makeTables(int v){
        List<MTable> mtables = new ArrayList<>();
        
        for (int i = 1; i <= 3; i++) {
            MTable table = new MTable();
            //table.setId(i);
            table.setName("Table_"+i);
            table.setModTime(new Date());
            if(i != v){
            table.getData().add(new TableData(new GregorianCalendar(2017,1,10).getTime(),
                    "Table_" + i + "_Cat1", 10.0, "Note_1"));
            table.getData().add(new TableData(new GregorianCalendar(2017,2,20).getTime(), 
                    "Table_" + i + "_Cat2", 20.0, "Note_2"));
            table.getData().add(new TableData(new GregorianCalendar(2017,3,21).getTime(), 
                    "Table_" + i + "_Cat3", 30.0, "Note_3"));
            }
            mtables.add(table);
        }
        return mtables;
}
    
    private void ValidateTables(MTable mTable, MTable table) {
        Calendar calEx = Calendar.getInstance();
        Calendar calAct = Calendar.getInstance();
        
        assertEquals(mTable.getName(), table.getName());
        int sz = mTable.getData().size();
        for(int i = 0; i< sz;i++){
        assertEquals(mTable.getData().get(i).getCat(), table.getData().get(i).getCat());
        assertEquals(mTable.getData().get(i).getAmount(), table.getData().get(i).getAmount());
        assertEquals(mTable.getData().get(i).getNote(), table.getData().get(i).getNote());
        
        calEx.setTime(mTable.getData().get(i).getCdate());
        calAct.setTime(table.getData().get(i).getCdate());
        assertEquals(calEx.get(Calendar.DAY_OF_MONTH), calAct.get(Calendar.DAY_OF_MONTH));
        assertEquals(calEx.get(Calendar.MONTH), calAct.get(Calendar.MONTH));
        assertEquals(calEx.get(Calendar.YEAR), calAct.get(Calendar.YEAR));
        }
    }
    
    /**
     * Test of findTableInfoAll method, of class MTableDataAccess.
     */
    @Test
    public void testFindTableInfoAll() {
        System.out.println("findTableInfoAll");
        List<MTable> tables = makeTables(-1);
        //Given
        Iterable<MTable> oti = repo.save(tables);
        //when
        List<TableInfo> ti = tableDA.findTableInfoAll();
        //then
        ti.forEach(t-> System.out.println("t->"+t.toString()));
        assertTrue(ti.size()==3);
        assertTrue(ti.get(0).getName().equals(tables.get(0).getName()));
        assertTrue(ti.get(1).getName().equals(tables.get(1).getName()));
        assertTrue(ti.get(2).getName().equals(tables.get(2).getName()));
    }

    /**
     * Test of findMTableAll method, of class MTableDataAccess.
     */
    @Test
    public void testFindMTableAll() {
        System.out.println("findMTableAll");
        List<MTable> tables = makeTables(-1);
        //Given
        Iterable<MTable> oti = repo.save(tables);
        //when
        List<MTable> tables1 = tableDA.findMTableAll();
        //then
        tables1.forEach(t-> System.out.println("t->"+t.toString()));
        assertTrue(tables1.size()==3);
        ValidateTables(tables.get(0), tables1.get(0));
         ValidateTables(tables.get(1), tables1.get(1));
          ValidateTables(tables.get(2), tables1.get(2));
    }

    /**
     * Test of findById method, of class MTableDataAccess.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        List<MTable> tables = makeTables(-1);
        //Given
        Iterable<MTable> oti = repo.save(tables);
        
        //when
        for (MTable mTable : oti) {
            MTable retTable = tableDA.findById(mTable.getId());
            //then
            ValidateTables(retTable, mTable);
        }
       
        
    }

    /**
     * Test of findByName method, of class MTableDataAccess.
     */
    @Test
    public void testFindByName() {
        System.out.println("findByName");
        List<MTable> tables = makeTables(-1);
        //Given
        Iterable<MTable> oti = repo.save(tables);
        
        //when
        for (MTable mTable : oti) {
            MTable retTable = tableDA.findByName(mTable.getName());
            //then
            ValidateTables(retTable, mTable);
        }
    }

    /**
     * Test of saveMTable method, of class MTableDataAccess.
     */
    @Test
    public void testSaveMTable() {
        System.out.println("saveMTable");
         //Given tables
        List<MTable> tables = makeTables(-1);
        //when save table 2
        MTable sTable = tableDA.saveMTable(tables.get(2));
        
        //then
        assertTrue(tableDA.findMTableAll().size()==1);
        ValidateTables(sTable,tables.get(2));
   
    }
    

    /**
     * Test of exists method, of class MTableDataAccess.
     */
    @Test
    public void testExists() {
        System.out.println("exists");
        List<MTable> tables = makeTables(-1);
        //Given
        Iterable<MTable> oti = repo.save(tables);
        
        //when
        for (MTable mTable : oti) {
            //then
          
            assertTrue(tableDA.exists(mTable.getId()));
          
        }
    }

    /**
     * Test of delete method, of class MTableDataAccess.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        
        List<MTable> tables = makeTables(-1);
        //Given
        Iterable<MTable> oti = repo.save(tables);
        assertTrue(tableDA.findMTableAll().size()==3);
        
        //when
        for (MTable mTable : oti) {
           
          
          tableDA.delete(mTable.getId());
          
        }
         //then
         assertTrue(tableDA.findMTableAll().isEmpty());
    }

    /**
     * Test of deleteAll method, of class MTableDataAccess.
     */
    @Test
    public void testDeleteAll() {
        System.out.println("deleteAll");
       List<MTable> tables = makeTables(-1);
        //Given
        Iterable<MTable> oti = repo.save(tables);
        assertTrue(tableDA.findMTableAll().size()==3);
        
        //when
          
          tableDA.deleteAll();
         //then
         assertTrue(tableDA.findMTableAll().isEmpty()); 
    }
    
}
