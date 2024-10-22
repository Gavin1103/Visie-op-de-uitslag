import {DatabaseService} from "@/services/DatabaseService";
import type {PartyWithVotes} from "@/models/Party";

export class PartyService{
    private dbService: DatabaseService;

    constructor() {
        this.dbService = new DatabaseService();
    }

    async getPartiesWithVotes(): Promise<PartyWithVotes[]> {
        return await this.dbService.get<PartyWithVotes[]>('election/parties/votes');
    }

    async getElectedParty(): Promise<PartyWithVotes> {
        return await this.dbService.get<PartyWithVotes>('election/electedParty');
    }
}