import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CreatePostComponent } from "./components/posts/create-post/create-post.component";
import { PostListComponent } from "./components/posts/post-list/post-list.component";
import { HomepageComponent } from "./components/posts/homepage/homepage.component";
import { NavbarComponent } from "./components/navbar/navbar.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CreatePostComponent, PostListComponent, HomepageComponent, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Contentbeheer_backend';
}
