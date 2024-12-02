import { Component, OnInit } from '@angular/core';
import { Post } from '../../../models/post.model';
import { PostService } from '../../../services/post.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
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
    this.loadPosts();
    this.postService.postUpdated$.subscribe(() => {
      this.loadPosts();
    });
  }

  loadPosts(): void {
    const filters = this.filterForm.value;
    this.postService.getFilteredPosts(filters.author, filters.content, filters.date).subscribe((data: Post[]) => {
      console.log(data);
      this.posts = data;
    });
  }

  onFilterChange(): void {
    this.loadPosts();
  }

  publishPost(postId: number): void {
    this.postService.publishPost(postId).subscribe(() => {
      this.loadPosts();
    });
  }

  editPost(post: Post): void {
    this.selectedPostId = post.id;
    const postToEdit = this.posts.find(p => p.id === this.selectedPostId);
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
        this.loadPosts();
        this.isEditing = false;
        this.selectedPostId = null;
      });
    }
  }

  cancelEdit(): void {
    this.isEditing = false;
    this.selectedPostId = null;
  }
}
