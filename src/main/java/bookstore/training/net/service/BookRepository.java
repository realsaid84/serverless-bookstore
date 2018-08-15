package bookstore.training.net.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Book Repository enables the support for retrieving and manipulating Books in the database<br>
 * @author slasisi
 */
public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query("select b from Book b where b.bookId = :bookId")
	Book findByBookId(@Param("bookId")Long bookId);

	@Query("select b from Book b join b.authors a where"
			+ " b.title = :searchTerm or"
			+ " b.publisher = :searchTerm or"
			+ " a.name = :searchTerm or"
			+ " a.placeOfBirth = :searchTerm or"
			+ " a.placeOfDeath = :searchTerm")
	List<Book> search(@Param("searchTerm")String searchTerm);
	
}