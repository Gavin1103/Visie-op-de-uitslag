import axios from 'axios';

export class DatabaseService {
  private baseUrl: string;

  constructor() {
    this.baseUrl = import.meta.env.VITE_API_BASE_URL;
  }

  async get<T>(endpoint: string): Promise<T> {
    try {
      const response = await axios.get(`${this.baseUrl}/${endpoint}`);
      return response.data;
    } catch (error) {
      console.error('Error in GET request:', error);
      throw error;
    }
  }

  async post<T>(endpoint: string, payload: T): Promise<T> {
    try {
      const response = await axios.post(`${this.baseUrl}/${endpoint}`, payload);
      return response.data;
    } catch (error) {
      console.error('Error in POST request:', error);
      throw error;
    }
  }

  async put<T>(endpoint: string, payload: T): Promise<T> {
    try {
      const response = await axios.put(`${this.baseUrl}/${endpoint}`, payload);
      return response.data;
    } catch (error) {
      console.error('Error in PUT request:', error);
      throw error;
    }
  }

  async delete(endpoint: string): Promise<void> {
    try {
      await axios.delete(`${this.baseUrl}/${endpoint}`);
    } catch (error) {
      console.error('Error in DELETE request:', error);
      throw error;
    }
  }
}
