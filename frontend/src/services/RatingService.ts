import {DatabaseService} from "@/services/DatabaseService";
import type {RatingTypeEnum} from "@/models/enum/rating/RatingTypeEnum";
import type {Rating} from "@/models/ratings/Rating";
import type {AmountOfRatings} from "@/models/ratings/AmountOfRatings";

export class RatingService {
    private dbService: DatabaseService;

    constructor() {
        this.dbService = new DatabaseService();
    }

    async hasRating(ratingTypeId: number, ratingTypeEnum: RatingTypeEnum): Promise<Rating> {
        return await this.dbService.get<Rating>(`rating/has-rating/${ratingTypeId}/${ratingTypeEnum}`);
    }

    async rate(rating: Rating, ratingTypeEnum: RatingTypeEnum): Promise<Rating> {
        return await this.dbService.post<Rating>(`rating/rate/${ratingTypeEnum}`, rating)
    }

    async getAmountOfRatings(ratingTypeId: number, ratingTypeEnum: RatingTypeEnum): Promise<AmountOfRatings>{
        return await this.dbService.get<AmountOfRatings>(`rating/get-amount/${ratingTypeId}/${ratingTypeEnum}`);
    }
}