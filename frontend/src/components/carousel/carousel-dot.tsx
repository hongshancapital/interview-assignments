import { useMemo } from 'react';
import { CarouselDotProps } from '.';

export const CarouselDot = ({
  delay,
  active,
  onDotClick,
}: CarouselDotProps) => {
  const className = useMemo(() => {
    if (active) {
      return 'carousel-dots-item active';
    }
    return 'carousel-dots-item';
  }, [active]);

  return (
    <div className={className} onClick={onDotClick}>
      <span role="button" style={{ animationDuration: `${delay}ms` }} />
    </div>
  );
};

export default CarouselDot;
