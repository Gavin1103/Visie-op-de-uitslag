import type {TopicResponse} from "@/models/forum/TopicResponse";
import {DatabaseService} from "@/services/DatabaseService";

export class TopicService {
    private dbService: DatabaseService;
    constructor() {
        this.dbService = new DatabaseService();
    }

    async getTopics(page: number = 0, size: number = 10, sort: string = "createdAt"): Promise<TopicResponse[]> {
        return await this.dbService.get<TopicResponse[]>(`topic/?page=${page}&size=${size}&sort=${sort}`);
    }

}