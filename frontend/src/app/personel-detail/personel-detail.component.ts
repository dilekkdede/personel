import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PersonelService} from '../services/personel.service';
import {AuthService} from '../services/auth.service';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-personel-detail',
  standalone: false,
  templateUrl: './personel-detail.component.html',
  styleUrl: './personel-detail.component.css'
})
export class PersonelDetailComponent implements OnInit {
  personel: any = null;
  error = '';
  loading = true;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private personelService: PersonelService,
              public auth: AuthService,
              private messageService: MessageService) {
  }

  async ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    const response = await this.personelService.findById(id);
    this.loading = false;
    if (response.status === 200) {
      this.personel = response.data;
    } else {
      this.error = response.message || 'Personel bulunamadı';
    }
  }

  statusLabel(status?: string) {
    if (status === 'ON_LEAVE') return 'İzinde';
    if (status === 'LEFT') return 'İşten ayrıldı';
    return 'Aktif';
  }

  async setStatus(status: string) {
    const response = await this.personelService.changeStatus(this.personel.id, status);
    if (response.status === 200) {
      this.personel = {...this.personel, ...response.data};
      this.messageService.add({severity: 'success', summary: 'Durum güncellendi', detail: this.statusLabel(status)});
    } else {
      this.messageService.add({severity: 'error', summary: 'İşlem başarısız', detail: response.message});
    }
  }

  back() {
    this.router.navigate(['/personel']);
  }
}
