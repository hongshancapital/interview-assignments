export interface DataItem{
  id:number,
  title:string[],
  description?:string[],
  icon:ICON_NAME,
  themeType:themeStyle,
}

export enum THEME_TYPE {
  BLACK = "black",
  WHITE = "white",
  GREY = 'grey',
}

export enum ICON_NAME {
  AIRPODS = 'airpods',
  IPHONE = 'iphone',
  TABLET = 'tablet',
}