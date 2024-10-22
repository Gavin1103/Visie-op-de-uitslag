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
  amountOfVotes: number;
  amountOfSeats: number;
}

export interface PartyWithVotes {
  partyId: number;
  name: string;
  logo: string;
  amountOfVotes: number;
  amountOfSeats:number;
}