import { DatabaseService } from '@/services/DatabaseService'
import type { fullParty, Party } from '@/models/Party'

export class ElectionService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async getParties(): Promise<Party[]> {
    return await this.dbService.get<Party[]>("election/parties")
  }

  async getPartyById(id: number): Promise<fullParty> {
    return await this.dbService.get<fullParty>(`election/party/${id}`);
  }
}