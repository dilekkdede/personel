import {Injectable} from '@angular/core';
import axios from 'axios';
import {environment} from '../../environments/environment';
import {apiBody} from './api-client';

@Injectable({
  providedIn: 'root'
})
export class CityService {
  baseUrl = environment.apiUrl;

  constructor() {
  }

  async findAll() {
    const body = await apiBody(axios.get(this.baseUrl + '/city/get-all'));
    return body.data;
  }

  async save(city: any) {
    return apiBody(axios.post(this.baseUrl + '/city/save', city));
  }

  findById(id: number) {
    console.log(id);
  }

  async delete(id: number) {
    return apiBody(axios.delete(this.baseUrl + '/city/delete/' + id));
  }

  async update(id: number, city: any) {
    return apiBody(axios.put(this.baseUrl + '/city/update/' + id, city));
  }
}
