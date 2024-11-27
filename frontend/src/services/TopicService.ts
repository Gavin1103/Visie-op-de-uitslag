import type {TopicResponse} from "@/models/forum/TopicResponse";
import {DatabaseService} from "@/services/DatabaseService";
import type { CreateTopic } from '@/models/topic/CreateTopic'

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

    async getTopicsByUser(page: number = 0, size: number = 10, sort: string = "createdAt"): Promise<TopicResponse> {
        return await this.dbService.get<TopicResponse>(`topic/get-topics?page=${page}&size=${size}&sort=${sort}`);
    }
}