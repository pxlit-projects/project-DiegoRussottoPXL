import { Component, OnInit } from '@angular/core';
import { Post } from '../../../models/post.model';
import { ReviewService } from '../../../services/review.service';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-drafts',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './drafts.component.html',
  styleUrl: './drafts.component.css'
})
export class DraftsComponent implements OnInit {
  posts: Post[] = [];
  errorMessage: string = '';

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
    console.log(`Post met ID ${postId} goedgekeurd.`);
    // Voeg hier je goedkeur-logica toe
  }
  
  rejectPost(postId: number): void {
    console.log(`Post met ID ${postId} afgewezen.`);
    // Voeg hier je afwijzing-logica toe
  }
  

}
