import axios from 'axios';

export async function apiBody<T = any>(request: Promise<any>): Promise<T> {
  try {
    const response = await request;
    return response.data;
  } catch (error: any) {
    if (error.response?.data) {
      return error.response.data;
    }
    throw error;
  }
}
