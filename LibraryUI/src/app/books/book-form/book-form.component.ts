import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { BooksService } from '../books.service';
import { Book } from '../model/book';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.scss'],
})
export class BookFormComponent implements OnInit, OnDestroy {
  private bookSubcription: Subscription;

  bookFormGroup = this.fb.group({
    bookId: [0],
    bookName: [''],
    authorName: [''],
    price: ['0.00'],
    totalPage: [0],
  });

  book: Book;

  constructor(private fb: FormBuilder, private booksService: BooksService) {
    this.bookSubcription = this.booksService.bookSubject
      .asObservable()
      .subscribe((response) => {
        if (!response) {
          this.bookFormGroup.reset({
            category: null,
            cost: null,
            description: null,
            id: 0,
            instock: null,
            model: null,
            notes: null,
            partnumber: null,
            pname: null,
            sold: null,
          });
        } else {
          this.bookFormGroup.setValue(response);
        }
      });
  }

  ngOnInit(): void {}

  ngOnDestroy(): void {
    this.bookSubcription.unsubscribe();
  }

  submitNewBook(event): void {
    this.booksService.bookSubmitSubject.next(this.bookFormGroup.value);
    this.bookFormGroup.reset();
  }
}
