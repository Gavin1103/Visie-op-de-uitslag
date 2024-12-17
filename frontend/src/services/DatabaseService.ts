import axios from 'axios';
import { CookieService } from '@/services/CookieService'

export class DatabaseService {
  private baseUrl: string;
  private token: string | null;
  private cookieService = new CookieService();

  constructor() {
    this.baseUrl = import.meta.env.VITE_API_BASE_URL;
    this.token = this.cookieService.getCookie(this.cookieService.accessTokenAlias);
  }

  public getBaseUrl(): string{
    return this.baseUrl;
  }

  private getHeaders() {
    return {
      headers: {
        Authorization: this.token ? `Bearer ${this.token}` : '',
        'Content-Type': 'application/json'
      },
    };
  }

  async get<T>(endpoint: string): Promise<T> {
    try {
      const response = await axios.get(`${this.baseUrl}/${endpoint}`, this.getHeaders());
      return response.data;
    } catch (error: any) {
      if(error.status === 403) {
        return error;
      }
      console.error('Error in GET request:', error);
      throw error;
    }
  }

  async post<T>(endpoint: string, payload: T): Promise<T> {
    try {
      const response = await axios.post(`${this.baseUrl}/${endpoint}`, payload, this.getHeaders());
      return response.data;
    } catch (error) {
      console.error('Error in POST request:', error);
      throw error;
    }
  }

  async put<T>(endpoint: string, payload: T): Promise<T> {
    try {
      const response = await axios.put(`${this.baseUrl}/${endpoint}`, payload, this.getHeaders());
      return response.data;
    } catch (error) {
      console.error('Error in PUT request:', error);
      throw error;
    }
  }

  async delete(endpoint: string): Promise<void> {
    try {
      await axios.delete(`${this.baseUrl}/${endpoint}`, this.getHeaders());
    } catch (error) {
      console.error('Error in DELETE request:', error);
      throw error;
    }
  }
}
