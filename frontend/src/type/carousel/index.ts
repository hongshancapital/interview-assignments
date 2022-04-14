
export interface ICarouselItem {
    id: number;
    src: string;
    title: string[];
    desc?: string[];
    style?: {};
    [keyName: string]: any; 
}
