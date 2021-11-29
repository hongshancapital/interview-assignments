import { IImgProps } from "../components/Carousel/CommonTypes";

export function createImgArr(origin:string[]):IImgProps[]{
         if(!origin) return origin
         return origin.map( (i:string) => {
              return {
                   url:i,
                   id:Math.random().toString(36).slice(-7)
              }
         })
}