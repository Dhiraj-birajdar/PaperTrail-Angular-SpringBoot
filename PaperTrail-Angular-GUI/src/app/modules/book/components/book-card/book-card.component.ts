import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.scss']
})
export class BookCardComponent {
  private id : number  = Math.floor(Math.random() * 1000) + 1;
  private _manage: boolean = false;
  private _book: BookResponse = {};
  private _bookCover: string|undefined;

  get manage(): boolean {
    return this._manage;
  }

  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }

  get bookCover(): string | undefined {
    if(this._book.cover){
      return 'data:image/jpeg;base64,' + this._book.cover;
    }
    return "https://picsum.photos/id/"+ this.id++ +"/200/200";
  }


  get book(): BookResponse {
    return this._book;
  }

  @Input()
  set book(value: BookResponse) {
    this._book = value;
  }

  @Output() private details: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  onShowDetails() {
    this.details.emit(this._book);
  }

  @Output() private addToWaitingList: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  onAddToWaitingList() {
    this.addToWaitingList.emit(this._book);
  }

  @Output() private borrow: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  onBorrow() {
    this.borrow.emit(this._book);
  }

  @Output() private edit: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  onEdit() {
    this.edit.emit(this._book);
  }

  @Output() private share: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  onShare() {
    this.share.emit(this._book);
  }

  @Output() private archive: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();
  onArchive() {
    this.archive.emit(this._book);
  }
}
