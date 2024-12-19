import { inject, Injectable } from '@angular/core';
import { environment } from '../enviroments/enviroment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
      private api: string = environment.apiUrl + 'comment/api/comments';
      private http: HttpClient = inject(HttpClient);

      createComment(postId: number, comment: { author: string; content: string }): Observable<any> {
        const url = `${this.api}/${postId}`;
        return this.http.post(url, comment);
      }


}
