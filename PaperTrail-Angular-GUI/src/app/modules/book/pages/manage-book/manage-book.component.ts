import {Component, OnInit} from '@angular/core';
import {BookRequest} from "../../../../services/models/book-request";
import {BookService} from "../../../../services/services/book.service";
import {ActivatedRoute, Router} from "@angular/router";
import {elementAt} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.component.html',
  styleUrls: ['./manage-book.component.scss']
})
export class ManageBookComponent implements OnInit{

  errorMsg: Array<string> = [];
  selectedPicture: string | undefined;
  selectedBookCover: any;
  bookRequest: BookRequest = {author: "", isbn: "", synopsis: "", title: ""};

  constructor(
    private bookService: BookService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    const bookId = this.activatedRoute.snapshot.params['bookId'];
    if(bookId){
      this.bookService.getBook({
        'book-id': bookId
      }).subscribe({
        next:(book:BookResponse)=>{
          this.bookRequest = {
            id: book.id,
            title: book.title || '',
            author: book.author || '',
            isbn: book.isbn || '',
            synopsis: book.synopsis || '',
            shareable: book.shareable || false
          }
          if(book.cover){
            this.selectedPicture = 'data:image/jpeg;base64,' + book.cover;
          }
        }
      });
    }
    }
  onFileSelected(event: any) {
    this.selectedBookCover = event.target.files[0];
    console.log(this.selectedBookCover);
    if(this.selectedBookCover){
      const reader = new FileReader();
      reader.onload = ()=>{
        this.selectedPicture = reader.result as string;
      }
      reader.readAsDataURL(this.selectedBookCover);
    }else{
      // this.selectedPicture = 'https://picsum.photos/200/200'; // todo: handle default image
    }
  }

  saveBook() {
    if(this.selectedBookCover === undefined){
      if(!this.errorMsg.includes("Please select a book cover"))
        this.errorMsg.push('Please select a book cover');
      return;
    }
    this.bookService.saveBook({
      body:this.bookRequest
    }).subscribe({
      next: (bookId:number) =>{
        this.bookService.uploadBookCover({
          'book-id': bookId,
          body:{
            file: this.selectedBookCover
          }
        }).subscribe({
          next:()=>{
            this.router.navigate(['/books/my-books'])
          }
        })
      },
      error:(err) =>{
        this.errorMsg = err.error.validationErrors;
      }
    })
  }
}
