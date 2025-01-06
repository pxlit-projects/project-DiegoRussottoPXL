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
    this.roleService.role$.subscribe(role => {
      this.isRedacteur = role === 'redacteur';
    });
  }
}
