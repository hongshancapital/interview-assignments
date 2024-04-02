import type { PropsWithChildren } from 'react';
import './index.css';

type CarouselSlideProps = PropsWithChildren<{
  className?: string;
}>;

function CarouselSlide({
  children,
  className,
}: CarouselSlideProps) {
  return <div className={`carousel-slide ${className || ''}`}>{children}</div>;
}

export default CarouselSlide