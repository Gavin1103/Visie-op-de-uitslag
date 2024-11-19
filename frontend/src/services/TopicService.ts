import type {TopicResponse} from "@/models/forum/TopicResponse";
import {DatabaseService} from "@/services/DatabaseService";
import type { NewUser } from '@/models/user/NewUser'
import type { CreateTopic } from '@/models/topic/CreateTopic'
import type { User } from '@/models/user/User'
import type { GetTopic } from '@/models/topic/GetTopic'

export class TopicService {
    private dbService: DatabaseService;
    constructor() {
        this.dbService = new DatabaseService();
    }

    async getTopics(page: number = 0, size: number = 10, sort: string = "createdAt"): Promise<PaginatedResponse<TopicResponse>> {
        return await this.dbService.get<PaginatedResponse<TopicResponse>>(`topic/?page=${page}&size=${size}&sort=${sort}`);
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