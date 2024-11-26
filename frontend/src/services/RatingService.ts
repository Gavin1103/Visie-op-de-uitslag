import {DatabaseService} from "@/services/DatabaseService";
import type {RatingTypeEnum} from "@/models/enum/rating/RatingTypeEnum";
import type {Rating} from "@/models/ratings/Rating";

export class RatingService {
    private dbService: DatabaseService;

    constructor() {
        this.dbService = new DatabaseService();
    }

    async hasRating(ratingTypeId: number, ratingType: RatingTypeEnum): Promise<Rating> {
        return await this.dbService.get<Rating>(`rating/has-rating/${ratingTypeId}/${ratingType}`);
    }

}