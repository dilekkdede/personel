import {Component, Input, OnInit} from '@angular/core';
import {AddressService} from '../services/address.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {Event} from '@angular/router';

@Component({
  selector: 'app-address',
  standalone: false,
  templateUrl: './address.component.html',
  styleUrl: './address.component.css'
})
export class AddressComponent implements OnInit {
  @Input({required: true}) personId!: any;

  addressList: any[] = [];

  isEditButton: boolean = false;
  adresDialogVisible: boolean = false;

  adresId: number | null = null;
  adresDescription: string | null = null;

  constructor(
    private addressService: AddressService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
  ) {
  }

  ngOnInit(): void {
    this.getAdres(this.personId);
  }

  getAdres(id: number) {
    this.addressService.findByPersonelId(id).then(response => {
      this.addressList = response;
    }).catch(error => {
      console.error('Adresler yüklenirken hata:', error);
    });
  }

  showDialogAdres() {
    this.isEditButton = false;
    this.adresDialogVisible = true;
    this.adresDescription = null;
    this.adresId = null;
  }

  cancelAdres() {
    this.adresDialogVisible = false;
  }


  saveAdres() {
    const adres = {
      id: null,
      description: this.adresDescription,
      personelId: this.personId,
    };

    this.addressService.save(adres).then(response => {
      if (response.status === 201) {
        this.adresDialogVisible = false;
        this.getAdres(this.personId);
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'Adres başarıyla eklendi'
        });
      }
    }).catch(error => console.log(error));
  }

  editAdres(adres: any) {
    this.isEditButton = true;
    this.adresDialogVisible = true;
    this.adresId = adres.id;
    this.adresDescription = adres.description;
  }

  updateAdres() {
    const adres = {
      id: this.adresId,
      description: this.adresDescription,
      personelId: this.personId,
    };

    this.addressService.update(this.adresId!, adres).then(response => {
      if (response.status === 200) {
        this.adresDialogVisible = false;
        this.getAdres(this.personId);
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'Adres başarıyla güncellendi'
        });
      }
    }).catch(error => console.log(error));
  }

  confirmDeleteAddress(event: Event, adresId: number) {
    this.confirmationService.confirm({
      //target: event.target as EventTarget,
      message: 'Silmek istediğinize emin misiniz?',
      header: 'Uyarı',
      icon: 'pi pi-info-circle',
      acceptLabel: 'Evet',
      rejectLabel: 'İptal',
      acceptButtonProps: {severity: 'danger'},
      rejectButtonProps: {severity: 'secondary', outlined: true},
      accept: () => this.deleteAdres(adresId)
    });
  }

  deleteAdres(adresId: number) {
    this.addressService.delete(adresId).then(response => {
      if (response.status === 200) {
        this.getAdres(this.personId);
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'Adres silindi'
        });
      }
    }).catch(error => console.log(error));
  }


}
