import { DatabaseService } from './DatabaseService';
import type { NewUser } from '@/models/user/NewUser'
import type { GetUser } from '@/models/user/GetUser'
import type { LoginUser } from '@/models/user/LoginUser'
import type { User } from '@/models/user/User'
import type {UserProfile} from "@/models/user/UserProfile";

export class UserService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async getUsers(): Promise<GetUser[]> {
    return await this.dbService.get<GetUser[]>('user/');
  }

  async createUser(user: NewUser): Promise<NewUser> {
    return await this.dbService.post<NewUser>('auth/register', user);
  }

  async createAsAdmin(user: NewUser): Promise<NewUser> {
    return await this.dbService.post<NewUser>('auth/create-user', user);
  }

  async updateUser(user: NewUser): Promise<NewUser> {
    return await this.dbService.put<NewUser>(`user/${user.id}`, user);
  }

  async deleteUser(userId: string): Promise<void> {
    await this.dbService.delete(`user/${userId}`);
  }

  async authenticateUser(user: LoginUser): Promise<LoginUser> {
    return await this.dbService.post<LoginUser>('auth/authenticate', user);
  }

  async getUserById(userId: number): Promise<User> {
    return await this.dbService.get<User>(`user/${userId}`);
  }

  async getUserByToken(token: string): Promise<UserProfile> {
    return await this.dbService.get<User>(`user/token/${token}`);
  }


  async currentUserIsAdmin(): Promise<boolean> {
    const result: any = await this.dbService.get<boolean>('auth/is-admin');
    return result.status !== 403;
  }

  async confirmAccount(token: string): Promise<boolean> {
    return await this.dbService.get<boolean>(`auth/confirm-account/${token}`);
  }
}
