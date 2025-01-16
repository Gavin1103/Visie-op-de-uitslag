import type { User } from '@/models/user/User'

export class GetTopic {
  id: number;
  statement: string;
  createdAt: Date;
  user: User;

  constructor(id: number, statement: string, createdAt: Date) {
    this.id = id;
    this.statement = statement;
    this.createdAt = createdAt;
  }
}
