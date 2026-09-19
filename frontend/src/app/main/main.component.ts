import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {PersonelService} from '../services/personel.service';
import {CityService} from '../services/city.service';
import {UnitService} from '../services/unit.service';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-main',
  standalone: false,
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit {
  personelCount = 0;
  cityCount = 0;
  unitCount = 0;
  activeCount = 0;
  onLeaveCount = 0;
  leftCount = 0;
  latest: any[] = [];
  withoutUnit: any[] = [];
  recent: any[] = [];
  unitChart: any;
  cityChart: any;
  chartOptions: any;
  pieOptions: any;
  hasUnitData = false;
  hasCityData = false;

  constructor(private personelService: PersonelService,
              private cityService: CityService,
              private unitService: UnitService,
              private router: Router,
              public auth: AuthService) {
  }

  async ngOnInit() {
    const [page, cities, units, unitCounts, cityCounts, dash] = await Promise.all([
      this.personelService.findAll(0, 1),
      this.cityService.findAll(),
      this.unitService.findAll(),
      this.personelService.countByUnit(),
      this.personelService.countByCity(),
      this.personelService.dashboard()
    ]);

    this.personelCount = page?.totalElements ?? 0;
    this.cityCount = cities?.length ?? 0;
    this.unitCount = units?.length ?? 0;
    this.latest = dash?.latest || [];
    this.withoutUnit = dash?.withoutUnit || [];
    this.recent = dash?.recent || [];
    this.activeCount = dash?.activeCount ?? 0;
    this.onLeaveCount = dash?.onLeaveCount ?? 0;
    this.leftCount = dash?.leftCount ?? 0;

    const unitRows = unitCounts || [];
    const cityRows = cityCounts || [];
    this.hasUnitData = unitRows.length > 0;
    this.hasCityData = cityRows.length > 0;

    this.unitChart = {
      labels: unitRows.map((item: any) => item.code),
      datasets: [{
        label: 'Personel sayısı',
        data: unitRows.map((item: any) => item.personSize),
        backgroundColor: '#22d3ee',
        borderRadius: 8
      }]
    };

    this.cityChart = {
      labels: cityRows.map((item: any) => item.name),
      datasets: [{
        data: cityRows.map((item: any) => item.personSize),
        backgroundColor: ['#22d3ee', '#34d399', '#818cf8', '#f59e0b', '#f43f5e', '#0ea5e9']
      }]
    };

    const tick = '#93a4bb';
    this.chartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {legend: {display: false}},
      scales: {
        x: {ticks: {color: tick}, grid: {display: false}},
        y: {ticks: {color: tick}, grid: {color: 'rgba(148,163,184,0.2)'}, beginAtZero: true}
      }
    };

    this.pieOptions = {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {legend: {position: 'bottom', labels: {color: tick}}}
    };
  }

  open(personel: any) {
    this.router.navigate(['/personel', personel.id]);
  }
}
