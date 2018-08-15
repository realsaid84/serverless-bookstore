package bookstore.training.net.service;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author Repository enables the support for retrieving and manipulating authors in the database<br>
 * @author slasisi
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	
}