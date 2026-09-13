import {Injectable} from '@angular/core';
import axios from 'axios';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  baseUrl = environment.apiUrl;


  constructor() {
  }


  async findByIdPersonelContact(id: number) {
    const response = await axios.get(this.baseUrl + '/contact/get-personel-ıd-contact/' + id).then(function (response) {
      return response.data.data;
    });
    return response;
  }


  async delete(id: number) {
    const response = await axios.delete(this.baseUrl + '/contact/delete/' + id).then(function (response) {
      return response.data;
    });
    return response;
  }

  async save(contact: any) {
    const response = await axios.post(this.baseUrl + '/contact/save/', contact).then(function (response) {
      return response.data;
    })
    return response;
  }

  async update(id: number, contact: any) {
    const response = await axios.put(this.baseUrl + '/contact/update/' + id, contact).then(function (response) {
      return response.data;
    })
    return response;
  }

}
