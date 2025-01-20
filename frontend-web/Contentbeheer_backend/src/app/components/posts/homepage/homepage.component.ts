import { Component } from '@angular/core';
import { RoleService } from '../../../services/role.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {
  username: string = ''; // Variabele om de gebruikersnaam op te slaan
  selectedRole: string = 'gebruiker'; // Standaardrol

  constructor(private roleService: RoleService) {}

  onRoleChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    this.selectedRole = selectElement.value;
    console.log('Role selected:', this.selectedRole); // Log voor debugging
  }

  saveUserData(): void {
    if (!this.username.trim()) {
      alert('Voer een geldige gebruikersnaam in.');
      return;
    }
    // Sla gebruikersnaam en rol op in localStorage
    localStorage.setItem('username', this.username);
    localStorage.setItem('role', this.selectedRole);
    this.roleService.setRole(this.selectedRole);

    console.log(`Gebruikersnaam: ${this.username}, Rol: ${this.selectedRole}`);
  }
}
