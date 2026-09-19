import {Injectable} from '@angular/core';
import axios from 'axios';
import {environment} from '../../environments/environment';
import {apiBody} from './api-client';

@Injectable({
  providedIn: 'root'
})
export class PersonelService {

  baseUrl = environment.apiUrl;

  constructor() {
  }

  async findAll() {
    const body = await apiBody(axios.get(this.baseUrl + '/personel/list'));
    return body.data;
  }

  async save(personel: any) {
    return apiBody(axios.post(this.baseUrl + '/personel/save', personel));
  }

  async delete(id: number) {
    return apiBody(axios.delete(this.baseUrl + '/personel/delete/' + id));
  }

  async update(id: number, personel: any) {
    return apiBody(axios.put(this.baseUrl + '/personel/update/' + id, personel));
  }

  async countByUnit() {
    const body = await apiBody(axios.get(this.baseUrl + '/personel/countByUnit'));
    return body.data;
  }

  async countByCity() {
    const body = await apiBody(axios.get(this.baseUrl + '/personel/countByCity'));
    return body.data;
  }

}
