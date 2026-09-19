import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username = '';
  password = '';
  loading = false;

  constructor(private auth: AuthService, private router: Router, private messageService: MessageService) {
    if (this.auth.isLoggedIn()) {
      this.router.navigate(['/dashboard']);
    }
  }

  async submit() {
    if (!this.username || !this.password) {
      this.messageService.add({severity: 'warn', summary: 'Eksik bilgi', detail: 'Kullanıcı adı ve şifre girin'});
      return;
    }
    this.loading = true;
    try {
      const response = await this.auth.login(this.username, this.password);
      if (response.status === 200) {
        this.router.navigate(['/dashboard']);
      } else {
        this.messageService.add({severity: 'error', summary: 'Giriş başarısız', detail: response.message});
      }
    } finally {
      this.loading = false;
    }
  }
}
