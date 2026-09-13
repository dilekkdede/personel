import {NgModule} from '@angular/core';
import {BrowserModule, provideClientHydration, withEventReplay} from '@angular/platform-browser';
import {providePrimeNG} from 'primeng/config';
import Aura from '@primeng/themes/aura';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ButtonModule} from 'primeng/button';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {FormsModule} from '@angular/forms';
import {InputNumberModule} from 'primeng/inputnumber';
import {InputTextModule} from "primeng/inputtext";
import {FloatLabel} from 'primeng/floatlabel';
import {CheckboxModule} from 'primeng/checkbox';
import {InputGroup} from 'primeng/inputgroup';
import {InputGroupAddonModule} from 'primeng/inputgroupaddon';
import {Listbox} from 'primeng/listbox';
import {PasswordModule} from 'primeng/password';
import {Select} from 'primeng/select';
import {ToggleSwitchModule} from 'primeng/toggleswitch';
import {TableModule} from 'primeng/table';
import {Card} from 'primeng/card';
import {DatePicker} from 'primeng/datepicker';
import {PanelModule} from 'primeng/panel';
import {TabsModule} from 'primeng/tabs';
import {Toast} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {Dialog} from 'primeng/dialog';
import {ChartModule, UIChart} from 'primeng/chart';
import {InputMask} from 'primeng/inputmask';
import {FileUpload} from 'primeng/fileupload';
import {CalismalarComponent} from './Calismalar/calismalar.component';
import {Ripple} from 'primeng/ripple';
import {TabPanel} from 'primeng/tabview';
import {Textarea} from 'primeng/textarea';
import {CityComponent} from './city/city.component';
import {AddressComponent} from './address/address.component';
import {PersonelComponent} from './personel/personel.component';
import {UnitComponent} from './unit/unit.component';
import {ReportComponent} from './report/report.component';
import {EducationComponent} from './education/education.component';
import {ImageModule} from 'primeng/image';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {DropdownModule} from 'primeng/dropdown';
import {MainComponent} from './main/main.component';
import {SplitterModule} from 'primeng/splitter';
import {ConfirmationService} from 'primeng/api';
import {ConfirmDialog, ConfirmDialogModule} from 'primeng/confirmdialog';
import { ContactComponent } from './contact/contact.component';

@NgModule({
  declarations: [
    AppComponent,
    CalismalarComponent,
    CityComponent,
    AddressComponent,
    PersonelComponent,
    UnitComponent,
    ReportComponent,
    EducationComponent,
    MainComponent,
    ContactComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ButtonModule,
    InputNumberModule,
    InputTextModule,
    FloatLabel,
    CheckboxModule,
    InputGroup,
    Listbox,
    PasswordModule,
    InputGroupAddonModule,
    Select,
    ToggleSwitchModule,
    TableModule,
    Card,
    DatePicker,
    PanelModule,
    TabsModule,
    Dialog,
    UIChart,
    Toast,
    InputMask,
    FileUpload,
    Ripple,
    Textarea,
    ImageModule,
    ScrollPanelModule,
    DropdownModule,

    SplitterModule,
    ChartModule,
    ConfirmDialogModule,
  ],
  providers: [
    MessageService,
    ConfirmationService,
    provideAnimationsAsync(),
    providePrimeNG({
      theme: {
        preset: Aura
      }
    }),
    provideClientHydration(withEventReplay())
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
