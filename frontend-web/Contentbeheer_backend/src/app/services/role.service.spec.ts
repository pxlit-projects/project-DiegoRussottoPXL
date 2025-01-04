import { TestBed } from '@angular/core/testing';
import { RoleService } from './role.service';

describe('RoleService', () => {
  let service: RoleService;
  let localStorageMock: any;

  beforeEach(() => {
    // Mocking localStorage
    localStorageMock = {
      getItem: jasmine.createSpy('getItem').and.callFake((key: string) => {
        return key === 'role' ? 'admin' : null; // Zorg ervoor dat de mock 'admin' retourneert
      }),
      setItem: jasmine.createSpy('setItem')
    };

    // Configure the testing module
    TestBed.configureTestingModule({
      providers: [
        RoleService,
        { provide: Storage, useValue: localStorageMock } // Mock localStorage with spy
      ]
    });

    service = TestBed.inject(RoleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });



  it('should return the correct role when role$ is subscribed to', (done) => {
    service.role$.subscribe(role => {
      // Controleer of de rol 'admin' is bij de eerste abonnee
      if (role === 'admin') {
        // Verander de rol en trigger opnieuw
        service.setRole('user');
      }
      // Controleer of de rol 'user' wordt na de wijziging
      if (role === 'user') {
        done();
      }
    });
  });

});
