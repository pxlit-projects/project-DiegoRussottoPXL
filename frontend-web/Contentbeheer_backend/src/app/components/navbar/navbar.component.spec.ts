import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NavbarComponent } from './navbar.component';
import { RoleService } from '../../services/role.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;
  let roleServiceMock: jasmine.SpyObj<RoleService>;

  beforeEach(async () => {
    roleServiceMock = jasmine.createSpyObj('RoleService', ['role$']);

    await TestBed.configureTestingModule({
      imports: [NavbarComponent],
      providers: [
        { provide: RoleService, useValue: roleServiceMock },
        { provide: ActivatedRoute, useValue: {} }, // Provide an empty object for ActivatedRoute
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should set isRedacteur to true when role is "redacteur"', () => {
    roleServiceMock.role$ = of('redacteur');
    fixture.detectChanges(); // Trigger ngOnInit

    expect(component.isRedacteur).toBeTrue();
  });

  it('should set isRedacteur to false when role is not "redacteur"', () => {
    roleServiceMock.role$ = of('gebruiker');
    fixture.detectChanges(); // Trigger ngOnInit

    expect(component.isRedacteur).toBeFalse();
  });

  it('should log role change in console', () => {
    spyOn(console, 'log');
    roleServiceMock.role$ = of('redacteur');
    fixture.detectChanges(); // Trigger ngOnInit

    expect(console.log).toHaveBeenCalledWith('Role changed in NavbarComponent:', 'redacteur');
  });
});
