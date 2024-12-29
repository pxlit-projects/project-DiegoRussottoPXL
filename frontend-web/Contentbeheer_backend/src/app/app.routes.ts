import { Routes } from '@angular/router';
import { CreatePostComponent } from './components/posts/create-post/create-post.component';
import { HomepageComponent } from './components/posts/homepage/homepage.component';
import { PostListComponent } from './components/posts/post-list/post-list.component';
import { authGuard } from './auth.guard';
import { DraftsComponent } from './components/review/drafts/drafts.component';
import { RejectedComponent } from './components/review/rejected/rejected.component';

export const routes: Routes = [
    { path: '', redirectTo: 'Home', pathMatch: 'full' },
    {path: 'home', component : HomepageComponent},
    {path: 'add', component : CreatePostComponent, canActivate: [authGuard]},
    {path: 'posts', component : PostListComponent},
    {path: 'drafts', component : DraftsComponent, canActivate: [authGuard]},
    {path: 'rejected', component : RejectedComponent, canActivate: [authGuard]},
    { path: '**', component: HomepageComponent }
];
