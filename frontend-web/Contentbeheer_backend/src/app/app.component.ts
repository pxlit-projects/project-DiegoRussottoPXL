import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CreatePostComponent } from "./components/create-post/create-post.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CreatePostComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Contentbeheer_backend';
}
