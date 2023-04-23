import { CarouselItem } from '../..';
import './index.scss';
import type { PropsWithChildren } from 'react';

interface Props {
  list: CarouselItem[];
  activeIndex: number;
  delay: number;
  onSlideTo(index: number): void;
}
type PaginationProps = PropsWithChildren<Props>;

export default function Pagination({
  list,
  activeIndex,
  delay,
  onSlideTo,
}: PaginationProps) {
  return (
    <div className="carousel-pagination">
      {list.map((el, paginationIndex) => {
        const isActive = paginationIndex === activeIndex;
        return (
          <div
            key={el.id}
            className="carousel-pagination-item"
            onClick={() => {
              if (!isActive) {
                onSlideTo(paginationIndex);
              }
            }}
          >
            <div className="carousel-pagination-inner">
              {isActive && (
                <span
                  className="carousel-pagination-progress"
                  style={{
                    animation: `progress ${delay}ms linear`,
                  }}
                ></span>
              )}
            </div>
          </div>
        );
      })}
    </div>
  );
}
