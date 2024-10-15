import { DatabaseService } from '@/services/DatabaseService'
import type { Party } from '@/models/Party'
// import { Party } from '@/models/Party'

export class ElectionService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async getParties(): Promise<Party[]> {
    return await this.dbService.get<Party[]>("election/parties")
  }
}