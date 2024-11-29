import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Post } from '../models/post.model';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = 'http://localhost:8091/api/post'

  constructor(private http: HttpClient) { }

  createPost(post: Post): Observable<Post> {
    return this.http.post<Post>(this.apiUrl, post);
}
}