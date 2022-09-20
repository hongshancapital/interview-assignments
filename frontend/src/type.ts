export type dataListType = {
  key: string;
  url: string;
  title: string[];
}[];

export interface CarouselProps {
  dataList: dataListType;
  delay: number;
}

export interface ScrollBoxProps {
  dataList: dataListType;
  currentInd: number;
}
