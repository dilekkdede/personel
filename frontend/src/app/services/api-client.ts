import axios from 'axios';

export async function apiBody<T = any>(request: Promise<any>): Promise<T> {
  try {
    const response = await request;
    return response.data;
  } catch (error: any) {
    if (typeof window !== 'undefined' && error.response?.status === 401 && !window.location.pathname.includes('/login')) {
      localStorage.removeItem('personel_token');
      localStorage.removeItem('personel_user');
      window.location.href = '/login';
    }
    if (error.response?.data) {
      return error.response.data;
    }
    throw error;
  }
}

axios.interceptors.request.use((config) => {
  const token = typeof localStorage === 'undefined' ? null : localStorage.getItem('personel_token');
  if (token) {
    config.headers = config.headers || {};
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});
