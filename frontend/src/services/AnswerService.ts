import { DatabaseService } from "@/services/DatabaseService";
import type { Answer } from "@/models/forum/Answer";
import type { CreateAnswerDto } from "@/models/forum/CreateAnswerDto";

export class AnswerService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async addAnswer(createAnswerDto: CreateAnswerDto): Promise<Answer> {
    return await this.dbService.post<Answer>('answer/add', createAnswerDto);
  }

  async deleteAnswer(id: number): Promise<void> {
    return await this.dbService.delete<void>(`answer/delete/${id}`);
  }

  async getAnswerById(id: number): Promise<Answer> {
    return await this.dbService.get<Answer>(`answer/${id}`);
  }

  async getAnswersByTopicId(topicId: number): Promise<Answer[]> {
    return await this.dbService.get<Answer[]>(`answer/topic/${topicId}`);
  }
}