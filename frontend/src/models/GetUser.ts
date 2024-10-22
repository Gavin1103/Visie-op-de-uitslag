import type { Role } from '@/models/Role'

export class GetUser {
  id: number;
  username: string;
  email: string;
  enabled: boolean;
  roles: Role[];

  constructor(id: number, username: string, email: string, enabled: boolean, roles: Role[]) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.enabled = enabled;
    this.roles = roles;
  }
}
