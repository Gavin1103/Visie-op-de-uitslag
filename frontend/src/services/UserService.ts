import { DatabaseService } from './DatabaseService';
import type { getUser, LoginUser, NewUser, User } from '@/models/User'

export class UserService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async getUsers(): Promise<getUser[]> {
    return await this.dbService.get<getUser[]>('auth/');
  }

  async createUser(user: NewUser): Promise<NewUser> {
    return await this.dbService.post<NewUser>('auth/register', user);
  }

  async createAsAdmin(user: NewUser): Promise<NewUser> {
    return await this.dbService.post<NewUser>('auth/create-user', user);
  }

  async updateUser(user: User): Promise<User> {
    return await this.dbService.put<User>(`user/${user.id}`, user);
  }

  async deleteUser(userId: string): Promise<void> {
    await this.dbService.delete(`user/${userId}`);
  }

  async authenticateUser(user: LoginUser): Promise<LoginUser> {
    return await this.dbService.post<LoginUser>('auth/authenticate', user);
  }

  async getUserById(userId: number): Promise<User> {
    return await this.dbService.get<User>(`users/${userId}`);
  }

  async currentUserIsAdmin(): Promise<boolean> {
    const result: any = await this.dbService.get<boolean>('auth/is-admin');
    return result.status !== 403;
  }

  async confirmAccount(token: string): Promise<boolean> {
    return await this.dbService.get<boolean>(`auth/confirm-account/${token}`);
  }
}
