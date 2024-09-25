import { DatabaseService } from './DatabaseService';
import type { NewUser, User } from '@/models/User'

export class UserService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async getUsers(): Promise<User[]> {
    return await this.dbService.get<User[]>('users');
  }

  async createUser(user: NewUser): Promise<NewUser> {
    return await this.dbService.post<NewUser>('authentication/register', user);
  }

  async getUserById(userId: number): Promise<User> {
    return await this.dbService.get<User>(`users/${userId}`);
  }
}
