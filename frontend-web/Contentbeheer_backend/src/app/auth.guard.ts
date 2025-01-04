import { CanActivateFn } from '@angular/router';
import { RoleService } from './services/role.service';  // Importeer je RoleService
import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const roleService = inject(RoleService);  
  const router = inject(Router);  

  const role = roleService.getRole();  

  if (role === 'redacteur') {
    return true;  
  } else {
    router.navigate(['/']);
    return false;  
  }
};
