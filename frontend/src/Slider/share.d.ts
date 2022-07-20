type SliderProps = {
  id: string | number;
  descriptionInfo: {
    title: string[];
    description: string[];
  };
  imageInfo: {
    src: string;
    alt: string;
  },
  style?: CSSProperties;
} | React.ReactElement;

type SwiperProps = {
  sliderList: Array<SliderProps>;
  speed?: number;
  delay?: number;
  pause?: boolean;
  autoPlay?: boolean;
  dots?: boolean;
}