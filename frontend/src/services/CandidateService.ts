import { DatabaseService } from '@/services/DatabaseService'
import type { CandidateWithVotes } from '@/models/Candidate'

export class CandidateService {
  private dbService: DatabaseService;

  constructor() {
    this.dbService = new DatabaseService();
  }

  async getCandidatesByArea(area: String, partyId: number | undefined, searchInput: String): Promise<CandidateWithVotes[]> {
    return await this.dbService.get<CandidateWithVotes[]>(`election/candidates/${partyId}/${area}/${searchInput}`)
  }
}