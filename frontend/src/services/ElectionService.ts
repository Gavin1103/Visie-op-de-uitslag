import { DatabaseService } from '@/services/DatabaseService'
// import { Party } from '@/models/Party'

export class ElectionService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async getParties(): Promise<String> {
    return await this.dbService.get<String>("election/parties")
  }
}