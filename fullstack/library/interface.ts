export type urlHanlder = (err: Error | null, url: string) => void;

export interface IConvert {
    convert: (cb: urlHanlder) => void; 
}