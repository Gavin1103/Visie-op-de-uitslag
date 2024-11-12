import { Client } from '@stomp/stompjs'
import { ChatMessageType } from '@/models/enum/ChatMessageType'
import { ref } from 'vue'
import type { ChatMessage } from '@/models/ChatMessage'
import { CookieService } from '@/services/CookieService'


export class WebSocketService {
  private wsURL: String;

  private stompClient: Client = new Client();
  public messages = ref<any[]>([]);

  private token: string | null;
  private cookieService = new CookieService();

  constructor() {
    this.wsURL = import.meta.env.VITE_API_WS_URL;
    this.token = this.cookieService.getCookie(this.cookieService.accessTokenAlias);
  }



  public connect(id: number) {

    this.stompClient = new Client({
      connectHeaders: {
        Authorization: this.token ? `Bearer ${this.token}` : '',
      },
      brokerURL: `${this.wsURL}/ws?access_token=${this.token}`,

      onConnect: (frame) => {
        console.log('Connected: ' + frame);

        this.stompClient.subscribe(`/topic/messages/${id}`, (messageOutput) => {
          console.log("Received message: ", messageOutput.body);
          this.messages.value.push(JSON.parse(messageOutput.body));
        });

        this.sendMessage(id, "", ChatMessageType.JOIN)
      },
      onStompError: (error) => {
        console.error('STOMP error: ', error);
      }
    });

    this.stompClient.activate();
  }

  public disconnect(id: number) {

    this.sendMessage(id, "", ChatMessageType.LEAVE)
    this.stompClient.deactivate();
  }

  public sendMessage(id: number, message: String, type: ChatMessageType) {

    const body: ChatMessage = {
      id: '1',
      name: 'User',
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