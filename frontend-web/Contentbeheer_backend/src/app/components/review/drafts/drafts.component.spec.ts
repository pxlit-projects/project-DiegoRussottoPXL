import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DraftsComponent } from './drafts.component';
import { ReviewService } from '../../../services/review.service';
import { of, throwError } from 'rxjs';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Post } from '../../../models/post.model';

describe('DraftsComponent', () => {
  let component: DraftsComponent;
  let fixture: ComponentFixture<DraftsComponent>;
  let reviewServiceMock: jasmine.SpyObj<ReviewService>;

  beforeEach(async () => {
    reviewServiceMock = jasmine.createSpyObj('ReviewService', [
      'getAllDraftedPosts',
      'publishPost',
      'rejectPostWithReason',
    ]);

    // Return mock data for posts
    reviewServiceMock.getAllDraftedPosts.and.returnValue(of([
      { id: 1, title: 'Post 1', content: 'Content 1', author: 'Author 1', date: '2025-01-01', status: 'DRAFT' , rejectionReason: ''},
      { id: 2, title: 'Post 2', content: 'Content 2', author: 'Author 2', date: '2025-01-02', status: 'DRAFT' , rejectionReason: ''},
    ]));

    await TestBed.configureTestingModule({
      imports: [DraftsComponent, ReactiveFormsModule, FormsModule],
      providers: [
        { provide: ReviewService, useValue: reviewServiceMock },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DraftsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch drafted posts on init', () => {
    component.ngOnInit();
    expect(reviewServiceMock.getAllDraftedPosts).toHaveBeenCalled();
    expect(component.posts.length).toBeGreaterThan(0);
  });

  it('should handle error when fetching posts', () => {
    const errorMessage = 'Failed to fetch posts';
    reviewServiceMock.getAllDraftedPosts.and.returnValue(throwError(errorMessage));

    component.ngOnInit();

    expect(component.errorMessage).toBe('Er is iets misgegaan bij het ophalen van de posts.');
  });

  it('should approve a post successfully', () => {
    const postId = 1;
    reviewServiceMock.publishPost.and.returnValue(of());

    component.approvePost(postId);

    expect(reviewServiceMock.publishPost).toHaveBeenCalledWith(postId);
    expect(component.errorMessage).toBe('');
  });

  it('should handle error when approving a post', () => {
    const postId = 1;
    const errorMessage = 'Failed to approve post';
    reviewServiceMock.publishPost.and.returnValue(throwError(errorMessage));

    component.approvePost(postId);

    expect(component.errorMessage).toBe(`Er is iets misgegaan bij het goedkeuren van de post met ID ${postId}.`);
  });

  it('should show reject input when showRejectInput is called', () => {
    const postId = 1;
    component.showRejectInput(postId);

    expect(component.rejectedPostId).toBe(postId);
    expect(component.rejectReason).toBe('');
  });

  

  it('should handle error when submitting rejection', () => {
    const postId = 1;
    const reason = 'Not relevant';
    const errorMessage = 'Failed to reject post';
    component.rejectedPostId = postId;
    component.rejectReason = reason;

    reviewServiceMock.rejectPostWithReason.and.returnValue(throwError(errorMessage));

    component.submitRejection(postId);

    expect(component.errorMessage).toBe(`Er is iets misgegaan bij het afwijzen van de post met ID ${postId}.`);
  });

  it('should not submit rejection if reason is empty', () => {
    const postId = 1;
    component.rejectedPostId = postId;
    component.rejectReason = '';

    component.submitRejection(postId);

    expect(component.errorMessage).toBe('De reden voor afwijzing mag niet leeg zijn.');
  });
});
