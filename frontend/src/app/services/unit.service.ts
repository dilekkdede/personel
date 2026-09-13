import {Injectable} from '@angular/core';
import axios from 'axios';


@Injectable({
  providedIn: 'root'
})
export class UnitService {
  baseUrl: any = 'http://localhost:8080/rest/api';

  constructor() {
  }

  async findAll() {
    //axios: API çağrısı yapmak için kullanılan bir kütüphanedir
    const response = await axios.get(this.baseUrl + '/unit/get-all').then(function (response) {
      return response.data.data;
    })
    return response;
  }

  async save(unit: any) {
    const response = await axios.post(this.baseUrl + '/unit/save', unit).then(function (response) {
      return response.data;
    })
    return response;

  }

  findById(id: number) {
    console.log(id);
  }

  async delete(id: number) {
    const response = await axios.delete(this.baseUrl + '/unit/delete/' + id).then(function (response) {
      return response.data;
    })
    return response;
  }

  async  update(id: number, unit: any) {
    const response = await axios.put(this.baseUrl + '/unit/update/' + id, unit).then(function (response) {
      return response.data;
    })
    return response;
  }
}
