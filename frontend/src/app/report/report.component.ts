import {Component, OnInit} from '@angular/core';
import {PersonelService} from '../services/personel.service';

@Component({
  selector: 'app-report',
  standalone: false,
  templateUrl: './report.component.html',
  styleUrl: './report.component.css'
})
export class ReportComponent implements OnInit {
  unitRows: any[] = [];
  cityRows: any[] = [];

  constructor(private personelService: PersonelService) {
  }

  async ngOnInit() {
    this.unitRows = await this.personelService.countByUnit() || [];
    this.cityRows = await this.personelService.countByCity() || [];
  }
}
