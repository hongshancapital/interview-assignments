import CarouselDotItem from "./CarouselDotItem";
import './index.css';

interface CarouselDotProps {
  count: number;
}

function CarouselDot({ count }: CarouselDotProps) {
  return (
    <div className="carousel-dot-container">
      {new Array(count).fill(1).map((item, index) => (
        <CarouselDotItem key={index} index={index} />
      ))}
    </div>
  );
}

CarouselDot.displayName = "CarouselDot";
export default CarouselDot;
