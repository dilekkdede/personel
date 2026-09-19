import {Injectable} from '@angular/core';
import axios from 'axios';
import {environment} from '../../environments/environment';
import {apiBody} from './api-client';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  baseUrl = environment.apiUrl;

  constructor() {
  }

  async findByIdPersonelContact(id: number) {
    const body = await apiBody(axios.get(this.baseUrl + '/contact/get-personel-id-contact/' + id));
    return body.data;
  }

  async delete(id: number) {
    return apiBody(axios.delete(this.baseUrl + '/contact/delete/' + id));
  }

  async save(contact: any) {
    return apiBody(axios.post(this.baseUrl + '/contact/save/', contact));
  }

  async update(id: number, contact: any) {
    return apiBody(axios.put(this.baseUrl + '/contact/update/' + id, contact));
  }

}
