import './index.css';
import type { PropsWithChildren } from 'react';

export type PaginationProps = PropsWithChildren<{
  pagination: number;
  active: number;
  duration: number;
  slideTo(index: number): void;
}
>;

function Pagination({
  pagination,
  active,
  duration,
  slideTo,
}: PaginationProps) {
  return <div className="carousel-pagination">{
    Array.from(Array(pagination).keys()).map(i =>
      <div
        key={i}
        className="carousel-pagination-item"
        onClick={() => slideTo(i)}
      >
        {active === i && (
          <span
            className="carousel-pagination-progress"
            style={{
              animation: `progress ${duration}ms linear`,
            }}
          ></span>
        )}
      </div>
    )
  }</div>;
}

export default Pagination