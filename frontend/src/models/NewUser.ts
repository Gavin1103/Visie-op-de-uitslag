import type { User } from '@/models/User';

export class NewUser {
  id?: number;
  username: string;
  email: string;
  password: string;
  roleName?: string;
  enabled?: boolean;

  constructor(
    username: string,
    email: string,
    password: string,
    roleName: string = 'USER',
    id?: number,
    enabled: boolean = false
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.roleName = roleName;
    this.enabled = enabled;
  }
}
