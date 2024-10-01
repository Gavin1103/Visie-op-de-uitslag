import { DatabaseService } from './DatabaseService';
import type { LoginUser, NewUser, User } from '@/models/User'

export class UserService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async getUsers(): Promise<User[]> {
    return await this.dbService.get<User[]>('users');
  }

  async createUser(user: NewUser): Promise<NewUser> {
    return await this.dbService.post<NewUser>('auth/register', user);
  }

  async authenticateUser(user: LoginUser): Promise<LoginUser> {
    return await this.dbService.post<LoginUser>('auth/authenticate', user);
  }

  async getUserById(userId: number): Promise<User> {
    return await this.dbService.get<User>(`users/${userId}`);
  }
}
