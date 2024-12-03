import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { RoleService } from '../../services/role.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  isRedacteur: boolean = false;

  constructor(private roleService: RoleService) {}

  ngOnInit(): void {
    // Abonneer je op rolveranderingen vanuit de service
    this.roleService.role$.subscribe(role => {
      console.log('Role changed in NavbarComponent:', role);  // Logging toegevoegen voor debugging

      this.isRedacteur = role === 'redacteur';
    });
  }
}
