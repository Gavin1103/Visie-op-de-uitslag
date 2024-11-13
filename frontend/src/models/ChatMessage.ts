import type { ChatMessageType } from '@/models/enum/ChatMessageType'

export interface ChatMessage {
  id: number
  userId: number
  chatId: number
  name: String
  message: String
  type: ChatMessageType
  activeUsers: number
  timestamp: Date
}