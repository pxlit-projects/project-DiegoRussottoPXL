import { TestBed } from '@angular/core/testing';
import { CommentService } from './comment.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../enviroments/enviroment.development';

describe('CommentService', () => {
  let service: CommentService;
  let httpMock: HttpTestingController;

  const apiUrl = environment.apiUrl + 'comment/api/comments';
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CommentService]
    });

    service = TestBed.inject(CommentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create a comment', () => {
    const postId = 1;
    const newComment = { author: 'Test Author', content: 'Test content' };

    service.createComment(postId, newComment).subscribe(response => {
      expect(response).toEqual(newComment);
    });

    const req = httpMock.expectOne(`${apiUrl}/${postId}`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newComment); // Controleren of het juiste object wordt meegestuurd
    req.flush(newComment); // Simuleer een succesvolle server response
  });

  it('should update a comment', () => {
    const commentId = 1;
    const updatedComment = { content: 'Updated content' };

    service.updateComment(commentId, updatedComment).subscribe(response => {
      expect(response).toEqual(updatedComment);
    });

    const req = httpMock.expectOne(`${apiUrl}/${commentId}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedComment); // Controleren of het juiste object wordt meegestuurd
    req.flush(updatedComment); // Simuleer een succesvolle server response
  });

  it('should delete a comment', () => {
    const commentId = 1;

    service.deleteComment(commentId).subscribe(response => {
      expect(response).toBeNull(); // We verwachten geen body in de response bij een DELETE-aanroep
    });

    const req = httpMock.expectOne(`${apiUrl}/${commentId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null); // Simuleer een succesvolle server response zonder body
  });
});
