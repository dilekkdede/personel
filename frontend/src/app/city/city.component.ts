import {Component, OnInit} from '@angular/core';
import {CityService} from '../services/city.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-city',
  standalone: false,
  templateUrl: './city.component.html',
  styleUrl: './city.component.css'
})
export class CityComponent implements OnInit {
  sehirler: any[] = [];
  visible = false;
  saving = false;
  cityName = '';
  cityCode = '';
  cityId: any = null;
  isEditButton = false;

  constructor(private cityService: CityService,
              private messageService: MessageService,
              private confirmationService: ConfirmationService,
              public auth: AuthService) {
  }

  ngOnInit(): void {
    this.getData();
  }

  showDialog() {
    this.isEditButton = false;
    this.visible = true;
    this.cityName = '';
    this.cityCode = '';
    this.cityId = null;
  }

  cancel() {
    this.visible = false;
  }

  getData() {
    this.cityService.findAll().then(response => this.sehirler = response || []);
  }

  private valid(): boolean {
    if (!this.cityName?.trim() || !this.cityCode?.trim()) {
      this.messageService.add({severity: 'warn', summary: 'Eksik bilgi', detail: 'Şehir adı ve plaka zorunlu'});
      return false;
    }
    return true;
  }

  notify(response: any, successMessage: string) {
    if (response.status === 200 || response.status === 201) {
      this.visible = false;
      this.getData();
      this.messageService.add({severity: 'success', summary: 'Başarılı', detail: successMessage});
    } else {
      this.messageService.add({
        severity: response.status === 409 ? 'warn' : 'error',
        summary: 'İşlem başarısız',
        detail: response.message || 'Kayıt yapılamadı'
      });
    }
  }

  saveCity() {
    if (!this.valid()) {
      return;
    }
    this.saving = true;
    this.cityService.save({name: this.cityName.trim(), code: this.cityCode.trim()})
      .then(response => this.notify(response, 'Şehir kaydedildi'))
      .finally(() => this.saving = false);
  }

  editCity(city: any) {
    this.isEditButton = true;
    this.visible = true;
    this.cityName = city.name || '';
    this.cityCode = city.code || '';
    this.cityId = city.id;
  }

  updateCity() {
    if (!this.valid()) {
      return;
    }
    this.saving = true;
    this.cityService.update(this.cityId, {id: this.cityId, name: this.cityName.trim(), code: this.cityCode.trim()})
      .then(response => this.notify(response, 'Şehir güncellendi'))
      .finally(() => this.saving = false);
  }

  confirmDelete(event: Event, cityId: any) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Bu şehri silmek istediğinize emin misiniz?',
      header: 'Silme onayı',
      icon: 'pi pi-info-circle',
      rejectButtonProps: {label: 'İptal', severity: 'secondary', outlined: true},
      acceptButtonProps: {label: 'Sil', severity: 'danger'},
      accept: () => this.cityService.delete(cityId).then(response => this.notify(response, 'Şehir silindi'))
    });
  }
}
