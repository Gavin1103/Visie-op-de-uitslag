import type { CandidateWithVotes } from '@/models/Candidate'

export interface Party {
  partyId: number;
  name: string;
  logo: string;
}

export interface PartyWithCandidates {
  partyId: number;
  name: string;
  logo: string;
  candidates: CandidateWithVotes[];
  totalVotes: number;
  totalSeats: number;
}

export interface PartyWithVotes {
  partyId: number;
  name: string;
  logo: string;
  amountOfVotes: number;
  amountOfSeats:number;
}