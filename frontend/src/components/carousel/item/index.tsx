import React, {
  Fragment,
  ReactElement
} from 'react';
import '../index.css';

export const CarouselItem: React.FC<ItemProps> = (props: ItemProps) => {
  const { children } = props;
  return (
    <Fragment>
      {children.map((item, i) => (
        <div
          key={i}
          className={`list-item list-item-${i + 1}`}
          style={{
            width: `${100 / children.length}%`,
          }}
        >
          {item}
        </div>
      ))}
    </Fragment>
  );
};

export interface ItemProps {
  children: ReactElement[]
}