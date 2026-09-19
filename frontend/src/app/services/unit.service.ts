import {Injectable} from '@angular/core';
import axios from 'axios';
import {environment} from '../../environments/environment';
import {apiBody} from './api-client';

@Injectable({
  providedIn: 'root'
})
export class UnitService {
  baseUrl = environment.apiUrl;

  constructor() {
  }

  async findAll() {
    const body = await apiBody(axios.get(this.baseUrl + '/unit/get-all'));
    return body.data;
  }

  async save(unit: any) {
    return apiBody(axios.post(this.baseUrl + '/unit/save', unit));
  }

  findById(id: number) {
    console.log(id);
  }

  async delete(id: number) {
    return apiBody(axios.delete(this.baseUrl + '/unit/delete/' + id));
  }

  async update(id: number, unit: any) {
    return apiBody(axios.put(this.baseUrl + '/unit/update/' + id, unit));
  }
}
