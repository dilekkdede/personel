import {Injectable} from '@angular/core';

const THEME_KEY = 'personel_theme';
const COMPACT_KEY = 'personel_compact';

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
export class ThemeService {
  dark = true;
  compact = false;

  constructor() {
    this.restore();
  }

  restore() {
    const stored = browserStorage()?.getItem(THEME_KEY);
    this.dark = stored ? stored === 'dark' : true;
    this.compact = browserStorage()?.getItem(COMPACT_KEY) === '1';
    this.apply();
  }

  setDark(dark: boolean) {
    this.dark = dark;
    browserStorage()?.setItem(THEME_KEY, dark ? 'dark' : 'light');
    this.apply();
  }

  setCompact(compact: boolean) {
    this.compact = compact;
    browserStorage()?.setItem(COMPACT_KEY, compact ? '1' : '0');
    this.apply();
  }

  apply() {
    if (typeof document === 'undefined') {
      return;
    }
    document.documentElement.classList.toggle('app-dark', this.dark);
    document.documentElement.classList.toggle('app-compact', this.compact);
  }
}
