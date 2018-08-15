package bookstore.training.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController that exposes different Rest CRUD end-points as part of this Book WebService
 * @author slasisi
 */
@RestController
public class BooksController {

    private static Logger logger = LoggerFactory.getLogger(BooksController.class);
    
    @Autowired BookService bookService;

    
    /**
     * Get Books 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getHomeBooks() {
        logger.info("Get Some books");
        return new ResponseEntity<>("BookStore MicroServices :"
        		+ "<br><a href=\"./books\">[Get All Books]</a>"
        		+ "<br><a href=\"./books/55\">[Get Book By Id]"
        		+ "<br><a href=\"./books/search?q=Title19\">[Search for Books with a Search Term: Title19]"
        		+ "<br>[Update Books]"
        		+ "<br>[Patch Books]"
        		+ "<br>[Create Books]", HttpStatus.OK);
    }
    
    /**
     * Get Books 
     * @return
     */
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public ResponseEntity<?> getBooks() {
        logger.info("Get all books");
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }
    
    /**
     * Search Books by search term q
     * @param searchTerm
     * @return
     */
    @RequestMapping(value = "/books/search", method = RequestMethod.GET)
    public ResponseEntity<?> searchBooks(@RequestParam("q") String searchTerm) {
        logger.info("Searching for Books based on queryterm ", searchTerm);
        return new ResponseEntity<>(bookService.search(searchTerm), HttpStatus.OK);
    }
    
    /**
     * Get Books by BookId
     * @param bookId
     * @return
     */
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET)
    public ResponseEntity<?> getBooksById(@PathVariable("bookId") String bookId) {
        logger.info("Fetching bookId for {}", bookId);
        Book foundBook = bookService.getBook(bookId);
        if(foundBook != null) {
        		return new ResponseEntity<>(foundBook, HttpStatus.OK);
        }
        return new ResponseEntity<>(bookId, HttpStatus.NOT_FOUND);
    }
    
    /**
     * Creates Books by bookId
     * @param bookId
     * @return
     */
    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        logger.info("Creating Book {}", book.getTitle());
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.OK);
    }
    
    /**
     * Deletes Books by bookId
     * @param bookId
     * @return
     */
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") String bookId) {
        logger.info("Deleting Book {}", bookId);
        bookService.deleteBook(bookId);
        return new ResponseEntity<>("Deleted:"+bookId, HttpStatus.OK);
    }
    
    /**
     * Patches Books by BookId and RequestBody to be patched
     * @param bookId
     * @return
     */
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchBook(@PathVariable("bookId") String bookId, @RequestBody Book book) {
        Book existingBook = bookService.getDeeplyNestedBook(bookId);
        copyPatchProperties(book, existingBook);
        return updateBook(bookId, existingBook);
    }
    
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.PUT)
    public ResponseEntity<?> putBook(@PathVariable("bookId") String bookId, @RequestBody Book book) {
        logger.info("Updating Book {}", bookId);
        book.setBookId(Long.valueOf(bookId));
        return updateBook(bookId, book);
    }

    /**
     * Copies patch properties from patchedBook to ExistingBook
     * @param book
     * @param existingBook
     */
	private void copyPatchProperties(Book book, Book existingBook) {
		if(book.getPrice() != null) {
        		existingBook.setPrice(book.getPrice());
        }
        if(book.getPublisher() != null) {
    			existingBook.setPublisher(book.getPublisher());
    		}
        if(book.getTitle() != null) {
			existingBook.setTitle(book.getTitle());
		}
        if(book.getYear() != null) {
			existingBook.setYear(book.getYear());
		}
        if(book.getAuthors() != null && !book.getAuthors().isEmpty()) {
			existingBook.setAuthors(book.getAuthors());
		}
	}

	private ResponseEntity<?> updateBook(String bookId, Book book) {
		Book updatedBook = bookService.updateBook(book);
        if(updatedBook != null) {
        		return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }
        else {
        		return new ResponseEntity<>(bookId, HttpStatus.NOT_FOUND);
        }
	}
    
}
