import {Injectable} from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class PersonelService {

  baseUrl: any = 'http://localhost:8080/rest/api';

  constructor() {
  }

  async findAll() {
    const response = await axios.get(this.baseUrl + '/personel/list').then(function (response) {
      return response.data.data;
    })
    return response;

  }

  async save(personel: any) {
    const response = await axios.post(this.baseUrl + '/personel/save', personel).then(function (response) {
      return response.data;
    })
    return response;
  }

  async delete(id: number) {
    const response = await axios.delete(this.baseUrl + '/personel/delete/' + id).then(function (response) {
      return response.data;
    })
    return response;
  }

  async update(id: number, personel: any) {
    const response = await axios.put(this.baseUrl + '/personel/update/' + id, personel).then(function (response) {
      return response.data;
    })
    return response;
  }

  async countByUnit() {
    const response = await axios.get(this.baseUrl + '/personel/countByUnit');
    return response.data.data;

  }


  async countByCity() {
    const response = await axios.get(this.baseUrl + '/personel/countByCity');
    return response.data.data;
  }

}
