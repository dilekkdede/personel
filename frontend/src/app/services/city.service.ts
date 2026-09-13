import {Injectable} from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class CityService {
  baseUrl: any = 'http://localhost:8080/rest/api';

  constructor() {
  }


  async findAll() {
    const response = await axios.get(this.baseUrl + '/city/get-all').then(function (response) {
      return response.data.data;
    })
    return response;

  }

  async save(city: any) {
    const response = await axios.post(this.baseUrl + '/city/save', city).then(function (response) {
      return response.data;
    })
    return response;
  }

  findById(id: number) {
    console.log(id);
  }

  async delete(id: number) {
    const response = await axios.delete(this.baseUrl + '/city/delete/' + id).then(function (response) {
      return response.data;
    })
    return response;
  }


  async update(id: number, city: any) {
    const response = await axios.put(this.baseUrl + '/city/update/' + id, city).then(function (response) {
      return response.data;
    })
    return response;
  }
}
