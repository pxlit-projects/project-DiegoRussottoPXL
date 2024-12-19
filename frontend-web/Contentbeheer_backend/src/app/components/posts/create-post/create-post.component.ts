
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostService } from '../../../services/post.service';

@Component({
  selector: 'app-create-post',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.css'
})
export class CreatePostComponent {
  postForm: FormGroup;

  constructor(
    private postService: PostService,
    private fb: FormBuilder
  ) {
    this.postForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.postForm.valid) {
      const post = this.postForm.value;
      const username = localStorage.getItem('username'); // Haal de gebruikersnaam op uit localStorage
  
      if (!username) {
        console.error('Geen gebruikersnaam gevonden in localStorage.');
        return;
      }
  
      post.author = username; // Stel de opgehaalde gebruikersnaam in als auteur
  
      this.postService.createPost(post).subscribe(response => {
        console.log('Post aangemaakt:', response);
        this.postService.notifyPostUpdate(); // Meld dat er een update is
      });
  
      this.postForm.reset();
    }
  }
  
  // publishPost() {
  //   // Stuur een request naar de backend om de post te publiceren
  //   const postId = 52; // Vervang dit door het werkelijke postId
  //   this.postService.publishPost(postId).subscribe();
  // }
  
}
