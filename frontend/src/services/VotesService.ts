import type {TotalAmountOfVotes} from "@/models/votes";
import {DatabaseService} from "@/services/DatabaseService";

export class VotesService {
    private dbService: DatabaseService;

    constructor() {
        this.dbService = new DatabaseService();
    }

    async getTotalAmountOfVotes(): Promise<TotalAmountOfVotes> {
        return await this.dbService.get<TotalAmountOfVotes>('election/totalAmountOfVotes');
    }

}