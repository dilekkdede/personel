import {Component, OnInit} from '@angular/core';
import {MessageService} from 'primeng/api';
import {PrimeNG} from 'primeng/config';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css',
  providers: [MessageService]
})
export class AppComponent implements OnInit {
  constructor(private primeng: PrimeNG) {
  }

  ngOnInit() {
    this.primeng.ripple.set(true);
  }

  menuFunction() {
    console.log("Buton tıklandı");
  }


}

