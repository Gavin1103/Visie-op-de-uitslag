import type {AmountOfRatings} from "@/models/ratings/AmountOfRatings";

export interface TopicResponse {
    id: number;
    statement: string;
    message: string;
    userId: number;
    username: string;
    createdAt: string;
    updatedAt: string;
    amountOfRatings: AmountOfRatings;
    amountOfAnswers:number;
}