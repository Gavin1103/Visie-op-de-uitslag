import type { Role } from '@/models/Role'

export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
  roles: Role[];
}

export interface getUser {
  id: number;
  username: string;
  email: string;
  enabled: boolean;
  roles: Role[];
}

export interface NewUser {
  username: string;
  email: string;
  password: string;
  roleName?: Role;
}

export enum Role {
  ADMIN,
  PARTYMEMBER,
  USER,
  VERIFIED,
}

export interface LoginUser {
  password: string;
  email: string;
}
