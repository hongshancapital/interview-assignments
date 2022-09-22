
/** 统一数据类型定义库 */


/** 定义基础的信息类型接口 */ 
export interface IBaseInf {
    id: number,
    title: string,
    description: string,
    link: string,
    imageUrl: string,
}

/** ICarouselInf 定制类型 */ 
export interface ICarouselInf extends IBaseInf {
    color?:string,
    backgroundColor?:string
}