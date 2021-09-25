import React, {
  CSSProperties,
  Children,
  HTMLAttributes,
  useContext,
  useCallback,
} from 'react';
import { classnames, getPrefixCls, UnionOmit } from '../../util';
import { modelContext } from './model';
import { ICarouselOptions, PaginationPosition } from './model/interface';

export interface ICarouselPagination {
  children?: React.ReactNode;
}

export type ICarouselPaginationProps = Partial<ICarouselOptions> & UnionOmit<
  ICarouselPagination,
  HTMLAttributes<HTMLDivElement>
>;

export const CarouselPagination = (
  props: ICarouselPaginationProps,
): JSX.Element | null => {
  const { className, ...extra } = props;
  const {state, dispatch} = useContext(modelContext);  

  const {
    speed = 3000,
    delay,
    autoplay = true,
    paginationColor,
    paginationPosition,
    paginationActiveColor
  } = extra;

  const {
    sliderLen,
    currentIndex,
    carouselSize
  } = state;

  const carouselDotsCls = classnames(
    getPrefixCls('carousel-pagination', {
      [paginationPosition as PaginationPosition]: true,
    }),
    className,
  );

  const handleSlideTo = useCallback((nextIndex: number) => {
    const translateSize =
      ((sliderLen === 1) ? 0 :  -nextIndex) * carouselSize.width;
    dispatch({
      type: 'UPDATE_STATE',
      payload: {
        currentIndex: nextIndex,
        containerTransform: {
          transform: `translateX(${translateSize}px)`,
          transitionDuration: `${delay}ms`,
        }
      }
    })
  }, [carouselSize]);


  const renderPagination = (): JSX.Element[] =>
    Children.map(new Array(sliderLen), (item, index) => {
      const isActive = currentIndex === index;
      const paginationInnerStyle: CSSProperties = {
        backgroundImage: isActive
          ? `linear-gradient(${paginationActiveColor},${paginationActiveColor})`
          : 'none',
      };

      Object.assign(paginationInnerStyle, {
        animationDuration: isActive && autoplay ? `${speed / 1000}s` : '0',
        animationPlayState: autoplay ? 'running' : 'paused',
      });

      return (
        <span
          key={index}
          className={'carousel-pagination-item'}
          style={{backgroundColor: paginationColor}}
          onClick={() => handleSlideTo(index)}
        >
          <span 
            className={getPrefixCls('carousel-pagination-item-inner', {
              active: isActive,
            })}
            style={paginationInnerStyle}
          />
        </span>
      );
    });


  if (sliderLen <= 1) {
    return null;
  }

  return (
    <div  className={carouselDotsCls}>
      {renderPagination()}
    </div>
  );
};
