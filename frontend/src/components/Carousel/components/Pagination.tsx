import './Pagination.css';
import type { PropsWithChildren } from 'react';
interface Props {
  pagination: number;
  active: number;
  delay: number;
  slideTo(index: number): void;
}
export type PaginationProps = PropsWithChildren<Props>;

export default function Pagination({
  pagination,
  active,
  delay,
  slideTo,
}: PaginationProps) {
  const nodes = [];
  for (let i = 0; i < pagination; i++) {
    nodes.push(
      <div
        key={i}
        className="carousel__pagination__item"
        onClick={() => slideTo(i)}
      >
        {active === i && (
          <span
            className="carousel__pagination__progress"
            style={{
              animation: `progress ${delay}ms linear`,
            }}
          ></span>
        )}
      </div>,
    );
  }
  return (
    <div className="carousel__pagination">
      {nodes}
    </div>
  )
}