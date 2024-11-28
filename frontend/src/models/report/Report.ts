export interface Report {
  id: number;
  createdAt: string;
  reason: string;
  messageId: number;
  message: string;
  reporterEmail: string;
  reportedEmail: string;
}