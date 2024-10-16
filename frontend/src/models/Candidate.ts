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
  candidateId: number;
  partyId: number;
  fullName: string;
  votes: number;
}