import {Injectable} from '@angular/core';
import axios from 'axios';
import {environment} from '../../environments/environment';
import {apiBody} from './api-client';

@Injectable({
  providedIn: 'root'
})
export class PersonelService {

  baseUrl = environment.apiUrl;

  async findAll(page = 0, size = 10, filters: any = {}) {
    const body = await apiBody(axios.get(this.baseUrl + '/personel/list', {
      params: {page, size, ...filters}
    }));
    return body.data;
  }

  async findById(id: number) {
    const body = await apiBody(axios.get(this.baseUrl + '/personel/get-id/' + id));
    return body;
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

  async changeStatus(id: number, employmentStatus: string) {
    return apiBody(axios.put(this.baseUrl + '/personel/status/' + id, null, {
      params: {employmentStatus}
    }));
  }

  async bulkStatus(ids: number[], employmentStatus: string) {
    return apiBody(axios.put(this.baseUrl + '/personel/bulk-status', {ids, employmentStatus}));
  }

  async dashboard() {
    const body = await apiBody(axios.get(this.baseUrl + '/personel/dashboard'));
    return body.data;
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
