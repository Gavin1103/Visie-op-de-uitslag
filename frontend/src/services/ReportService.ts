import { DatabaseService } from '@/services/DatabaseService'
import type { Report } from '@/models/report/Report'

export class ReportService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  public async getUnhandledReports(): Promise<Report[]> {
    return await this.dbService.get<Report[]>("report/unhandled")
  }
}