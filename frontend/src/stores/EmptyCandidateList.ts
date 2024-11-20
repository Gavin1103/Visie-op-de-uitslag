
import type { CandidateWithVotes } from '@/models/Candidate'
import type { TopicResponse } from '@/models/forum/TopicResponse'

export const emptyCandidateList: CandidateWithVotes[] = [
  {
    candidateId: {
      candidateId: 0,
      partyId: 0
    },
    fullName: ' ',
    votes: 0
  },
  {
    candidateId: {
      candidateId: 0,
      partyId: 0
    },
    fullName: ' ',
    votes: 0
  },
  {
    candidateId: {
      candidateId: 0,
      partyId: 0
    },
    fullName: ' ',
    votes: 0
  },
  {
    candidateId: {
      candidateId: 0,
      partyId: 0
    },
    fullName: ' ',
    votes: 0
  },
  {
    candidateId: {
      candidateId: 0,
      partyId: 0
    },
    fullName: ' ',
    votes: 0
  },
  {
    candidateId: {
      candidateId: 0,
      partyId: 0
    },
    fullName: ' ',
    votes: 0
  },
  {
    candidateId: {
      candidateId: 0,
      partyId: 0
    },
    fullName: ' ',
    votes: 0
  },
  {
    candidateId: {
      candidateId: 0,
      partyId: 0
    },
    fullName: ' ',
    votes: 0
  },
];

export const dummytopicResponse: TopicResponse = {id: 0,
  statement: "",
  message: "",
  userId: 0,
  username: "",
  createdAt: "",
  updatedAt: "",
  likes: 0,
  dislikes: 0,
  amountOfAnswers: 0}