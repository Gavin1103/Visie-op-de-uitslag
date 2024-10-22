export interface Party {
  partyId: number;
  name: string;
  logo: string;
}

export interface PartyWithVotes {
  partyId: number;
  name: string;
  logo: string;
  amountOfVotes: number;
  amountOfSeats:number;
}