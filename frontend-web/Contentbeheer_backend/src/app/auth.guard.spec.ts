import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RoleService } from './services/role.service';
import { authGuard } from './auth.guard';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

describe('authGuard', () => {
  let roleServiceMock: jasmine.SpyObj<RoleService>;
  let routerMock: jasmine.SpyObj<Router>;

  beforeEach(() => {
    roleServiceMock = jasmine.createSpyObj('RoleService', ['getRole']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      providers: [
        { provide: RoleService, useValue: roleServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    });
  });

  it('should return true when role is "redacteur"', () => {
    roleServiceMock.getRole.and.returnValue('redacteur');
    const routeMock = {} as ActivatedRouteSnapshot;
    const stateMock = {} as RouterStateSnapshot;

    TestBed.runInInjectionContext(() => {
      const result = authGuard(routeMock, stateMock);
      expect(result).toBeTrue();
    });
  });

  it('should return false and navigate to home when role is not "redacteur"', () => {
    roleServiceMock.getRole.and.returnValue('gebruiker');
    const routeMock = {} as ActivatedRouteSnapshot;
    const stateMock = {} as RouterStateSnapshot;

    TestBed.runInInjectionContext(() => {
      const result = authGuard(routeMock, stateMock);
      expect(result).toBeFalse();
      expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
    });
  });
});
