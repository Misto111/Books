package bg.home.books.service;

import bg.home.books.model.entity.dto.BookDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDTO> getAllBooks();

    Optional<BookDTO> findBookById(Long id);

    void deleteBookById(Long id);

    Long createBook(BookDTO bookDTO);
}
