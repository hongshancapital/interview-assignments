import { useImperativeHandle } from 'react';
import { CarouselRef } from '..';
import { getSafeIndex } from '../../utils/getSafeIndex';

type UseBundleRefHook = (props: {
  ref: React.ForwardedRef<CarouselRef>;
  setActive: React.Dispatch<React.SetStateAction<number>>;
  length: number;
}) => {
  moveTo: (index: number) => void;
  moveToNext: () => void;
  moveToPrevious: () => void;
};

/** 合成 moveXXX 方法，并绑定到 ref 上供外部调用。 */
export const useBundleRef: UseBundleRefHook = (props) => {
  const { ref, setActive, length } = props;

  const moveTo = (index: number) => {
    setActive(getSafeIndex(index, length));
  };

  const moveToNext = () => {
    setActive((x) => getSafeIndex(x + 1, length));
  };

  const moveToPrevious = () => {
    setActive((x) => getSafeIndex(x - 1, length));
  };

  const hanlde = () => {
    return {
      moveTo,
      moveToNext,
      moveToPrevious,
    };
  };

  useImperativeHandle(ref, hanlde);

  return { moveTo, moveToNext, moveToPrevious };
};
