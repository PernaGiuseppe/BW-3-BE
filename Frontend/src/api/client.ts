import axios from 'axios';
import { API_BASE_URL, TOKEN_STORAGE_KEY } from '../config';

export const api = axios.create({
  baseURL: API_BASE_URL,
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem(TOKEN_STORAGE_KEY);
  if (token) {
    config.headers = config.headers || {};
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});

export interface ApiError {
  status: number;
  message: string;
}

export const extractError = (err: unknown): ApiError => {
  if (axios.isAxiosError(err)) {
    return {
      status: err.response?.status || 0,
      message: (err.response?.data as any)?.message || err.message,
    };
  }
  return { status: 0, message: 'Unexpected error' };
};