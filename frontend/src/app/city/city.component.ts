import {Component, OnInit} from '@angular/core';
import {CityService} from '../services/city.service';
import {ConfirmationService, MessageService} from 'primeng/api';

// @ts-ignore

@Component({
  selector: 'app-city',
  standalone: false,
  templateUrl: './city.component.html',
  styleUrl: './city.component.css'
})
export class CityComponent implements OnInit {
  //NgModule sadece input yapılan companentlerde kullanılır. Button gibi companentlerde kullanılmaz.
  //Biz HtML içinde kullandığımız herhangi bir input companentin value değerini ts tarafına NgModule ile taşırız
  //Tüm bu işlemlere de DataBinding denir
  //Aslında böyle yaparak dışarıdan girilen inputları elimizde tuttuk . CityCode ya atayarak vb.
  //Ngİf -> ile html tagları içersinde companentlere koşullar yazılabiliyor. javadaki if else yapısı gibi
  sehirler: any = [];
  visible: boolean = false;
  cityName: any = null;
  cityCode: any = null;
  cityId: any = null;
  isEditButton: boolean = false;
  selectedCity: any = null;
  cities = [
    { name: 'Adana', code: '01' },
    { name: 'Adıyaman', code: '02' },
    { name: 'Afyonkarahisar', code: '03' },
    { name: 'Ağrı', code: '04' },
    { name: 'Amasya', code: '05' },
    { name: 'Ankara', code: '06' },
    { name: 'Antalya', code: '07' },
    { name: 'Artvin', code: '08' },
    { name: 'Aydın', code: '09' },
    { name: 'Balıkesir', code: '10' },
    { name: 'Bilecik', code: '11' },
    { name: 'Bingöl', code: '12' },
    { name: 'Bitlis', code: '13' },
    { name: 'Bolu', code: '14' },
    { name: 'Burdur', code: '15' },
    { name: 'Bursa', code: '16' },
    { name: 'Çanakkale', code: '17' },
    { name: 'Çankırı', code: '18' },
    { name: 'Çorum', code: '19' },
    { name: 'Denizli', code: '20' },
    { name: 'Diyarbakır', code: '21' },
    { name: 'Edirne', code: '22' },
    { name: 'Elazığ', code: '23' },
    { name: 'Erzincan', code: '24' },
    { name: 'Erzurum', code: '25' },
    { name: 'Eskişehir', code: '26' },
    { name: 'Gaziantep', code: '27' },
    { name: 'Giresun', code: '28' },
    { name: 'Gümüşhane', code: '29' },
    { name: 'Hakkâri', code: '30' },
    { name: 'Hatay', code: '31' },
    { name: 'Isparta', code: '32' },
    { name: 'Mersin', code: '33' },
    { name: 'İstanbul', code: '34' },
    { name: 'İzmir', code: '35' },
    { name: 'Kars', code: '36' },
    { name: 'Kastamonu', code: '37' },
    { name: 'Kayseri', code: '38' },
    { name: 'Kırklareli', code: '39' },
    { name: 'Kırşehir', code: '40' },
    { name: 'Kocaeli', code: '41' },
    { name: 'Konya', code: '42' },
    { name: 'Kütahya', code: '43' },
    { name: 'Malatya', code: '44' },
    { name: 'Manisa', code: '45' },
    { name: 'Kahramanmaraş', code: '46' },
    { name: 'Mardin', code: '47' },
    { name: 'Muğla', code: '48' },
    { name: 'Muş', code: '49' },
    { name: 'Nevşehir', code: '50' },
    { name: 'Niğde', code: '51' },
    { name: 'Ordu', code: '52' },
    { name: 'Rize', code: '53' },
    { name: 'Sakarya', code: '54' },
    { name: 'Samsun', code: '55' },
    { name: 'Siirt', code: '56' },
    { name: 'Sinop', code: '57' },
    { name: 'Sivas', code: '58' },
    { name: 'Tekirdağ', code: '59' },
    { name: 'Tokat', code: '60' },
    { name: 'Trabzon', code: '61' },
    { name: 'Tunceli', code: '62' },
    { name: 'Şanlıurfa', code: '63' },
    { name: 'Uşak', code: '64' },
    { name: 'Van', code: '65' },
    { name: 'Yozgat', code: '66' },
    { name: 'Zonguldak', code: '67' },
    { name: 'Aksaray', code: '68' },
    { name: 'Bayburt', code: '69' },
    { name: 'Karaman', code: '70' },
    { name: 'Kırıkkale', code: '71' },
    { name: 'Batman', code: '72' },
    { name: 'Şırnak', code: '73' },
    { name: 'Bartın', code: '74' },
    { name: 'Ardahan', code: '75' },
    { name: 'Iğdır', code: '76' },
    { name: 'Yalova', code: '77' },
    { name: 'Karabük', code: '78' },
    { name: 'Kilis', code: '79' },
    { name: 'Osmaniye', code: '80' },
    { name: 'Düzce', code: '81' }
  ];


  showDialog() {
    this.isEditButton = false;
    this.visible = true;
    this.cityName = null;
    this.cityCode = null;
  }


  constructor(private cityService: CityService, private messageService: MessageService, private confirmationService: ConfirmationService) {
  }

  ngOnInit(): void {
    this.getData(); // Başka sayfaya gittiğimizd dahi veriler kalsın diye burada çağırırız metotları
  }

  openDialog(): void {
    console.log('openDialog');
  }



  saveCity() {
    const city = {
      "id": null,
      "name": this.cityName,
      "code": this.cityCode,
    }

    this.cityService.save(city).then(response => {

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
      //  console.log(response);
    }).catch(error => {
      console.log(error);

    })

    console.log(city);
  }

  getData() {
    this.cityService.findAll().then(response => {
      this.sehirler = response;
    });
  }

  cancel() {
    this.visible = false;
  }

  deleteCity(cityId: any) {

    this.cityService.delete(cityId).then(response => {
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

  confirmDelete(event: Event, cityId: any) {
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
        this.deleteCity(cityId);
      },
      reject: () => {
      },
    });
  }

  editCity(city: any) {
    //Edit metodu tamamen setlemeye yarıyor (postmandeki body ekranı diyebiliriz)
    this.isEditButton = true;
    this.visible = true;
    this.cityName = city.name;
    this.cityCode = city.code;
    this.cityId = city.id;
  }


  updateCity() {
    this.visible = true;

    const city = {
      "id": this.cityId,
      "name": this.cityName,
      "code": this.cityCode,
    }

    this.cityService.update(this.cityId, city).then(response => {

      if (response.status === 201) {
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

    console.log(city);

  }


}
