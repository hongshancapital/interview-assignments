import { ObjectID } from 'typeorm';
export declare class TrackLink {
    id: ObjectID;
    shortened: string;
    remoteAddress: string;
    userAgent: string;
    createdAt: string;
    updatedAt: string;
}
