export class CreateTopic {
  statement: string;
  message: string;

  constructor(
    statement: string,
    message: string,
  ) {
    this.statement = statement;
    this.message = message;
  }
}
