import { Routes } from '@angular/router';
import { CreatePostComponent } from './components/posts/create-post/create-post.component';
import { HomepageComponent } from './components/posts/homepage/homepage.component';
import { PostListComponent } from './components/posts/post-list/post-list.component';

export const routes: Routes = [
    { path: '', redirectTo: 'Home', pathMatch: 'full' },
    {path: 'home', component : HomepageComponent},
    {path: 'add', component : CreatePostComponent},
    {path: 'posts', component : PostListComponent},
    { path: '**', component: HomepageComponent }
];
