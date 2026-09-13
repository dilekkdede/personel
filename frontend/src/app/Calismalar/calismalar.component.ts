import { Component } from '@angular/core';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-calismalar',
  standalone: false,
  templateUrl: './calismalar.component.html',
  styleUrl: './calismalar.component.css'
})
export class CalismalarComponent {
  title = 'PERSONEL FRONTED';
  first_name: any = "Dilek";
  last_name: any = "Dede";
  school_number: any = null;
  numberValue: any = null;
  textValue: any = null;
  overLabelValue: any = null;
  textAreaValue: any = null;
  chechkBoxValue: any = null;
  inputGroup: any = null;
  listBox: String[] = ['Ankara', 'Van', 'Kocaeli']
  selectedCity: any = null;
  password: any = null;
  listFood: String[] = ["Hamburger", "Pizza", "Lahmacun", "Kebap"];
  selectedFood: any = null;
  checked = true;
  value: any = null;
  ingredient: any = null;

  customers = [
    {
      first_name: 'Ali',
      last_name: 'Kara',
      city: 'Ankara',
    },
    {
      first_name: 'Dilek',
      last_name: 'Dede',
      city: 'Van',

    },
    {
      first_name: 'Can',
      last_name: 'Kabak',
      city: 'Kocaeli',

    },
    {
      first_name: 'Zeynep',
      last_name: 'Hoş',
      city: 'İstanbul',

    },
    {
      first_name: 'Kerem',
      last_name: 'Çatma',
      city: 'Sivas',

    }
  ];
  products = [
    {
      first_name: 'Dilek',
      last_name: 'Dede',
      city: 'Ankara',
    },
    {
      first_name: 'Kaan',
      last_name: 'Orak',
      city: 'Kocaeli',
    },
    {
      first_name: 'Canan',
      last_name: 'Kara',
      city: 'Van',
    }
  ];

  selectedCustomer: any = null;
  date1: any = null;
  date2: any = null;
  date3: any = null;

  dateTime12h: Date = new Date();
  dateTime24h: Date = new Date();
  time: Date = new Date();

  visible: boolean = false;

  basicData = {
    labels: ['mart', 'nisan', 'mayıs'],
    datasets: [
      {
        label: 'Sales',
        data: [10, 20, 30],
        backgroundColor: 'orange',
      }
    ]
  };
  basicOptions = {
    responsive: true,
    scales: {
      x: {
        beginAtZero: true
      },
      y: {
        beginAtZero: true
      }
    }
  };

  constructor(private messageService: MessageService) {
  }

  ngOnInit(): void {
    console.log("PERSONEL VERISI BACKEND TARAFINDAN ÇEIKILIYOR...");
  }

  savePerson() {
    console.log("Personel kayıt edildi.");
    console.log("Number value: " + this.numberValue);
    console.log("CheckBox: " + this.chechkBoxValue);
    console.log("Seçili şehir: " + this.selectedCity);
    console.log("Password: " + this.password);
    console.log("Seçili yemek: " + this.selectedFood);
    console.log("Toogle switch: " + this.checked);
    console.log("DatePickerIcon:" + this.date1);
    console.log("DatePickerTime:" + this.dateTime24h);
  }


  kalpButonu() {
    console.log("Kalp Butonuna Tıklandı");
  }

  save() {
    console.log("Personel save");
  }

  delete() {
    console.log("Personel save");
  }

  showDialog() {
    this.visible = true;
  }

  //app.module.ts de prooviderse MessageServiceyi eklemeliyiz.ardından app.component.ts ye compamnentlere provicders: başlığı altında ekleyip son olarak consturcutorda this liyoruz.

  show() {
    console.log("Personel show");
    this.messageService.add({
      severity: 'info',
      summary: 'Başarılı',
      detail: "İşlem başarıyla tamamlandı"
    });
  }

  showSuccess() {
    this.messageService.add({
      severity: 'success',
      summary: 'Başarılı',
      detail: 'Başarılı bir şekilde kayıt yapıldı'
    })
  }

  showInfo() {
    this.messageService.add({
      severity: 'info',
      summary: 'Bilgilendirme',
      detail: "Personelle ilgili bilgi verilmektedir"
    })
  }

  showWarn() {
    this.messageService.add({
      severity: 'warn',
      summary: 'Eksik Bilgi',
      detail: 'Lütfen tüm zorunlu alanları doldurunuz.'
    })
  }

  showError() {
    this.messageService.add({
      severity: 'error',
      summary: 'Hata',
      detail: "Kayıt gerçekleştirilemedi"

    })
  }


}
