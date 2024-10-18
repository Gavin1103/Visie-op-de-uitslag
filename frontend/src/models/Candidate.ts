export interface Candidate {
  candidateId: number;
  firstName: string;
  lastName: string;
  initials: string;
  lastNamePrefix: string;
  locality: string;
  partyId: number;
}

export interface CandidateWithVotes {
  candidateId: CandidateId
  fullName: string;
  votes: number;
}

type CandidateId = {
  candidateId: number;
  partyId: number;
}