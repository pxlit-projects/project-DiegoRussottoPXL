import { Component } from '@angular/core';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {
  onRoleChange(event: any) {
    const selectedRole = event.target.value;
    console.log(`Geselecteerde rol: ${selectedRole}`);
    localStorage.setItem('role', selectedRole);
  }
}
