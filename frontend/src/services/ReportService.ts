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

  public async handleReport(report: Report | null, disableUser: boolean, deleteMessage: boolean): Promise<void> {
    await this.dbService.post<Report | null>(`report/handle/${disableUser}/${deleteMessage}`, report);
  }
}