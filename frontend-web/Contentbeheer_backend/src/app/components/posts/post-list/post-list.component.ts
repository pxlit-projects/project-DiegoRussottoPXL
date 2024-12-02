import { Component, OnInit } from '@angular/core';
import { Post } from '../../../models/post.model';
import { PostService } from '../../../services/post.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent implements OnInit {
  posts: Post[] = [];
  isEditing: boolean = false;
  selectedPostId: number | null = null;
  postForm: FormGroup;
  filterForm: FormGroup;


  constructor(private postService: PostService, private fb: FormBuilder) {
    this.postForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
    this.filterForm = this.fb.group({
      author: [''],
      content: [''],
      date: ['']
    });
  }

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      author: [''],
      content: [''],
      date: ['']
    });

    this.loadPosts();
  }

  loadPosts(): void {
    const filters = this.filterForm.value;
    this.postService.getFilteredPosts(filters.author, filters.content, filters.date).subscribe((data: Post[]) => {
      this.posts = data;
    });
  }

  onFilterChange(): void {
    this.loadPosts(); // Reload posts when filter changes
  }

  publishPost(postId: number): void {
    this.postService.publishPost(postId).subscribe(() => {
      this.loadPosts(); // Herlaad de posts nadat de post is gepubliceerd
    });
  }
  editPost(post: Post): void {
    this.selectedPostId = post.id;
    const postToEdit = this.posts.find(post => post.id === this.selectedPostId);
    if (postToEdit) {
      this.postForm.patchValue({
        title: postToEdit.title,
        content: postToEdit.content
      });
    }
    this.isEditing = true;
  }
  savePost(): void {
    if (this.postForm.valid && this.selectedPostId) {
      const updatedPost = {
        id: this.selectedPostId,
        ...this.postForm.value
      };
      this.postService.updatePost(updatedPost).subscribe(() => {
        this.loadPosts();  // Herlaad de posts na het opslaan
        this.isEditing = false; // Stop met bewerken
        this.selectedPostId = null; // Reset de geselecteerde post
      });
    }
  }

  cancelEdit(): void {
    this.isEditing = false;
    this.selectedPostId = null;
  }

}
