type carouselData = {
    img: string;
    text: text[];
  }
  
  type text = {
    label: string;
    color: string;
    fontSize: string;
  }
  
  export interface propsType {
    carouselList: carouselData[];
    speed?: number;
  } 