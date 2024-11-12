export interface TopicResponse {
    id: number;
    statement: string;
    message: string;
    userId: number;
    username: string;
    createdAt: string;
    updatedAt: string;
    likes: number;
    dislikes: number;
    amountOfAnswers:number;
}