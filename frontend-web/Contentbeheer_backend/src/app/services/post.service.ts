import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Post } from '../models/post.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../enviroments/enviroment.development';
import { Comment } from '../models/comment.model';
@Injectable({
  providedIn: 'root'
})
export class PostService {


    private api: string = environment.apiUrl + 'post/api/post';
    private http: HttpClient = inject(HttpClient);

    private postUpdatedSource = new BehaviorSubject<null>(null);
  postUpdated$ = this.postUpdatedSource.asObservable();
       createPost(post: Post): Observable<Post> {
     return this.http.post<Post>(this.api, post);
 }
 publishPost(postId: number): Observable<void> {
  return this.http.put<void>(`${this.api}/${postId}/publish`, {});

  }
  getAllPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.api);
  }
  updatePost(updatedPost: Post): Observable<Post> {
    return this.http.put<Post>(`${this.api}/${updatedPost.id}`, updatedPost);
  }
  // post.service.ts

  getFilteredPosts(author: string, content: string, date: string): Observable<Post[]> {
    // Bouw de query parameters direct in de URL
    let filterUrl = `${this.api}?`;

    if (author) {
      filterUrl += `author=${author}&`;
    }
    if (content) {
      filterUrl += `content=${content}&`;
    }
    if (date) {
      filterUrl += `date=${date}&`;
    }

    // Verwijder de laatste '&' als er filters zijn toegevoegd
    filterUrl = filterUrl.endsWith('&') ? filterUrl.slice(0, -1) : filterUrl;

    return this.http.get<Post[]>(filterUrl);
  }
  notifyPostUpdate(): void {
    this.postUpdatedSource.next(null);
  }
  getCommentsByPostId(postId: number): Observable<Comment[]> {
    const commentsApi = `${this.api}/${postId}/comments`;
    return this.http.get<Comment[]>(commentsApi);
  }
  
}