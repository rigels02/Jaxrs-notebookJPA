package org.rb.notebook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rb.notebook.model.Header;
import org.rb.notebook.model.Note;
import org.rb.notebook.repository.INoteBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ********************************************
 * TO TEST RUN SPRING APPLICATION FIRST !!!
 * ********************************************
 * @author raitis
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotebookApplicationUT {

    
    
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private INoteBookRepo repo;
    
    @Before
    public void before(){
        repo.deleteAll();
    }
    
    private boolean isNoteListEQ(Note[] notes1, Note[] notes2){
        if(notes1.length != notes2.length) return false;
        for(int i=0; i<  notes1.length; i++){
            Note note1 = notes1[i];
            Note note2 = notes1[i];
            if( !note1.getTitle().equals(note2.getTitle())
                || !note1.getModTime().equals(note2.getModTime())
                || !note1.getNote().equals(note2.getNote())
                    )
                return false;
        }
        return true;
    }
    
    @Test
    public void getNotes() {
        List<Note> notes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            notes.add(new Note("Note_" + i, new Date(), "Content_" + i));
        }
        Note[] anotes= new Note[notes.size()];
        notes.toArray(anotes);
        //header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Note>> requestEntity = new HttpEntity(notes,headers);
        //ResponseEntity<HttpStatus> response = this.restTemplate.postForEntity("/notes", notes, HttpStatus.class);
        ResponseEntity<HttpStatus> response = restTemplate.exchange("/notes", HttpMethod.POST, requestEntity, HttpStatus.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        // 2nd post - every post must delete old data///
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestEntity = new HttpEntity(notes,headers);
        //ResponseEntity<HttpStatus> response = this.restTemplate.postForEntity("/notes", notes, HttpStatus.class);
        response = restTemplate.exchange("/notes", HttpMethod.POST, requestEntity, HttpStatus.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        // 3d post  - every post must delete old data///
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestEntity = new HttpEntity(notes,headers);
        //ResponseEntity<HttpStatus> response = this.restTemplate.postForEntity("/notes", notes, HttpStatus.class);
        response = restTemplate.exchange("/notes", HttpMethod.POST, requestEntity, HttpStatus.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        //check - result should be equal as for 1st post
        ResponseEntity<Note[]> entity = this.restTemplate.getForEntity("/notes", Note[].class);

        int sz = entity.getBody().length;
        Note[] notes1 = entity.getBody();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(sz == 3);
        
        assertTrue(isNoteListEQ(notes1, anotes));
        
    }

    @Test
    public void testDelete(){
        List<Note> notes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            notes.add(new Note("Note_" + i, new Date(), "Content_" + i));
        }
        Note[] anotes= new Note[notes.size()];
        notes.toArray(anotes);
        //header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Note>> requestEntity = new HttpEntity(notes,headers);
        //ResponseEntity<HttpStatus> response = this.restTemplate.postForEntity("/notes", notes, HttpStatus.class);
        ResponseEntity<HttpStatus> response = restTemplate.exchange("/notes", HttpMethod.POST, requestEntity, HttpStatus.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        ResponseEntity<Note[]> entity = this.restTemplate.getForEntity("/notes", Note[].class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        assertTrue(entity.getBody().length== 3);
        response = restTemplate.exchange("/notes", HttpMethod.DELETE, null, HttpStatus.class);
         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
         
         entity = this.restTemplate.getForEntity("/notes", Note[].class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(entity.getBody().length==0);
    }
    
    @Test
    public void testGetHeaders(){
    List<Note> notes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            notes.add(new Note("Note_" + i, new Date(), "Content_" + i));
        }
        Note[] anotes= new Note[notes.size()];
        notes.toArray(anotes);
        
        //header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Note>> requestEntity = new HttpEntity(notes,headers);
        //ResponseEntity<HttpStatus> response = this.restTemplate.postForEntity("/notes", notes, HttpStatus.class);
        ResponseEntity<HttpStatus> response = restTemplate.exchange("/notes", HttpMethod.POST, requestEntity, HttpStatus.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ResponseEntity<Header[]> response1 = restTemplate.getForEntity("/notes/headers", Header[].class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(response1.getBody().length==1);
        assertTrue(response1.getBody()[0].getTitle().equals("root"));
        
    }
    
}   
