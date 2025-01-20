import { Component, OnInit } from '@angular/core';
import { Post } from '../../../models/post.model';
import { ReviewService } from '../../../services/review.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-drafts',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './drafts.component.html',
  styleUrl: './drafts.component.css'
})
export class DraftsComponent implements OnInit {
  posts: Post[] = [];
  errorMessage: string = '';
  rejectedPostId: number | null = null;
  editingPostId: number | null = null;
  rejectReason: string = ''; 
  editedTitle = '';
  editedContent = '';


  constructor(private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.fetchPosts();
  }

  fetchPosts(): void {
    this.reviewService.getAllDraftedPosts().subscribe({
      next: (data) => {
        this.posts = data;
        console.log(this.posts);
      },
      error: (error) => {
        this.errorMessage = 'Er is iets misgegaan bij het ophalen van de posts.';
        console.error(error);
      }
    });
  }
  approvePost(postId: number): void {
    this.reviewService.publishPost(postId).subscribe({
      next: () => {
        console.log(`Post met ID ${postId} goedgekeurd.`);
        this.fetchPosts(); // Vernieuw de postlijst na goedkeuren
      },
      error: (error) => {
        this.errorMessage = `Er is iets misgegaan bij het goedkeuren van de post met ID ${postId}.`;
        console.error(error);
      }
    });
  }
  
// Toon het inputveld voor afwijzingsreden
showRejectInput(postId: number): void {
  this.rejectedPostId = postId; // Stel het huidige post-ID in
  this.rejectReason = ''; // Reset de reden
}

// Bevestig de afwijzing met reden
submitRejection(postId: number): void {
  if (!this.rejectReason.trim()) {
    this.errorMessage = 'De reden voor afwijzing mag niet leeg zijn.';
    return;
  }
  console.log(`Post met ID ${postId} afgewezen met reden: ${this.rejectReason}`);
  this.reviewService.rejectPostWithReason(postId, this.rejectReason).subscribe({
    next: () => {
      console.log(`Post met ID ${postId} afgewezen met reden: ${this.rejectReason}`);
      this.rejectedPostId = null; // Reset de ID
      this.rejectReason = ''; // Reset de reden
      this.fetchPosts(); // Vernieuw de lijst
    },
    error: (error) => {
      this.errorMessage = `Er is iets misgegaan bij het afwijzen van de post met ID ${postId}.`;
      console.error(error);
    }
  });
}
showEditForm(postId: number) {
  this.editingPostId = postId;
  // Reset de afwijzingsstate
  this.rejectedPostId = null;
  const post = this.posts.find(p => p.id === postId);
  if (post) {
    this.editedTitle = post.title;
    this.editedContent = post.content;
  }
}

submitEdit(postId: number) {
  const post = this.posts.find(p => p.id === postId);
  if (post) {
    post.title = this.editedTitle;
    post.content = this.editedContent;
    this.editingPostId = null;
    this.reviewService.resubmitPost(post).subscribe({
        next: () => {
        },
        error: (err) => {
          this.errorMessage = `Er is iets misgegaan bij het opnieuw indienen van de post: ${err}`;
        }
      });
  }
}

cancelEdit() {
  this.editingPostId = null;
}
}
