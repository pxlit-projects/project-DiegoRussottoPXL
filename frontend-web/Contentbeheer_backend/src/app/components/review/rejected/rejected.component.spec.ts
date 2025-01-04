import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RejectedComponent } from './rejected.component';
import { ReviewService } from '../../../services/review.service';
import { of, throwError } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { Post } from '../../../models/post.model';

describe('RejectedComponent', () => {
  let component: RejectedComponent;
  let fixture: ComponentFixture<RejectedComponent>;
  let reviewServiceMock: jasmine.SpyObj<ReviewService>;

  beforeEach(async () => {
    // Maak een mock van de ReviewService
    reviewServiceMock = jasmine.createSpyObj('ReviewService', [
      'getAllRejectedPosts',
      'resubmitPost',
    ]);

    // Zorg ervoor dat de mock van getAllRejectedPosts een observable teruggeeft
    reviewServiceMock.getAllRejectedPosts.and.returnValue(of([]));

    await TestBed.configureTestingModule({
      imports: [RejectedComponent, FormsModule],
      providers: [
        { provide: ReviewService, useValue: reviewServiceMock },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RejectedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch rejected posts on init', () => {
    const mockPosts: Post[] = [
      { id: 1, title: 'Post 1', content: 'Content 1', date: '2025-01-01', rejectionReason: 'Not relevant', author: 'Author 1', status: 'PENDING' },
      { id: 2, title: 'Post 2', content: 'Content 2', date: '2025-01-02', rejectionReason: 'Poor quality', author: 'Author 2', status: 'PENDING' },
    ];

    // Return the mock posts when the service method is called
    reviewServiceMock.getAllRejectedPosts.and.returnValue(of(mockPosts));

    component.ngOnInit();

    expect(reviewServiceMock.getAllRejectedPosts).toHaveBeenCalled();
    expect(component.rejectedPosts).toEqual(mockPosts);
  });

  it('should handle error when fetching rejected posts', () => {
    const errorMessage = 'Failed to fetch posts';
    reviewServiceMock.getAllRejectedPosts.and.returnValue(throwError(errorMessage));

    component.ngOnInit();

    expect(component.errorMessage).toBe('Er is iets misgegaan bij het ophalen van de afgewezen posts.');
  });

  it('should start editing a post', () => {
    const post: Post = { id: 1, title: 'Post 1', content: 'Content 1', date: '2025-01-01', rejectionReason: 'Not relevant', author: 'Author 1', status: 'PENDING' };

    component.editPost(post);

    expect(component.isEditing).toBeTrue();
    expect(component.currentPost).toEqual(post);
  });
  

  it('should handle error when resubmitting a post', () => {
    const post: Post = { id: 1, title: 'Post 1', content: 'Content 1', date: '2025-01-01', rejectionReason: 'Not relevant', author: 'Author 1', status: 'PENDING' };
    component.currentPost = post;
    const errorMessage = 'Failed to resubmit post';

    reviewServiceMock.resubmitPost.and.returnValue(throwError(errorMessage));

    component.submitResubmission();

    expect(component.errorMessage).toBe(`Er is iets misgegaan bij het opnieuw indienen van de post: ${errorMessage}`);
  });

  it('should cancel editing a post', () => {
    component.cancelEdit();
    expect(component.isEditing).toBeFalse();
  });
});
