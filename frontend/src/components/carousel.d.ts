declare namespace CarouselTypes {
  type SlideType = {
    bgColor: string;
    textColor?: string;
    title: string;
    text: string;
    img: string;
    imgSize: 'l' | 'm' | 's';
  };
  
  interface CarouselProps {
    slides: SlideType[];
    interval?: number;
  }
}