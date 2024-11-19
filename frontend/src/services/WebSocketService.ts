import { Client } from '@stomp/stompjs'
import { ChatMessageType } from '@/models/enum/ChatMessageType'
import { ref } from 'vue'
import type { ChatMessage } from '@/models/chat/ChatMessage'
import { CookieService } from '@/services/CookieService'
import { DatabaseService } from '@/services/DatabaseService'


export class WebSocketService {
  private wsURL: String;

  private stompClient: Client = new Client();
  public messages = ref<ChatMessage[]>([]);

  private token: string | null;
  private cookieService = new CookieService();
  private dbService: DatabaseService;

  constructor() {
    this.wsURL = import.meta.env.VITE_API_WS_URL;
    this.token = this.cookieService.getCookie(this.cookieService.accessTokenAlias);
    this.dbService = new DatabaseService();
  }

  public connect(id: number) {
    this.stompClient = new Client({
      connectHeaders: {
        Authorization: this.token ? `Bearer ${this.token}` : '',
      },
      brokerURL: `${this.wsURL}/ws`,

      onConnect: async (frame) => {
        console.log('Connected: ' + frame);

        const livechat: ChatMessage[] = await this.dbService.get<ChatMessage[]>(`chat/topic/${id}`)
        this.messages.value = livechat || []

        this.stompClient.subscribe(`/topic/messages/${id}`, (messageOutput) => {
          console.log("Received message: ", messageOutput.body);
          this.messages.value = [...this.messages.value, JSON.parse(messageOutput.body)];
        });
        if(this.token) {
          this.sendMessage(id, "", ChatMessageType.JOIN)
        }
        window.addEventListener('beforeunload', this.handleWindowClose.bind(this, id));
      },

      onStompError: (error) => {
        console.error('STOMP error: ', error);
      }
    });

    this.stompClient.activate();

  }

  private handleWindowClose(id: number) {
    this.disconnect(id);
  }

  public disconnect(id: number) {
    if(this.stompClient) {
      if(this.token) {
        this.sendMessage(id, "", ChatMessageType.LEAVE);
      }
      this.stompClient.deactivate();
      this.messages.value = [];

      window.removeEventListener('beforeunload', this.handleWindowClose.bind(this, id));
    }
  }

  public sendMessage(id: number, message: String, type: ChatMessageType) {
    if(this.token) {
      const body: ChatMessage = {
        id: '1',
        name: 'User',
        chatId: id,
        message: message,
        type: type,
        timestamp: new Date().toISOString()
      }

      this.stompClient.publish({
        destination: `/app/chat/${id}`,
        body: JSON.stringify(body),
        headers: {
          Authorization: `${this.token}`
        }
      });
    }
  }
}