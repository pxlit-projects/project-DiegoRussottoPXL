import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CreatePostComponent } from './create-post.component';
import { PostService } from '../../../services/post.service';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';

describe('CreatePostComponent', () => {
  let component: CreatePostComponent;
  let fixture: ComponentFixture<CreatePostComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;

  beforeEach(async () => {
    postServiceMock = jasmine.createSpyObj('PostService', ['createPost', 'notifyPostUpdate']);

    await TestBed.configureTestingModule({
      imports: [CreatePostComponent, ReactiveFormsModule],
      providers: [
        { provide: PostService, useValue: postServiceMock },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    postServiceMock.createPost.calls.reset();
    postServiceMock.notifyPostUpdate.calls.reset();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should not submit the form if it is invalid', () => {
    component.postForm.controls['title'].setValue('');
    component.postForm.controls['content'].setValue('');
    
    component.onSubmit();

    expect(postServiceMock.createPost).not.toHaveBeenCalled();
  });

  it('should submit the form if it is valid', () => {
    component.postForm.controls['title'].setValue('Test Title');
    component.postForm.controls['content'].setValue('Test Content');
    localStorage.setItem('username', 'testUser');
    postServiceMock.createPost.and.returnValue(of({
      id: 1,
      title: 'Test Title',
      content: 'Test Content',
      author: 'testUser',
      date: '2023-03-09',
      status: 'DRAFT',
      rejectionReason: null,
    }));
  
    component.onSubmit();
  
    expect(postServiceMock.createPost).toHaveBeenCalled();
    
    // Controleer of het formulier is gereset
    expect(component.postForm.value).toEqual({ title: null, content: null }); // Aangepast naar `null`
  });
  


  it('should call notifyPostUpdate after post creation', () => {
    component.postForm.controls['title'].setValue('Test Title');
    component.postForm.controls['content'].setValue('Test Content');
    localStorage.setItem('username', 'testUser');
    postServiceMock.createPost.and.returnValue(of({
      id: 1,
      title: 'Test Title',
      content: 'Test Content',
      author: 'testUser',
      date: '2023-03-09',
      status: 'DRAFT',
      rejectionReason: null,
    }));

    component.onSubmit();

    expect(postServiceMock.notifyPostUpdate).toHaveBeenCalled();
  });

});
