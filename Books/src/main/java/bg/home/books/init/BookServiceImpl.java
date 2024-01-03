package bg.home.books.init;

import bg.home.books.model.entity.AuthorEntity;
import bg.home.books.model.entity.BookEntity;
import bg.home.books.model.entity.dto.AuthorDTO;
import bg.home.books.model.entity.dto.BookDTO;
import bg.home.books.repository.AuthorRepository;
import bg.home.books.repository.BookRepository;
import bg.home.books.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
     return bookRepository
             .findAll()
             .stream()
             .map(BookServiceImpl::mapBookToDTO)
             .toList();
    }

    @Override
    public Optional<BookDTO> findBookById(Long id) {
      return  bookRepository
                .findById(id)
                .map(BookServiceImpl::mapBookToDTO);


    }

    private static BookDTO mapBookToDTO(BookEntity bookEntity) {                 //mapping метод, който превръща ентити е dto

        AuthorDTO authorDTO = new AuthorDTO().setName(bookEntity.getAuthor().getName());

        return new BookDTO()
                .setId(bookEntity.getId())
                .setAuthor(authorDTO)
                .setTitle(bookEntity.getTitle())
                .setIsbn(bookEntity.getIsbn());

    }

    @Override
    public void deleteBookById(Long id) {

        this.bookRepository.deleteById(id);

    }

    @Override
    public Long createBook(BookDTO bookDTO) {

        AuthorEntity author = authorRepository.findByName(bookDTO.getAuthor().getName()).orElse(null);

        if (author == null) {
            author = new AuthorEntity()
                    .setName(bookDTO.getAuthor().getName());

            author = authorRepository.save(author);
        }

       BookEntity newBook =  new BookEntity()
                .setAuthor(author)
                .setIsbn(bookDTO.getIsbn())
                .setTitle(bookDTO.getTitle());

        newBook = bookRepository.save(newBook);

        return newBook.getId();
    }
}
