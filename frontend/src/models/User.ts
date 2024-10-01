import type { Role } from '@/models/Role'

export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
  roles: Role[];
}

export interface getUser {
  userId: number;
  username: string;
  email: string;
  enabled: boolean;
  roles: Role[];
}

export interface NewUser {
  username: string;
  email: string;
  password: string;
}

export interface LoginUser {
  password: string;
  email: string;
}
