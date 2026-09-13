import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ContactService} from '../services/contact.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {response} from 'express';
import {Event} from '@angular/router';

@Component({
  selector: 'app-contact',
  standalone: false,
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent implements OnInit {
  @Input({required: true}) personId!: any;

  isEditButton: boolean = false;
  contactDialogVisible: boolean = false;
  conactId: any = null;
  contactDescription: any = null;
  contactType: any = null;
  contactList: any = [];

  constructor(private contactService: ContactService, private messageService: MessageService, private confirmationService: ConfirmationService) {
  }

  contactShowDialog(): void {
    this.isEditButton = false;
    this.contactDialogVisible = true;
    this.conactId = null;
    this.contactType = null;
    this.contactDescription = null;
  }

  ngOnInit(): void {
    this.getContact(this.personId);
  }

  getContact(id: number) {
    this.contactService.findByIdPersonelContact(id).then(response => {
      this.contactList = response;
    }).catch(error => {
      console.error('Contacts yüklenirken hata:', error);
    });
  }


  cancelContact() {
    this.contactDialogVisible = false;
  }

  saveContact() {
    const dto = {
      id: null,
      contact: this.contactDescription,
      type: this.contactType,
      personelId: this.personId,
    }

    this.contactService.save(dto).then(response => {
      if (response.status == 201) {
        this.getContact(this.personId);
        this.contactDialogVisible = false;
        console.log("Veri kayıt edildi");

        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'İletişim Bilgisi başarıyla eklendi'
        });
      }
    }).catch(error => console.log(error));
  }

  editContact(contact: any) {
    this.contactDialogVisible = true;
    this.isEditButton = true;
    this.conactId = contact.id;
    this.contactDescription = contact.contact;
    this.contactType = contact.type;
  }


  updateContact() {

    const dto = {
      id: this.conactId,
      contact: this.contactDescription,
      type: this.contactType,
      personelId: this.personId,
    };
    console.log(dto);
    this.contactService.update(this.conactId, dto).then(response => {
      if (response.status == 200) {
        this.contactDialogVisible = false;
        this.getContact(this.personId);
        console.log("Veri güncellendi");

        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'İletişim bilgisi başarıyla güncellendi'
        });
      }
    }).catch(error => console.log(error));
  }


  deleteContact(id: number) {
    this.contactService.delete(id).then(response => {
      if (response.status == 200) {
        this.getContact(this.personId);
        this.contactDialogVisible = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'İletişim silindi'

        });
      }
    }).catch(error => console.log(error));
  }


  confirmDeleteContact(event: Event, contactId: number) {
    this.confirmationService.confirm({
      //target: event.target as EventTarget,
      message: 'Silmek istediğinize emin misiniz?',
      header: 'Uyarı',
      icon: 'pi pi-info-circle',
      acceptLabel: 'Evet',
      rejectLabel: 'İptal',
      acceptButtonProps: {severity: 'danger'},
      rejectButtonProps: {severity: 'secondary', outlined: true},
      accept: () => this.deleteContact(contactId)
    });
  }


}
