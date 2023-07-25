import React, {
  Fragment,
  ReactElement
} from 'react';
import '../index.css';

export const CarouselItems: React.FC<ItemsProps> = (props: ItemsProps) => {
  const { children } = props;
  return (
    <Fragment>
      {children && children.map((item, i) => (
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

export interface ItemsProps {
  children?: ReactElement[]
}