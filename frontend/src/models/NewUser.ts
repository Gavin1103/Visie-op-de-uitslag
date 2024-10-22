import type { User } from '@/models/User';

export class NewUser {
  id?: number;
  username: string;
  email: string;
  password: string;
  roleName?: string;

  constructor(
    username: string,
    email: string,
    password: string,
    roleName: string = 'USER',
    id?: number
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.roleName = roleName;
  }

  static fromUser(user: User): NewUser {
    const roleName = user.roles?.[0]?.name ?? 'USER';
    return new NewUser(user.username, user.email, user.password, roleName, user.id);
  }
}
