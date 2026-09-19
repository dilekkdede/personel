import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {ThemeService} from '../services/theme.service';

@Component({
  selector: 'app-shell',
  standalone: false,
  templateUrl: './shell.component.html',
  styleUrl: './shell.component.css'
})
export class ShellComponent {
  constructor(public auth: AuthService, public theme: ThemeService, private router: Router) {
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
