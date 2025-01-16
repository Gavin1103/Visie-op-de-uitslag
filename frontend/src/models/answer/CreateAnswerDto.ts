export class CreateAnswerDto {
  message: string;
  topicId: number;

  constructor(message: string, topicId: number) {
    this.message = message;
    this.topicId = topicId;
  }
}