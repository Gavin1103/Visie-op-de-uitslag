import type { CandidateWithVotes } from '@/models/Candidate'

export interface Party {
  partyId: number;
  name: string;
  logo: string;
}

export interface fullParty {
  partyId: number;
  name: string;
  logo: string;
  candidates: CandidateWithVotes[];
  totalVotes: number;
  totalSeats: number;
}