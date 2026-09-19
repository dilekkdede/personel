import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {PersonelService} from '../services/personel.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {CityService} from '../services/city.service';
import {UnitService} from '../services/unit.service';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-personel',
  standalone: false,
  templateUrl: './personel.component.html',
  styleUrl: './personel.component.css'
})
export class PersonelComponent implements OnInit {

  personeller: any[] = [];
  selected: any[] = [];
  totalRecords = 0;
  rows = 10;
  q = '';
  cityId: any = null;
  unitId: any = null;
  bolum = '';
  employmentStatus: any = null;
  hasFilter = false;
  loading = false;
  visible = false;
  saving = false;
  isEditButton = false;
  submitted = false;
  personId: any = null;
  personName = '';
  personLastName = '';
  personUserName = '';
  personDescription = '';
  personBolum = '';
  personBirthDay: Date | null = null;
  personAdres_id: any = null;
  personCity_id: any = null;
  personUnit_id: any = null;
  personAdresDescription = '';
  personStatus = 'ACTIVE';
  cities: any[] = [];
  units: any[] = [];
  statusOptions = [
    {label: 'Aktif', value: 'ACTIVE'},
    {label: 'İzinde', value: 'ON_LEAVE'},
    {label: 'İşten ayrıldı', value: 'LEFT'}
  ];

  constructor(private personelService: PersonelService,
              private messageService: MessageService,
              private cityService: CityService,
              private unitService: UnitService,
              private confirmationService: ConfirmationService,
              private router: Router,
              public auth: AuthService) {
  }

  ngOnInit(): void {
    this.getCity();
    this.getUnits();
  }

  filters() {
    return {
      q: this.q || undefined,
      cityId: this.cityId || undefined,
      unitId: this.unitId || undefined,
      bolum: this.bolum || undefined,
      employmentStatus: this.employmentStatus || undefined
    };
  }

  showDialog() {
    this.isEditButton = false;
    this.visible = true;
    this.submitted = false;
    this.personId = null;
    this.personName = '';
    this.personLastName = '';
    this.personUserName = '';
    this.personDescription = '';
    this.personBolum = '';
    this.personBirthDay = null;
    this.personAdres_id = null;
    this.personCity_id = null;
    this.personUnit_id = null;
    this.personAdresDescription = '';
    this.personStatus = 'ACTIVE';
  }

  cancel() {
    this.visible = false;
  }

  load(event?: any) {
    const page = event ? Math.floor((event.first || 0) / (event.rows || this.rows)) : 0;
    const size = event?.rows ?? this.rows;
    this.hasFilter = !!(this.q || this.cityId || this.unitId || this.bolum || this.employmentStatus);
    this.loading = true;
    this.personelService.findAll(page, size, this.filters()).then(data => {
      this.personeller = data?.content ?? [];
      this.totalRecords = data?.totalElements ?? 0;
      this.loading = false;
    }).catch(() => this.loading = false);
  }

  clearFilters() {
    this.q = '';
    this.cityId = null;
    this.unitId = null;
    this.bolum = '';
    this.employmentStatus = null;
    this.load();
  }

  getUnits() {
    this.unitService.findAll().then(response => this.units = response || []);
  }

  getCity() {
    this.cityService.findAll().then(response => this.cities = response || []);
  }

  invalid(field: 'name' | 'lastName' | 'bolum' | 'city' | 'unit') {
    if (!this.submitted) {
      return false;
    }
    if (field === 'name') return !this.personName?.trim();
    if (field === 'lastName') return !this.personLastName?.trim();
    if (field === 'bolum') return !this.personBolum?.trim();
    if (field === 'city') return !this.personCity_id;
    return !this.personUnit_id;
  }

  private valid(): boolean {
    this.submitted = true;
    if (this.invalid('name') || this.invalid('lastName') || this.invalid('bolum') || this.invalid('city') || this.invalid('unit')) {
      this.messageService.add({severity: 'warn', summary: 'Eksik bilgi', detail: 'Kırmızı alanları doldur'});
      return false;
    }
    return true;
  }

