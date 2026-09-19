import {Component} from '@angular/core';
import {ThemeService} from '../services/theme.service';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-settings',
  standalone: false,
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent {
  constructor(public theme: ThemeService, public auth: AuthService) {
  }
}
