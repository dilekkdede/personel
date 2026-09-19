import {Component, OnInit} from '@angular/core';
import {UnitService} from '../services/unit.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-unit',
  standalone: false,
  templateUrl: './unit.component.html',
  styleUrl: './unit.component.css'
})
export class UnitComponent implements OnInit {
  birimler: any[] = [];
  visible = false;
  saving = false;
  unitId: any = null;
  isEditButton = false;
  unitName = '';
  unitCode = '';

  constructor(private unitService: UnitService,
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
    this.unitName = '';
    this.unitCode = '';
    this.unitId = null;
  }

  cancel() {
    this.visible = false;
  }

  getData() {
    this.unitService.findAll().then(response => this.birimler = response || []);
  }

  private valid(): boolean {
    if (!this.unitName?.trim() || !this.unitCode?.trim()) {
      this.messageService.add({severity: 'warn', summary: 'Eksik bilgi', detail: 'Birim adı ve kodu zorunlu'});
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

  saveUnit() {
    if (!this.valid()) {
      return;
    }
    this.saving = true;
    this.unitService.save({name: this.unitName.trim(), code: this.unitCode.trim()})
      .then(response => this.notify(response, 'Birim kaydedildi'))
      .finally(() => this.saving = false);
  }

  editUnit(unit: any) {
    this.isEditButton = true;
    this.visible = true;
    this.unitName = unit.name || '';
    this.unitCode = unit.code || '';
    this.unitId = unit.id;
  }

  updateUnit() {
    if (!this.valid()) {
      return;
    }
    this.saving = true;
    this.unitService.update(this.unitId, {id: this.unitId, name: this.unitName.trim(), code: this.unitCode.trim()})
      .then(response => this.notify(response, 'Birim güncellendi'))
      .finally(() => this.saving = false);
  }

  confirmDelete(event: Event, unitId: any) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Bu birimi silmek istediğinize emin misiniz?',
      header: 'Silme onayı',
      icon: 'pi pi-info-circle',
      rejectButtonProps: {label: 'İptal', severity: 'secondary', outlined: true},
      acceptButtonProps: {label: 'Sil', severity: 'danger'},
      accept: () => this.unitService.delete(unitId).then(response => this.notify(response, 'Birim silindi'))
    });
  }
}
