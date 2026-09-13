import {Injectable} from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  baseUrl: any = 'http://localhost:8080/rest/api';

  constructor() {
  }

  async findByPersonelId(id: number) {
    const response = await axios.get(this.baseUrl + '/adres/get-personel-id/' + id).then(function (response) {
      return response.data.data;
    });
    return response;
  }

  async delete(id: number) {
    const response = await axios.delete(this.baseUrl + '/adres/delete/' + id).then(function (response) {
      return response.data;
    })
    return response;
  }

  async update(id: number, adres: any) {
    const response = await axios.put(this.baseUrl + '/adres/update/' + id, adres).then(function (response) {
      return response.data;
    })
    return response;
  }


  async save(adres: any) {
    const response = await axios.post(this.baseUrl + '/adres/save', adres).then(function (response) {
      return response.data;
    })
    return response;
  }


}
