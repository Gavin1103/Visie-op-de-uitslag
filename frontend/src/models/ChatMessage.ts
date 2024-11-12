import type { ChatMessageType } from '@/models/enum/ChatMessageType'

export interface ChatMessage {
  id: number
  userId: number
  name: String
  message: String
  type: ChatMessageType
  timestamp: Date
}