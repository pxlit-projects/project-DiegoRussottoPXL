import { TestBed } from '@angular/core/testing';
import { ReviewService } from './review.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../enviroments/enviroment.development';
import { Post } from '../models/post.model';

describe('ReviewService', () => {
  let service: ReviewService;
  let httpMock: HttpTestingController;

  const apiUrl = environment.apiUrl + 'review/api/review';
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReviewService]
    });

    service = TestBed.inject(ReviewService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all drafted posts', () => {
    const mockPosts: Post[] = [
      { id: 1, title: 'Draft Post 1', content: 'Draft content 1', author: 'Author 1', date: '2025-01-01', status: 'DRAFT', rejectionReason: null },
      { id: 2, title: 'Draft Post 2', content: 'Draft content 2', author: 'Author 2', date: '2025-01-02', status: 'DRAFT', rejectionReason: null }
    ];

    service.getAllDraftedPosts().subscribe(posts => {
      expect(posts.length).toBe(2);
      expect(posts).toEqual(mockPosts);
    });

    const req = httpMock.expectOne(`${apiUrl}/drafts`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts); // Simuleer een succesvolle server response
  });

  it('should publish a post', () => {
    const postId = 1;

    service.publishPost(postId).subscribe(response => {
      expect(response).toBeNull(); // De response voor een publish zou void moeten zijn
    });

    const req = httpMock.expectOne(`${apiUrl}/post/${postId}/publish`);
    expect(req.request.method).toBe('PUT');
    req.flush(null); // Geen inhoud terugsturen om de verwachte void te simuleren
  });

  it('should reject a post with reason', () => {
    const postId = 1;
    const reason = 'Reason for rejection';

    service.rejectPostWithReason(postId, reason).subscribe(response => {
      expect(response).toBeNull(); // De response voor reject zou void moeten zijn
    });

    const req = httpMock.expectOne(`${apiUrl}/post/${postId}/reject`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(reason); // De reden moet correct in de request body worden meegegeven
    req.flush(null); // Geen inhoud terugsturen om de verwachte void te simuleren
  });

  it('should get all rejected posts', () => {
    const mockPosts: Post[] = [
      { id: 1, title: 'Rejected Post 1', content: 'Rejected content 1', author: 'Author 1', date: '2025-01-01', status: 'PENDING', rejectionReason: 'Reason 1' },
      { id: 2, title: 'Rejected Post 2', content: 'Rejected content 2', author: 'Author 2', date: '2025-01-02', status: 'PENDING', rejectionReason: 'Reason 2' }
    ];

    service.getAllRejectedPosts().subscribe(posts => {
      expect(posts.length).toBe(2);
      expect(posts).toEqual(mockPosts);
    });

    const req = httpMock.expectOne(`${apiUrl}/rejected`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts); // Simuleer een succesvolle server response
  });

  it('should resubmit a post', () => {
    const post: Post = { 
      id: 1, 
      title: 'Post Title', 
      content: 'Post content', 
      author: 'Author', 
      date: '2025-01-01', 
      status: 'DRAFT', 
      rejectionReason: null 
    };
  
    service.resubmitPost(post).subscribe(response => {
      expect(response).toBeNull(); // Verwacht geen waarde (void).
    });
  
    const req = httpMock.expectOne(`${apiUrl}/post/${post.id}/resubmit`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual({ title: post.title, content: post.content });
    req.flush(null); // Geen inhoud terugsturen om de verwachte void te simuleren
  });
  
});
