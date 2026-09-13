import {Component, OnInit} from '@angular/core';
import {PersonelService} from '../services/personel.service';

@Component({
  selector: 'app-main',
  standalone: false,
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit {

  basicData: any;
  basicOptions: any;
  data: any;
  options: any;


  async ngOnInit() {

    const unitCounts: any[] = await this.personelService.countByUnit();

    const labels = unitCounts.map(item => item.code);
    const data = unitCounts.map(item => item.personSize);

    this.basicData = {
      labels: labels,
      datasets: [
        {
          label: 'Personel Sayısı' ,
          data: data,
          backgroundColor: [
            '#FF6384', // Pembe
            '#36A2EB', // Mavi
            '#FFCE56', // Sarı
            '#4BC0C0', // Turkuaz
            '#9966FF', // Mor
            '#FF9F40', // Turuncu
            '#C9CBCF', // Açık gri
            '#00A36C', // Zümrüt yeşili
            '#FF6F61', // Mercan kırmızısı
            '#D4E157'  // Açık sarı-yeşil
          ],
        }
      ]
    };

    this.basicOptions = {
      responsive: true,
      plugins: {
        legend: {
          labels: {
            color: '#495057',
          },
        },
      },
      scales: {
        x: {
          ticks: {
            color: "#495057",
          },
          grid: {
            color: "#ebedef",
          },
        },
        y: {
          ticks: {
            color: "#495057",
          },
          grid: {
            color: "#ebedef",
          },
        },
      },
    };


    const cityCounts: any[] = await this.personelService.countByCity();

    const labels2 = cityCounts.map(item => item.name);
    const data2 = cityCounts.map(item => item.personSize);


    this.data = {
      labels:labels2,
      datasets: [
        {
          data: data2,
          backgroundColor: [
            '#FF6384', // Pembe
            '#36A2EB', // Mavi
            '#FFCE56', // Sarı
            '#4BC0C0', // Turkuaz
            '#9966FF', // Mor
            '#FF9F40', // Turuncu
            '#C9CBCF', // Açık gri
            '#00A36C', // Zümrüt yeşili
            '#FF6F61', // Mercan kırmızısı
            '#D4E157'  // Açık sarı-yeşil
          ],
          hoverBackgroundColor: [
            '#FF6384', // Pembe
            '#36A2EB', // Mavi
            '#FFCE56', // Sarı
            '#4BC0C0', // Turkuaz
            '#9966FF', // Mor
            '#FF9F40', // Turuncu
            '#C9CBCF', // Açık gri
            '#00A36C', // Zümrüt yeşili
            '#FF6F61', // Mercan kırmızısı
            '#D4E157'  // Açık sarı-yeşil
          ]
        }
      ]
    };

    this.options = {
      responsive: true,
      legend: {
        position: 'bottom',
        labels: {
          color: '#495057'
        }
      },
      plugins: {
        datalabels: {
          display: true,
          color: '#fff'
        }
      },
      title: {
        display: true,          // Başlık gösterilsin
        text: 'Şehir Bazlı Personel Dağılımı', // Başlık metni
        font: {
          size: 18
        }
      }
    };

  }

  constructor(private personelService: PersonelService) {
  }

  async countByUnit() {
    const sonuc = await this.personelService.countByUnit();
    console.log("Servisten gelen veri:", sonuc);
  }

  async countByCity() {

    const sonuc = await this.personelService.countByCity();
    console.log("Servisten gelen veri:", sonuc);
  }


}
