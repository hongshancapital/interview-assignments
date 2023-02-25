import useSlider from "./useSlider";
import CarouselButton from "./CarouselButton";
import "./Carousel.css";

interface CarouselProps {
  /** 3000ms default */
  interval?: number;
  children: JSX.Element[];
}

export default function Carousel(props: CarouselProps) {
  const { interval = 3000, children } = props;
  const sliderCnt = children.length;

  const [current, progress] = useSlider(sliderCnt, interval);

  return (
    <div className="Carousel">
      <div
        className="CarouselContainer"
        style={{
          width: `${sliderCnt * 100}%`,
          transform: `translateX(-${current * 100 / sliderCnt}%)`,
        }}
      >
        {children.map((child, i) => (
          <div key={i} className="CarouselItem">
            {child}
          </div>
        ))}
      </div>
      <div className="CarouselControl">
        {children.map((_child, i) => (
          <CarouselButton
            key={i}
            progress={current === i ? progress : 0}
          />
        ))}
      </div>
    </div>
  );
}
