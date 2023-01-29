import { useEffect } from 'react';

type UseObserveHook = (props: {
  active: number;
  length: number;
  onChange?: (index: number) => void;
}) => void;

/** 检测 active 的变化，依据 active 的变化驱动视图更新 */
export const useObserve: UseObserveHook = ({ active, length, onChange }) => {
  useEffect(() => {
    onChange?.(active);
    const el = document.getElementById(`carouse_item_${active}`);
    el?.scrollIntoView({
      behavior: 'smooth',
      block: 'end',
      inline: 'nearest',
    });
  }, [active]);
};
