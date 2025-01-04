import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomepageComponent } from './homepage.component';
import { RoleService } from '../../../services/role.service';
import { FormsModule } from '@angular/forms';
import { of } from 'rxjs';

describe('HomepageComponent', () => {
  let component: HomepageComponent;
  let fixture: ComponentFixture<HomepageComponent>;
  let roleServiceMock: jasmine.SpyObj<RoleService>;

  beforeEach(async () => {
    roleServiceMock = jasmine.createSpyObj('RoleService', ['getRoles']); // Mock van de RoleService

    await TestBed.configureTestingModule({
      imports: [HomepageComponent, FormsModule],
      providers: [
        { provide: RoleService, useValue: roleServiceMock },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomepageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should change selected role when onRoleChange is called', () => {
    const event = { target: { value: 'admin' } } as unknown as Event;
    component.onRoleChange(event);
    
    expect(component.selectedRole).toBe('admin');
  });

  it('should alert user when username is empty in saveUserData', () => {
    spyOn(window, 'alert'); // Spy on window.alert
    component.username = ''; // Lege gebruikersnaam
    component.saveUserData();
    
    expect(window.alert).toHaveBeenCalledWith('Voer een geldige gebruikersnaam in.');
  });

  it('should save username and role in localStorage when saveUserData is called', () => {
    spyOn(localStorage, 'setItem'); // Spy on localStorage.setItem
    component.username = 'testUser';
    component.selectedRole = 'admin';
    component.saveUserData();
    
    expect(localStorage.setItem).toHaveBeenCalledWith('username', 'testUser');
    expect(localStorage.setItem).toHaveBeenCalledWith('role', 'admin');
  });

  it('should alert welcome message when saveUserData is called with valid username', () => {
    spyOn(window, 'alert'); // Spy on window.alert
    component.username = 'testUser';
    component.selectedRole = 'admin';
    component.saveUserData();
    
    expect(window.alert).toHaveBeenCalledWith('Welkom, testUser! Je bent nu ingelogd als admin.');
  });
});
