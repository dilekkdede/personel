import {Injectable} from '@angular/core';
import axios from 'axios';
import {environment} from '../../environments/environment';
import {apiBody} from './api-client';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  baseUrl = environment.apiUrl;

  constructor() {
  }

  async findByPersonelId(id: number) {
    const body = await apiBody(axios.get(this.baseUrl + '/adres/get-personel-id/' + id));
    return body.data;
  }

  async delete(id: number) {
    return apiBody(axios.delete(this.baseUrl + '/adres/delete/' + id));
  }

  async update(id: number, adres: any) {
    return apiBody(axios.put(this.baseUrl + '/adres/update/' + id, adres));
  }

  async save(adres: any) {
    return apiBody(axios.post(this.baseUrl + '/adres/save', adres));
  }

}
