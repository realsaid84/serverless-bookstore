package bookstore.training.net.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A Dictionary Service to lazy load books  at Application startup. <br>
 * It also provides mechanisms for basic Create, Read and Delete operations of a BookStore.
 * @see #init()
 * @author slasisi
 */
@Service
public class BookService {



	private static Logger logger = LoggerFactory.getLogger(BookService.class);


	@Autowired
	private BookRepository bookRepository;
	


	public BookService() {
	}

	/**
	 * Method called after DictionaryMap Bean Initialisation
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	public void init() throws IOException {
		logger.debug("Initializing Book DB Post Construct with dummy Data");
		List<Book> books = new ArrayList<>();
		
		for(int i=1 ; i <= 100; i++) {
			Book book = new Book();
			book.setPrice(5.0 * i);
			book.setPublisher("Bookstore");
			book.setTitle("Title"+i);
			book.setYear("190"+i);
			book.setAuthors(new ArrayList<>());
			Author author1 = new Author();
			author1.setName("Lambeth North"+i);
			author1.setPlaceOfBirth("EC"+i);
			author1.setBook1(book);
			book.getAuthors().add(author1);
			books.add(book);
		}
		bookRepository.saveAll(books);

	}
	
	public List<Book> getAllBooks(){
		
		List<Book> allBooks = bookRepository.findAll();
		if(allBooks != null && !allBooks.isEmpty()) {
			allBooks.forEach(book ->{
				nullifyNestedBooks(book);
			});
		}
		return allBooks;
	}

	private void nullifyNestedBooks(Book book) {
		if(book != null && book.getAuthors() != null 
				&& !book.getAuthors().isEmpty()) {
			book.getAuthors().forEach(author -> author.setBook1(null));
		}
	}
	
	public Book getBook(String bookId) {
		logger.info("Retrieving Book {} from DB", bookId);
		Book book = bookRepository.findByBookId(Long.valueOf(bookId));
		nullifyNestedBooks(book);
		return book;
	}
	
	public Book getDeeplyNestedBook(String bookId) {
		logger.info("Retrieving Book {} from DB", bookId);
		Book book = bookRepository.findByBookId(Long.valueOf(bookId));
		return book;
	}

	public Book createBook(Book book) {
		List<Book> savedBookEntity = bookRepository.saveAll(convertBookRequest(book));
		if(savedBookEntity != null && !savedBookEntity.isEmpty()) {
			savedBookEntity.forEach(this::nullifyNestedBooks);
			return savedBookEntity.get(0);
		}
		return null;
	}

	private List<Book> convertBookRequest(Book book) {
		Book bookToSave = convertBook(book);
		logger.info("Saving book {} to database", book.getTitle());
		List<Book> books = new ArrayList<>();
		if(book.getAuthors() != null && ! book.getAuthors().isEmpty()) {
			List<Author> authorList =  new ArrayList<>();
			for(Author author : book.getAuthors()) {
				Author convertedAuthor = new Author();
				convertedAuthor.setBook1(bookToSave);
				convertedAuthor.setDateOfBirth(author.getDateOfBirth());
				convertedAuthor.setDateOfDeath(author.getDateOfDeath());
				convertedAuthor.setName(author.getName());
				convertedAuthor.setPlaceOfBirth(author.getPlaceOfBirth());
				convertedAuthor.setPlaceOfDeath(author.getPlaceOfDeath());
				authorList.add(convertedAuthor);
			}
			bookToSave.setAuthors(authorList);
		}
		books.add(bookToSave);
		return books;
	}

	private Book convertBook(Book book) {
		Book convertedBook = new Book();
		convertedBook.setTitle(book.getTitle());
		convertedBook.setPublisher(book.getPublisher());
		convertedBook.setYear(book.getYear());
		convertedBook.setPrice(book.getPrice());
		return convertedBook;
	}

	public void deleteBook(String bookId) {
		logger.info("Deleting {} from database", bookId);
		Book bookToBeDeleted = bookRepository.findByBookId(Long.valueOf(bookId));
		if(bookToBeDeleted != null) {
			bookRepository.delete(bookToBeDeleted);
		}
		else {
			logger.info("Book {} doesn't exist in database", bookId);
		}
		
	}

	public Book updateBook(Book book) {
		Optional<Book> bookToBeUpdated = bookRepository.findById(book.getBookId());
		if(bookToBeUpdated.isPresent()) {
			logger.info("Updating Book {} ", book.getBookId());
			
			Book updatedBook = bookRepository.save(book);
			nullifyNestedBooks(updatedBook);
			return updatedBook;
		}
		logger.info("Book {} can't be found in database", book.getBookId());
		return null;
	}

	
	public List<Book> search(String searchTerm) {
		logger.info("Finding Book by searchterm{} ", searchTerm);
		List<Book> foundBooks = bookRepository.search(searchTerm);
		if(foundBooks != null && !foundBooks.isEmpty()) {
			foundBooks.forEach(this::nullifyNestedBooks);
		}
		return bookRepository.search(searchTerm);
	}

}
