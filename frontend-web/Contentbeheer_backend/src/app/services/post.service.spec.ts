import { TestBed } from '@angular/core/testing';
import { PostService } from './post.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../enviroments/enviroment.development';
import { Post } from '../models/post.model';
import { of } from 'rxjs';

describe('PostService', () => {
  let service: PostService;
  let httpMock: HttpTestingController;

  const apiUrl = environment.apiUrl + 'post/api/post';
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PostService]
    });

    service = TestBed.inject(PostService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create a post', () => {
    const newPost: Post = {
      id: 1,
      title: 'Test Post',
      content: 'Test content',
      author: 'Test User',
      date: '2025-01-01',
      status: 'DRAFT',
      rejectionReason: null
    };

    service.createPost(newPost).subscribe(response => {
      expect(response).toEqual(newPost);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');
    req.flush(newPost); // Simuleer een succesvolle server response
  });

  it('should get all posts', () => {
    const mockPosts: Post[] = [
      { id: 1, title: 'Post 1', content: 'Content 1', author: 'Author 1', date: '2025-01-01', status: 'PUBLISHED', rejectionReason: null },
      { id: 2, title: 'Post 2', content: 'Content 2', author: 'Author 2', date: '2025-01-02', status: 'DRAFT', rejectionReason: null }
    ];

    service.getAllPosts().subscribe(posts => {
      expect(posts.length).toBe(2);
      expect(posts).toEqual(mockPosts);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts); // Simuleer een succesvolle server response
  });

  it('should get filtered posts based on author', () => {
    const mockPosts: Post[] = [
      { id: 1, title: 'Post 1', content: 'Content 1', author: 'Author 1', date: '2025-01-01', status: 'PUBLISHED', rejectionReason: null },
    ];

    const author = 'Author 1';
    const filterUrl = `${apiUrl}?author=${author}`;

    service.getFilteredPosts(author, '', '').subscribe(posts => {
      expect(posts.length).toBe(1);
      expect(posts).toEqual(mockPosts);
    });

    const req = httpMock.expectOne(filterUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts); // Simuleer een succesvolle server response
  });

  it('should notify post update', () => {
    spyOn(service['postUpdatedSource'], 'next'); // Spioneren op postUpdatedSource.next

    service.notifyPostUpdate();

    expect(service['postUpdatedSource'].next).toHaveBeenCalledWith(null);
  });

  it('should get comments by post id', () => {
    const mockComments = [
      { id: 1, postId: 1, author: 'Commenter 1', content: 'Comment content 1', timestamp: '2025-01-01T00:00:00Z' }
    ];

    const postId = 1;
    const commentsApiUrl = `${apiUrl}/${postId}/comments`;

    service.getCommentsByPostId(postId).subscribe(comments => {
      expect(comments.length).toBe(1);
      expect(comments).toEqual(mockComments);
    });

    const req = httpMock.expectOne(commentsApiUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockComments); // Simuleer een succesvolle server response
  });
});
