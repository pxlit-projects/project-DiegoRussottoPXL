import { Component, OnInit } from '@angular/core';
import { Post } from '../../../../models/post.model';
import { ReviewService } from '../../../../services/review.service';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-rejected',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './rejected.component.html',
  styleUrl: './rejected.component.css'
})
export class RejectedComponent implements OnInit {
  rejectedPosts: Post[] = [];
  errorMessage: string = '';
  isEditing: boolean = false; // Controleert of we in de bewerkingsmodus zijn
  currentPost: Post = {
    id: 0, title: '', content: '', date: '', rejectionReason: '',
    author: '',
    status: 'DRAFT'
  }; // Huidige post die wordt bewerkt

  constructor(private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.fetchRejectedPosts();
  }

  fetchRejectedPosts(): void {
    this.reviewService.getAllRejectedPosts().subscribe({
      next: (posts) => {
        console.log('Ophalen van rejected posts succesvol:', posts);
        this.rejectedPosts = posts;
        console.log(this.rejectedPosts);
      },
      error: (err) => {
        console.error('Fout bij het ophalen van rejected posts:', err);
        this.errorMessage = 'Er is iets misgegaan bij het ophalen van de afgewezen posts.';
      }
    });
  }

  // Start de bewerkingsmodus voor de geselecteerde post
  editPost(post: Post): void {
    this.isEditing = true;
    this.currentPost = { ...post }; // Maak een kopie van de post voor bewerking
  }

  // Verzend de gewijzigde gegevens naar de backend
   submitResubmission(): void {
      this.reviewService.resubmitPost(this.currentPost).subscribe({
        next: () => {
          alert('Post opnieuw ingediend!');
          this.isEditing = false;
          this.fetchRejectedPosts(); // Update de lijst met posts na herindiening
        },
        error: (err) => {
          this.errorMessage = `Er is iets misgegaan bij het opnieuw indienen van de post: ${err}`;
        }
      });
   }

  // Annuleer de bewerkingsmodus zonder iets op te slaan
  cancelEdit(): void {
    this.isEditing = false;
  }

}