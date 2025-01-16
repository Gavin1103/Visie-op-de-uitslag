import { DatabaseService } from "@/services/DatabaseService";
import type { CreateAnswerDto } from '@/models/answer/CreateAnswerDto'

export class AnswerService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async addAnswer(createAnswerDto: CreateAnswerDto): Promise<any> {
    return await this.dbService.post<any>('answer/add', createAnswerDto);
  }

  async getAnswersByTopicId(topicId: number): Promise<any[]> {
    return await this.dbService.get<any[]>(`answer/topic/${topicId}`);
  }
}