  private payload() {
    const data: any = {
      firstName: this.personName.trim(),
      lastName: this.personLastName.trim(),
      userName: this.personUserName?.trim() || null,
      description: this.personDescription?.trim() || null,
      bolum: this.personBolum.trim(),
      birthDate: this.personBirthDay,
      city: {id: this.personCity_id},
      unit: {id: this.personUnit_id},
      employmentStatus: this.personStatus
    };
    if (this.personAdresDescription?.trim()) {
      data.adres = {id: this.personAdres_id, description: this.personAdresDescription.trim()};
    }
    return data;
  }

  notify(response: any, successMessage: string) {
    if (response.status === 200 || response.status === 201) {
      this.visible = false;
      this.selected = [];
      this.load();
      this.messageService.add({severity: 'success', summary: 'Başarılı', detail: successMessage});
    } else {
      this.messageService.add({severity: 'error', summary: 'İşlem başarısız', detail: response.message || 'Kayıt yapılamadı'});
    }
  }

  save() {
    if (!this.valid()) {
      return;
    }
    this.saving = true;
    this.personelService.save(this.payload()).then(response => this.notify(response, 'Personel kaydedildi'))
      .finally(() => this.saving = false);
  }

  edit(personel: any) {
    this.isEditButton = true;
    this.visible = true;
    this.submitted = false;
    this.personId = personel.id;
    this.personName = personel.firstName || '';
    this.personLastName = personel.lastName || '';
    this.personUserName = personel.userName || '';
    this.personDescription = personel.description || '';
    this.personBolum = personel.bolum || '';
    this.personBirthDay = personel.birthDate ? new Date(personel.birthDate) : null;
    this.personAdres_id = personel.adres?.id;
    this.personAdresDescription = personel.adres?.description || '';
    this.personCity_id = personel.city?.id ?? null;
    this.personUnit_id = personel.unit?.id ?? null;
    this.personStatus = personel.employmentStatus || 'ACTIVE';
  }

  updatePerson() {
    if (!this.valid()) {
      return;
    }
    this.saving = true;
    this.personelService.update(this.personId, this.payload()).then(response => this.notify(response, 'Personel güncellendi'))
      .finally(() => this.saving = false);
  }

  openDetail(personel: any) {
    this.router.navigate(['/personel', personel.id]);
  }

  statusLabel(status?: string) {
    if (status === 'ON_LEAVE') return 'İzinde';
    if (status === 'LEFT') return 'İşten ayrıldı';
    return 'Aktif';
  }

  confirmLeave(event: Event, personel: any) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: personel.firstName + ' işten ayrıldı olarak işaretlensin mi?',
      header: 'Pasife al',
      icon: 'pi pi-info-circle',
      rejectButtonProps: {label: 'İptal', severity: 'secondary', outlined: true},
      acceptButtonProps: {label: 'Pasife al', severity: 'danger'},
      accept: () => {
        this.personelService.changeStatus(personel.id, 'LEFT').then(response => this.notify(response, 'Personel pasife alındı'));
      }
    });
  }

  async applyBulk(status: string) {
    if (!this.selected.length) {
      this.messageService.add({severity: 'warn', summary: 'Seçim yok', detail: 'Önce personel seç'});
      return;
    }
    const ids = this.selected.map(item => item.id);
    const response = await this.personelService.bulkStatus(ids, status);
    this.notify(response, response.message || 'Toplu güncelleme yapıldı');
  }

  async exportCsv() {
    const data = await this.personelService.findAll(0, 500, this.filters());
    const rows = data?.content ?? [];
    if (!rows.length) {
      this.messageService.add({severity: 'warn', summary: 'Veri yok', detail: 'Dışa aktarılacak kayıt bulunamadı'});
      return;
    }
    const header = ['Ad', 'Soyad', 'Kullanıcı', 'Bölüm', 'Şehir', 'Birim', 'Durum'];
    const lines = rows.map((item: any) => [
      item.firstName, item.lastName, item.userName, item.bolum,
      item.city?.name, item.unit?.name || item.unit?.code, this.statusLabel(item.employmentStatus)
    ].map(value => `"${String(value ?? '').replaceAll('"', '""')}"`).join(','));
    const csv = [header.join(','), ...lines].join('\n');
    const blob = new Blob(['\ufeff' + csv], {type: 'text/csv;charset=utf-8;'});
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = 'personel.csv';
    link.click();
    URL.revokeObjectURL(url);
  }
}
