
import { getKeys } from '../util';
import {
  IElementPosition,
  IElementSize,
} from './type';



export function getDefaultElementSize(): IElementSize {
  return {
    width: 0,
    height: 0,
  };
}


export function getWindowSize(): IElementSize {

  return {
    width:
      window.innerWidth ||
      document.documentElement.clientWidth ||
      document.body.clientWidth,
    height:
      window.innerHeight ||
      document.documentElement.clientHeight ||
      document.body.clientHeight,
  };
}

export type NonReadonlyCss = Partial<
  Omit<CSSStyleDeclaration, 'length' | 'parentRule'>
>;

export function swap<T>(
  element: HTMLElement,
  styles: NonReadonlyCss,
  callback: () => T,
): T {
  const old: Record<string, unknown> = {};
  const keys = getKeys(styles);

  keys.forEach((key) => (old[key] = element.style[key]));
  Object.assign(element.style, styles);

  const result = callback();

  Object.assign(element.style, old);

  return result;
}

/**
 * 获取元素大小
 */
export function getElementSize(element: HTMLElement): IElementSize {
  const { width, height } = getElementPosition(element);

  return {
    width,
    height,
  };
}

/**
 * 获取元素位置
 */
export function getElementPosition(element: HTMLElement): IElementPosition {

  const style = getComputedStyle(element);

  const getPosition = (): IElementPosition => {
    const {
      width,
      height,
      top,
      left,
      right,
      bottom,
    } = element.getBoundingClientRect();

    return { width, height, top, left, right, bottom };
  };

  if (style.display !== 'none') {
    return getPosition();
  }

  return swap(element, { display: 'block' }, getPosition);
}

export function isElementSizeEqual(a: IElementSize, b: IElementSize): boolean {
  return a.width === b.width && a.height === b.height;
}

export function isElementPositionEqual(
  a: IElementPosition,
  b: IElementPosition,
): boolean {
  return (
    isElementSizeEqual(a, b) &&
    a.top === b.top &&
    a.bottom === b.bottom &&
    a.left === b.left &&
    a.right === b.right
  );
}

export interface IElementScroll {
  scrollTop: number;
  scrollLeft: number;
}

