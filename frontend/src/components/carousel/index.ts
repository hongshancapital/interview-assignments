export interface CarouselItemProps {
  id: number;
  img?: string;
  title?: string;
  description?: string;
  width?: number;
  carouselItemProps?: React.DetailedHTMLProps<
    React.HTMLAttributes<HTMLDivElement>,
    HTMLDivElement
  >;
}

export type CarouselProps = {
  list: CarouselItemProps[];
  delay?: number;
};

export type CarouselDotProps = {
  delay: number;
  active: boolean;
  onDotClick: () => void;
};

export * from './carousel';
