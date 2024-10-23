import type { Role } from '@/models/Role'

export class User {
  id: number;
  username: string;
  email: string;
  password: string;
  roles: Role[];
  enabled?: boolean;

  constructor(id: number, username: string, email: string, password: string, roles: Role[]) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.roles = roles;
  }
}