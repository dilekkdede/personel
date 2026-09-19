import {Injectable} from '@angular/core';
import axios from 'axios';
import {environment} from '../../environments/environment';
import {apiBody} from './api-client';

const TOKEN_KEY = 'personel_token';
const USER_KEY = 'personel_user';

function browserStorage(): Storage | null {
  try {
    return typeof localStorage === 'undefined' ? null : localStorage;
  } catch {
    return null;
  }
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  async login(username: string, password: string) {
    const body = await apiBody(axios.post(environment.apiUrl + '/auth/login', {username, password}));
    if (body.status === 200 && body.data?.token) {
      browserStorage()?.setItem(TOKEN_KEY, body.data.token);
      browserStorage()?.setItem(USER_KEY, JSON.stringify(body.data));
    }
    return body;
  }

  logout() {
    browserStorage()?.removeItem(TOKEN_KEY);
    browserStorage()?.removeItem(USER_KEY);
  }

  getToken(): string | null {
    return browserStorage()?.getItem(TOKEN_KEY) ?? null;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  currentUser(): any {
    const raw = browserStorage()?.getItem(USER_KEY);
    return raw ? JSON.parse(raw) : null;
  }

  isAdmin(): boolean {
    const role = String(this.currentUser()?.role || '').toUpperCase();
    return role === 'ADMIN' || role === 'ROLE_ADMIN';
  }
}
