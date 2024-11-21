export interface Report {
  id: number;
  createdAt: string;
  reason: string;
  message: string;
  reporterEmail: string;
  reportedEmail: string;
}