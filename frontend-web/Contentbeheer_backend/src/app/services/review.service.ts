import { inject, Injectable } from '@angular/core';
import { environment } from '../enviroments/enviroment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from '../models/post.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private api: string = environment.apiUrl + 'review/api/review';
  private http: HttpClient = inject(HttpClient);

  getAllDraftedPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.api + "/drafts");
  }
  publishPost(postId: number): Observable<void> {
    return this.http.put<void>(`${this.api}/post/${postId}/publish`, {});  
    }
    // rejectPost(postId: number): Observable<void> {
    //   return this.http.put<void>(`${this.api}/post/${postId}/reject`, {}); // Pas de endpoint aan naar jouw backend
    // }
    rejectPostWithReason(postId: number, reason: string): Observable<void> {
      return this.http.put<void>(`${this.api}/post/${postId}/reject`, reason );
    }
    getAllRejectedPosts(): Observable<Post[]> {
      return this.http.get<Post[]>(this.api + "/rejected");
    }
    
    
    
}
