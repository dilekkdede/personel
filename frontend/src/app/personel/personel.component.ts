import {Component, OnInit} from '@angular/core';
import {PersonelService} from '../services/personel.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {CityService} from '../services/city.service';
import {UnitService} from '../services/unit.service';

@Component({
  selector: 'app-personel',
  standalone: false,
  templateUrl: './personel.component.html',
  styleUrl: './personel.component.css'
})
export class PersonelComponent implements OnInit {

  personeller: any = [];
  visible: boolean = false;
  isEditButton: boolean = false;
  personId: any = null;
  personName: any = null;
  personLastName: any = null;
  personUserName: any = null;
  personDescription: any = null;
  personBolum: any = null;
  personBirthDay: any = null;
  personAdres_id: any = null;
  personContact_id: any = null;
  personCity_id: any = null;
  personUnit_id: any = null;
  personAdresDescription: string | null = null;
  cities: any[] = [];
  units: any[] = [];


  constructor(private personelService: PersonelService,
              private messageService: MessageService,
              private cityService: CityService,
              private unitService: UnitService,
              private confirmationService: ConfirmationService) {
  }

  ngOnInit(): void {
    this.getData();
    this.getCity();
    this.getUnits();
  }

  showDialog() {
    this.isEditButton = false;
    this.visible = true;
    this.personName = null;
    this.personLastName = null;
    this.personUserName = null;
    this.personDescription = null;
    this.personBolum = null;
    this.personBirthDay = null;
    this.personAdres_id = null;
    this.personCity_id = null;
    this.personUnit_id = null;
  }


  cancel() {
    this.visible = false;
  }


  getData() {
    this.personelService.findAll().then(response => {
      this.personeller = response;
    });
  }

  getUnits() {
    this.unitService.findAll().then(response => {
      this.units = response;
    });
  }

  getCity() {
    this.cityService.findAll().then(response => {
      this.cities = response;
    });
  }


  save() {
    const personel = {
      id: null,
      firstName: this.personName,
      lastName: this.personLastName,
      userName: this.personUserName,
      description: this.personDescription,
      bolum: this.personBolum,
      birthDate: this.personBirthDay,
      city: {id: this.personCity_id},
      unit: {id: this.personUnit_id},
      adres: {  // adres bilgisi eklendi
        description: this.personAdresDescription
      }
    };

    this.personelService.save(personel).then(response => {
      if (response.status === 201) {
        this.visible = false;
        this.getData();
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'Başarılı bir şekilde kayıt yapıldı'
        });
      } else if (response.status === 400) {
        this.messageService.add({
          severity: 'error',
          summary: 'Başarısız',
          detail: response.message
        });
      }
    }).catch(error => {
      console.log(error);
    });
  }

  edit(personel: any) {
    this.isEditButton = true;
    this.visible = true;
    this.personId = personel.id;
    this.personName = personel.firstName;
    this.personLastName = personel.lastName;
    this.personUserName = personel.userName;
    this.personDescription = personel.description;
    this.personBolum = personel.bolum;
    this.personBirthDay = new Date(personel.birthDate);
    this.personAdres_id = personel.adres?.id;
    this.personContact_id = personel.contact?.id;
    this.personAdresDescription = personel.adres?.description || '';
    this.personCity_id = personel.city?.id;
    this.personUnit_id = personel.unit?.id;
  }


  updatePerson() {
    this.visible = true;

    const personel = {
      id: this.personId,
      firstName: this.personName,
      lastName: this.personLastName,
      userName: this.personUserName,
      description: this.personDescription,
      bolum: this.personBolum,
      birthDate: this.personBirthDay,
      adres: {
        id: this.personAdres_id,
        description: this.personAdresDescription
      },

      city: {id: this.personCity_id},
      unit: {id: this.personUnit_id}
    };

    this.personelService.update(this.personId, personel)
      .then(response => {
        if (response.status === 200) {
          this.visible = false;
          this.getData();
          this.getCity();
          this.messageService.add({
            severity: 'success',
            summary: 'Başarılı',
            detail: 'Başarılı bir şekilde güncellendi'
          });
        } else if (response.status === 400) {
          this.messageService.add({
            severity: 'error',
            summary: 'Başarısız',
            detail: response.message
          });
        }
      })
      .catch(error => {
        console.log(error);
      });
  }


  deletePerson(personId: any) {
    this.personelService.delete(personId).then(response => {
      if (response.status === 200) {
        this.visible = false;
        this.getData();
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'Kayıt silindi'
        })
      }
    }).catch(error => {
      console.log(error);
    })

  }


  confirmDelete(event: Event, personId: any) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'silmek istediğinize emin misiniz!',
      header: 'Danger Zone',
      icon: 'pi pi-info-circle',
      rejectLabel: 'Cancel',
      rejectButtonProps: {
        label: 'İptal',
        severity: 'secondary',
        outlined: true,
      },
      acceptButtonProps: {
        label: 'Evet',
        severity: 'danger',
      },

      accept: () => {
        this.deletePerson(personId)
      },
      reject: () => {
      },
    });
  }


}
