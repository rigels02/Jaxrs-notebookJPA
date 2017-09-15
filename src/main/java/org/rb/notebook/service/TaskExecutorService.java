
package org.rb.notebook.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rb.notebook.json.model.NotesBookJ;
import org.rb.notebook.model.NotesBook;
import org.springframework.core.task.TaskExecutor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * run long tasks by TaskExecutor.
 * Here the ThreadPoolTaskExecutor is used by this application.
 * @author raitis
 */
@Service
public class TaskExecutorService {

private class MessagePrinterTask implements Runnable {

        private String message;

        public MessagePrinterTask(String message) {
            this.message = message+" : "+Thread.currentThread().getName()
                    +":"+Thread.currentThread().getId();
        }

        public void run() {
            System.out.println(message);
        }

    }

    private TaskExecutor taskExecutor;

    public TaskExecutorService(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Async
    public void printMessages() {
        for(int i = 0; i < 25; i++) {
            taskExecutor.execute(new MessagePrinterTask("Message" + i));
        }
    }

    
    public void saveNotesBookAsJson(NotesBookJ book){
     taskExecutor.execute(new SaveNotesBookAsJson(book));
    }
    
    
    private class SaveNotesBookAsJson implements Runnable{

        NotesBookJ book;
        DateFormatter df;
        
        public SaveNotesBookAsJson(NotesBookJ book) {
            this.book = book;
             df = new DateFormatter("dd-MM-yyyy_HH-mm-ss");
        }
        
        @Override
        public void run() {
            if (book == null) {
                return;
            }
            NotesBookJ bookjson = this.book;
            
            System.out.println(""+Thread.currentThread().getName()
                                +":"+Thread.currentThread().getId());
            ObjectMapper mapper = new ObjectMapper();
            try {
                String target = String.format("notesBookJsonRoot%s.txt", df.print(new Date(), Locale.ENGLISH));
                mapper.writeValue(new File(target), bookjson);
            } catch (IOException ex) {
                Logger.getLogger(TaskExecutorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
