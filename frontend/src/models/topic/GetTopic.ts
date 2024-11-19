export class GetTopic {
  id: number;
  statement: string;
  createdAt: string;

  constructor(id: number, statement: string, createdAt: string) {
    this.id = id;
    this.statement = statement;
    this.createdAt = createdAt;
  }
}
