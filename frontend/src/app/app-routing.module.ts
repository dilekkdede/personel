import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CityComponent} from './city/city.component';
import {UnitComponent} from './unit/unit.component';
import {ReportComponent} from './report/report.component';
import {PersonelComponent} from './personel/personel.component';
import {PersonelDetailComponent} from './personel-detail/personel-detail.component';
import {MainComponent} from './main/main.component';
import {LoginComponent} from './login/login.component';
import {ShellComponent} from './shell/shell.component';
import {SettingsComponent} from './settings/settings.component';
import {AuthGuard} from './auth.guard';

export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {
    path: '',
    component: ShellComponent,
    canActivate: [AuthGuard],
    children: [
      {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
      {path: 'dashboard', component: MainComponent},
      {path: 'personel', component: PersonelComponent},
      {path: 'personel/:id', component: PersonelDetailComponent},
      {path: 'units', component: UnitComponent},
      {path: 'cities', component: CityComponent},
      {path: 'reports', component: ReportComponent},
      {path: 'settings', component: SettingsComponent},
    ]
  },
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
