import { useMemo } from 'react';
import {
  dotActiveStyle,
  dotCommonStyle,
  getPaginationStyles,
  pageStyle,
} from './pagination-styles';

interface IPaginationProps {
  count: number;
  currentSlide: number;
  delay: number;
}

export const Pagination: React.FC<IPaginationProps> = ({
  count,
  currentSlide = -1,
  delay,
}) => {
  const list = useMemo(() => new Array(count).fill(''), [count]);

  return (
    <div style={{ ...getPaginationStyles, width: `${count * 30 + 2}px` }}>
      {list.map((_: string, index: number) => {
        const isActive = index === currentSlide;
        const activeStyle = {
          transition: `transform ${delay}ms`,
        };

        return (
          <div key={index} style={pageStyle}>
            <div
              style={
                isActive
                  ? { ...dotCommonStyle, ...activeStyle, ...dotActiveStyle }
                  : { ...dotCommonStyle }
              }
            ></div>
          </div>
        );
      })}
    </div>
  );
};
