import './index.scss';
import type { PropsWithChildren } from 'react';

interface Props {
  className?: string;
}
type CarouselSlideProps = PropsWithChildren<Props>;

export default function CarouselSlide({
  children,
  className,
}: CarouselSlideProps) {
  return <div className={`carousel-slide ${className || ''}`}>{children}</div>;
}
