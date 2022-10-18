export interface SourceItem{
    title:string
    subTitle?:string
    style:ItemStyle
}
export interface ItemStyle{
    [key : string]: string
}