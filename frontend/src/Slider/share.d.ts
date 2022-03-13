type SliderProps = React.ReactElement | { src: string, alt: string }

type SwiperProps = {
  sliderList: Array<SliderProps>
  speed?: number;
  delay?: number;
  pause?: boolean;
  autoPlay?: boolean;
  dots?: boolean;
}