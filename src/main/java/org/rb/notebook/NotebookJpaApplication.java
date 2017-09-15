package org.rb.notebook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.rb.notebook.model.Note;
import org.rb.notebook.model.NotesBook;
import org.rb.notebook.repository.INoteBookRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class NotebookJpaApplication {
    private static final Logger log= LoggerFactory.getLogger(NotebookJpaApplication.class);
    
	public static void main(String[] args) {
		SpringApplication.run(NotebookJpaApplication.class, args);
	}
        
       // @Bean
	public CommandLineRunner demo(INoteBookRepo crudRepo){

		return args -> {
			log.info("Save some Notes .....");
                        
                    List<Note> notes = new ArrayList<>();
                    for(int i=1; i<= 3; i++){
                     notes.add(new Note("Āboli_"+i,new Date(),"Dāži_"+i));
                    }
                    NotesBook book = new NotesBook("root");
                    book.setModTime(new Date());
                    
                    book.setNotes(notes);
                    
                    crudRepo.save(book);
		};
	}
        
        @Bean
        public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }
}
