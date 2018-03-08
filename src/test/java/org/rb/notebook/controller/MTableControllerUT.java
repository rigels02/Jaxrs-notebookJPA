
package org.rb.notebook.controller;

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
import org.junit.runner.RunWith;
import org.rb.notebook.model.tables.MTable;
import org.rb.notebook.model.tables.TableData;
import org.rb.notebook.model.tables.TableInfo;
import org.rb.notebook.repository.IMtableJpaRepo;
import org.rb.notebook.service.MTableDataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

/**
 *
 * ********************************************
 * TO TEST RUN SPRING APPLICATION FIRST !!!
 * ********************************************
 * @author raitis
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MTableControllerUT {
    
    @Autowired
    private IMtableJpaRepo repo;
    
    @Autowired
    private MTableDataAccess tableDA;
    
    
    @Before
    public void before(){
        repo.deleteAll();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    
    @After
    public void tearDown() {
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
     * Test of handShake method, of class MTableController.
     */
    @Test
    public void testHandShake() {
        System.out.println("handShake");
        MTableController instance = new MTableController();
        ResponseEntity<String> expResult = null;
        ResponseEntity<String> result = instance.handShake();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTableInfoLst method, of class MTableController.
     */
    @Test
    public void testGetTableInfoLst() {
        System.out.println("getTableInfoLst");
        MTableController instance = new MTableController();
        ResponseEntity<List<TableInfo>> expResult = null;
        ResponseEntity<List<TableInfo>> result = instance.getTableInfoLst();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMTablesAll method, of class MTableController.
     */
    @Test
    public void testGetMTablesAll() {
        System.out.println("getMTablesAll");
        MTableController instance = new MTableController();
        ResponseEntity<List<MTable>> expResult = null;
        ResponseEntity<List<MTable>> result = instance.getMTablesAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMTableById method, of class MTableController.
     */
    @Test
    public void testGetMTableById() {
        System.out.println("getMTableById");
        long id = 0L;
        MTableController instance = new MTableController();
        ResponseEntity<MTable> expResult = null;
        ResponseEntity<MTable> result = instance.getMTableById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of postTable method, of class MTableController.
     */
    @Test
    public void testPostTable() {
        System.out.println("postTable");
        MTable table = null;
        BindingResult bindResult = null;
        MTableController instance = new MTableController();
        ResponseEntity<MTable> expResult = null;
        ResponseEntity<MTable> result = instance.postTable(table, bindResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putTableById method, of class MTableController.
     */
    @Test
    public void testPutTableById() {
        System.out.println("putTableById");
        long id = 0L;
        MTable table = null;
        BindingResult bindResult = null;
        MTableController instance = new MTableController();
        ResponseEntity<MTable> expResult = null;
        ResponseEntity<MTable> result = instance.putTableById(id, table, bindResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteMTableById method, of class MTableController.
     */
    @Test
    public void testDeleteMTableById() {
        System.out.println("deleteMTableById");
        long id = 0L;
        MTableController instance = new MTableController();
        ResponseEntity<HttpStatus> expResult = null;
        ResponseEntity<HttpStatus> result = instance.deleteMTableById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteAll method, of class MTableController.
     */
    @Test
    public void testDeleteAll() {
        System.out.println("deleteAll");
        MTableController instance = new MTableController();
        ResponseEntity<HttpStatus> expResult = null;
        ResponseEntity<HttpStatus> result = instance.deleteAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
