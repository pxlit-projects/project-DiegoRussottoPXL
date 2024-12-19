import { Component, OnInit } from '@angular/core';
import { Post } from '../../../models/post.model';
import { PostService } from '../../../services/post.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Comment } from '../../../models/comment.model';

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
  userRole: string | null = null; 
  comments: Comment[] = [];
  selectedPostCommentsId: number | null = null; // Houdt bij voor welke post de comments worden bekeken


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
    this.userRole = localStorage.getItem('role'); // Haal de rol op bij het laden van de component
    this.loadPosts();
    this.postService.postUpdated$.subscribe(() => {
      this.loadPosts();
    });
  }

  loadPosts(): void {
    const filters = this.filterForm.value;
  
    this.postService.getFilteredPosts(filters.author, filters.content, filters.date).subscribe((data: Post[]) => {
      if (this.userRole === 'gebruiker') {
        // Filter alleen de gepubliceerde posts
        this.posts = data.filter(post => post.status === 'PUBLISHED');
      } else {
        // Toon alle posts voor andere rollen
        this.posts = data;
      }
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
  viewComments(postId: number): void {
    this.selectedPostCommentsId = postId; // Houd bij welke post geselecteerd is
    this.postService.getCommentsByPostId(postId).subscribe((data: Comment[]) => {
      this.comments = data;
    });
  }
  clearComments(): void {
    this.selectedPostCommentsId = null;
    this.comments = [];
  }
  
}
