package bookstore.training.net.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;
    

    @Test
    public void noParamShouldReturnDefaultMessage() throws Exception {

        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk());
               
    }

    @Test
    public void paramsShouldReturnABook() throws Exception {
    ContentResultMatchers matcher = MockMvcResultMatchers.content();
        this.mockMvc.perform(get("/books/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(matcher.contentType(MediaType.APPLICATION_JSON_UTF8));
    }
    
    @Test
    public void requestBodyShouldAddBook() throws Exception {
        this.mockMvc.perform(post("/books")
        							.contentType(MediaType.APPLICATION_JSON)
        							.content("{ \"id\": \"1\",\"title\" : \"New Book\"}"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Book")); 
    }
    
    @Test
    public void paramWordShouldDeleteBook() throws Exception {
    	ContentResultMatchers matcher = MockMvcResultMatchers.content();
        this.mockMvc.perform(delete("/books/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(matcher.string(("Deleted:1")));
    }
    
  

}
