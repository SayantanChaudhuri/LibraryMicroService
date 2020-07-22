import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { BooksService } from './books.service';
import { Book } from './model/book';
import { Table } from 'primeng/table/table';
import { Observable, Subscription } from 'rxjs';
import { MessageService, ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss'],
})
export class BooksComponent implements OnInit, OnDestroy {
  booksList: Book[] = [
    { bookId: 0, bookName: '', authorName: '', totalPage: 0, price: 0.0 },
  ];
  loading = true;
  newBook: Book;
  first = 0;
  last = 0;
  totalRecords = 0;

  @ViewChild('dt') table: Table;

  modalDisplay = false;

  private booksSubmitSubscription: Subscription;
  modalHeader: string;

  constructor(
    private booksService: BooksService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
    this.getAllBooks();
    this.booksSubmitSubscription = this.booksService.bookSubmitSubject
    .asObservable()
    .subscribe((book) => {
      this.modalDisplay = false;

      if (this.modalHeader.indexOf('Update') > -1) {
        this.updateBook(book);
      } else {
        this.saveBook(book);
      }
    });
  }

  ngOnInit(): void {}

  ngOnDestroy(): void {
    this.booksSubmitSubscription.unsubscribe();
  }

  showNewBookModal(event): void {
    this.modalDisplay = true;
    this.modalHeader = 'New book details';
    this.booksService.bookSubject.next(null);
  }

  showUpdateBookModal(book: Book): void {
    this.booksService.bookSubject.next(book);
    this.modalHeader = 'Update book details';
    this.modalDisplay = true;
  }

  showDeleteBookModal(book: Book): void {
    this.confirmationService.confirm({
      accept: () => {
        this.deleteBook(book);
      },
    });
  }

  getAllBooks(): void {
    this.loading = true;
    this.booksService.getAllBooks().subscribe(
      (response) => {
        this.booksList = response.books;
        this.loading = false;
      },
      (err) => {
        console.error(err);
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Success',
          detail: 'Error: Records couldn\'t load. Try later',
        });
      }
    );
  }

  saveBook(book: Book): void {
    this.loading = true;
    this.booksService.saveBook(book).subscribe(
      (response) => {
        this.booksList = response.books;
        this.loading = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Record successfully saved.',
        });
      },
      (err) => {
        console.error(err);
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Success',
          detail: 'Error: Record couldn\'t saved. Try later',
        });
      }
    );
  }

  updateBook(book: Book): void {
    this.loading = true;
    this.booksService.updateBook(book).subscribe(
      (response) => {
        this.booksList = response.books;
        this.loading = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Record successfully updated.'
        });
      },
      (err) => {
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Success',
          detail: 'Error: Record couldn\'t updated. Try later.'
        });
      }
    );
  }

  deleteBook(book: Book): void {
    this.loading = true;
    this.booksService.deleteBook(book).subscribe(
      (response) => {
        this.booksList = response.books;
        this.loading = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Record successfully deleted.',
        });
      },
      (err) => {
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Success',
          detail: 'Error: Record couldn\'t updated. Try later.'
        });
      }
    );
  }
}
