import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Post } from '../models/post.model';
import { Observable } from 'rxjs';
import { environment } from '../enviroments/enviroment.development';
@Injectable({
  providedIn: 'root'
})
export class PostService {


    private api: string = environment.apiUrl + 'post/api/post';
    private http: HttpClient = inject(HttpClient);
       createPost(post: Post): Observable<Post> {
     return this.http.post<Post>(this.api, post);
 }
}