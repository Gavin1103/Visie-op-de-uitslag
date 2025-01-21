export class CreateTopic {
  statement: string;
  message: string;
  id?: number;

  constructor(
    statement: string,
    message: string,
  ) {
    this.statement = statement;
    this.message = message;
  }
}
