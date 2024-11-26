import type {TopicResponse} from "@/models/forum/TopicResponse";
import {DatabaseService} from "@/services/DatabaseService";
import type { CreateTopic } from '@/models/topic/CreateTopic'

export class TopicService {
    private dbService: DatabaseService;
    constructor() {
        this.dbService = new DatabaseService();
    }

    async getTopics(page: number = 0, size: number = 10, sort: string = "createdAt"): Promise<PaginatedResponse<TopicResponse>> {

        let url: string = `topic/?page=${page}&size=${size}&sort=${sort}`

        if(sort === "likes" || sort === "dislikes") {
          url = `topic/?page=${page}&size=${size}&customSort=${sort}`
        }

        return await this.dbService.get<PaginatedResponse<TopicResponse>>(url);
    }

    async searchTopicByStatement(statement: string): Promise<TopicResponse[]> {
        return await this.dbService.get<TopicResponse[]>(`topic/search/${statement}`);
    }

    async createTopic(topic: CreateTopic): Promise<CreateTopic> {
        return await this.dbService.post<CreateTopic>('topic/create-topic', topic);
    }

    async getTopicById(userId: number): Promise<TopicResponse> {
        return await this.dbService.get<TopicResponse>(`topic/${userId}`);
    }
}