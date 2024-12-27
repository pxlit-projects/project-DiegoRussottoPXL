import { Component, OnInit } from '@angular/core';
import { Post } from '../../../models/post.model';
import { PostService } from '../../../services/post.service';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Comment } from '../../../models/comment.model';
import { CommentService } from '../../../services/comment.service';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {
  posts: Post[] = [];
  isEditing: boolean = false;
  selectedPostId: number | null = null;
  postForm: FormGroup;
  filterForm: FormGroup;
  commentForm: FormGroup;
  userRole: string | null = null; 
  comments: Comment[] = [];
  selectedPostCommentsId: number | null = null;
  editCommentForm: FormGroup;
  currentUser: string | null = null;

  // Toevoeging van bewerkingsvlag voor comments
  isEditingComment: boolean = false;
  selectedCommentId: number | null = null;

/*************  ✨ Codeium Command ⭐  *************/
/**
 * Initializes the PostListComponent with necessary services and form groups.
 * 

/******  3166c288-f083-47cb-9902-388ccbb99bc9  *******/
  constructor(private postService: PostService, private commentService: CommentService, private fb: FormBuilder) {
    this.postForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
    this.filterForm = this.fb.group({
      author: [''],
      content: [''],
      date: ['']
    });
    this.commentForm = this.fb.group({
      content: ['', Validators.required],
    });
    this.editCommentForm = this.fb.group({
      content: ['', Validators.required],
    });
    this.currentUser = localStorage.getItem('username');
    if (!this.currentUser) {
      console.error('Geen gebruikersnaam gevonden in localStorage.');
    }
  }

  ngOnInit(): void {
    this.userRole = localStorage.getItem('role');
    this.loadPosts();
    this.postService.postUpdated$.subscribe(() => {
      this.loadPosts();
    });
  }

  loadPosts(): void {
    const filters = this.filterForm.value;
  
    this.postService.getFilteredPosts(filters.author, filters.content, filters.date).subscribe((data: Post[]) => {
      if (this.userRole === 'gebruiker') {
        this.posts = data.filter(post => post.status === 'PUBLISHED');
      } else {
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
    this.selectedPostCommentsId = postId;
    this.postService.getCommentsByPostId(postId).subscribe((data: Comment[]) => {
      this.comments = data;
    });
  }

  clearComments(): void {
    this.selectedPostCommentsId = null;
    this.comments = [];
  }

  toggleComments(postId: number): void {
    if (this.selectedPostCommentsId === postId) {
      this.clearComments();
    } else {
      this.viewComments(postId);
    }
  }

  addComment(postId: number): void {
    if (!this.currentUser) {
      console.error('U moet ingelogd zijn om een reactie toe te voegen.');
      alert('U moet eerst inloggen om een reactie toe te voegen.');
      return;
    }
  
    if (this.commentForm.valid) {
      const newComment = {
        ...this.commentForm.value,
        author: this.currentUser,
      };
  
      this.commentService.createComment(postId, newComment).subscribe({
        next: () => {
          this.viewComments(postId);
          this.commentForm.reset();
        },
        error: (err) => {
          console.error('Error adding comment:', err);
        },
      });
    }
  }
  

  // Bewerk een comment
  editComment(comment: Comment): void {
    this.selectedCommentId = comment.id;
    this.isEditingComment = true;
    this.editCommentForm.patchValue({
      content: comment.content,
    });
  }

  // Sla bewerkte comment op
  saveComment(commentId: number): void {
    console.log('Form value:', this.editCommentForm.value); // Controleer de waarde van het formulier
  console.log('Form valid:', this.editCommentForm.valid); // Controleer of het formulier geldig is

    if (this.editCommentForm.valid) {
      const updatedComment = {
        ...this.editCommentForm.value,  // Haal de bewerkte waarde op uit het formulier
      };
  
      this.commentService.updateComment(commentId, updatedComment).subscribe(() => {
        this.viewComments(this.selectedPostCommentsId!); // Refresh comments
        this.isEditingComment = false;
        this.selectedCommentId = null;
      });
    }
  }
  

  // Annuleer het bewerken van de comment
  cancelEditComment(): void {
    this.isEditingComment = false;
    this.selectedCommentId = null;
  }

  deleteComment(commentId: number): void {
    this.commentService.deleteComment(commentId).subscribe(() => {
      this.viewComments(this.selectedPostCommentsId!); // Refresh comments
    });
  }
}
