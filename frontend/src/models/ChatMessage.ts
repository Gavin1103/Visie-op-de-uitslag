import type { ChatMessageType } from '@/models/enum/ChatMessageType'

export interface ChatMessage {
  id: number
  name: String
  message: String
  type: ChatMessageType
  timestamp: Date
}