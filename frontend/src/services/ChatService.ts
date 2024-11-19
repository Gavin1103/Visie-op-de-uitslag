import { DatabaseService } from '@/services/DatabaseService'
import type { CreateReport } from '@/models/chat/CreateReport'

export class ChatService {

  private DbService: DatabaseService;

  constructor() {
    this.DbService = new DatabaseService();
  }

  public async ReportMessage(reason: CreateReport, id: number | undefined): Promise<void> {
    await this.DbService.post(`chat/report/${id}`, reason)
  }
}