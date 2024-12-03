import { CanActivateFn } from '@angular/router';
import { RoleService } from './services/role.service';  // Importeer je RoleService
import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const roleService = inject(RoleService);  // Injecteer RoleService
  const router = inject(Router);  // Injecteer Router om door te sturen

  const role = roleService.getRole();  // Haal de rol op uit localStorage

  // Controleer of de rol 'redacteur' is
  if (role === 'redacteur') {
    return true;  // Toegang toegestaan voor redacteurs
  } else {
    // Toegang geweigerd voor andere rollen, stuur naar de homepage of een andere pagina
    router.navigate(['/']);
    return false;  // Weiger toegang
  }
};
