import {Component, OnInit} from '@angular/core';
import {UnitService} from '../services/unit.service';
import {ConfirmationService, MessageService} from 'primeng/api';

@Component({
  selector: 'app-unit',
  standalone: false,
  templateUrl: './unit.component.html',
  styleUrl: './unit.component.css'
})
export class UnitComponent implements OnInit {

  birimler: any = [];
  visible: boolean = false;
  unitId: any = null;
  isEditButton: boolean = false;
  unitName: any = null;
  unitCode: any = null;


  constructor(private unitService: UnitService, private messageService: MessageService,private confirmationService: ConfirmationService) {
    //Dependcy Injection işlemi yapıldı. Yazmış olduğumuz servisin kullanılabilir hale gelmesi
  }

  showDialog() {
    this.isEditButton = false;
    this.visible = true;
    this.unitName = null;
    this.unitCode = null;
  }

  cancel(): void {
    this.visible = false;
  }


  ngOnInit(): void {
    //Angularda sayfa yüklendiğinde ilk çalışan fonksiyondur
    this.getData();
  }


  openDialog(): void {
    console.log('openDialog');
  }


  saveUnit() {
    const unit = {
      "id": null,
      "name": this.unitName,
      "code": this.unitCode,
    }

    this.unitService.save(unit).then(response => {

      if (response.status === 201) {
        this.visible = false;
        this.getData();
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'Başarılı bir şekilde kayıt yapıldı'
        })
      }
      if (response.status === 400) {
        this.messageService.add({
          severity: 'error',
          summary: 'Başarısız',
          detail: response.message
        })
      }
    }).catch(error => {
      console.log(error);
    })
  }

  getData() {
    this.unitService.findAll().then(response => {
      this.birimler = response;
    });
  }


  deleteUnit(unitId:any) {
    this.unitService.delete(unitId).then(response => {
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

  confirmDelete(event: Event, unitId: any) {
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
        this.deleteUnit(unitId);
      },
      reject: () => {
      },
    });
  }

  editUnit(unit: any) {
    this.isEditButton = true;
    this.visible = true;
    this.unitName = unit.name;
    this.unitCode = unit.code;
    this.unitId = unit.id;
  }

  updateUnit() {
    this.visible = true;
    const unit = {
      "id": null,
      "name": this.unitName,
      "code": this.unitCode,
    }
    this.unitService.update(this.unitId, unit).then(response => {
      if (response.status === 200) {
        this.visible = false;
        this.getData();
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'Başarılı bir şekilde güncellendi'
        })
      }
      if (response.status === 400) {
        this.messageService.add({
          severity: 'error',
          summary: 'Başarısız',
          detail: response.message
        })
      }
    }).catch(error => {
      console.log(error);
    })
  }
}
