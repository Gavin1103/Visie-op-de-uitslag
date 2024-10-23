import { DatabaseService } from '@/services/DatabaseService'
import type { Party, PartyWithCandidates } from '@/models/Party'

export class ElectionService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async getParties(): Promise<Party[]> {
    return await this.dbService.get<Party[]>("election/parties")
  }

  async getPartyById(id: number): Promise<PartyWithCandidates> {
    return await this.dbService.get<PartyWithCandidates>(`election/party/${id}`);
  }




}