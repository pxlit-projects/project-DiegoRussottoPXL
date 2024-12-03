import { Component } from '@angular/core';
import { RoleService } from '../../../services/role.service';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {
  constructor(private roleService: RoleService) {}

  onRoleChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const selectedRole = selectElement.value;
    console.log('Role selectcccccccced:', selectedRole);  // Log toevoegen voor debugging

    this.roleService.setRole(selectedRole);
  }
}
