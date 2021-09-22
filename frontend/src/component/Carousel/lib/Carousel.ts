
import React, { CSSProperties, Children } from 'react';
import { getDefaultElementSize, getKeys, IElementSize } from '../../../util';
import { Emitter } from './Emitter';

export type PaginationPosition = 'left' | 'center' | 'right';

export interface ICarouselOptions {
  delay: number;
  speed: number;
  autoplay: boolean;
  paginationColor: string;
  paginationActiveColor: string;
  paginationPosition: PaginationPosition;
}

export interface ICarouselEvents {
  'active-index-change': [number];
  'begin-translate': [React.CSSProperties];
  'carousel-size-change': [IElementSize];
}

export class Carousel extends Emitter<ICarouselEvents> {
  public delay: number;
  public speed: number;
  public autoplay: boolean;
  public paginationColor: string;
  public paginationActiveColor: string;
  public paginationPosition: PaginationPosition;

  public currentIndex: number = 0;
  public preIndex: number | null = null;
  public sliders: React.ReactNode[] = [];
  public carouselSize: IElementSize = getDefaultElementSize();
  private timeId: NodeJS.Timeout | null = null;

  constructor(options: ICarouselOptions) {
    super();
    const {
      delay,
      speed,
      autoplay,
      paginationColor,
      paginationActiveColor,
      paginationPosition,
    } = options;

    this.delay = delay;
    this.speed = speed;
    this.autoplay = autoplay;
    this.paginationColor = paginationColor;
    this.paginationActiveColor = paginationActiveColor;
    this.paginationPosition = paginationPosition;
  }

  public updateCarouselOptions(options: ICarouselOptions): void {
    getKeys(options).forEach((key) => {
      const value = options[key];
      if (value !== undefined) {
        if (key === 'autoplay' && this.autoplay !== options[key]) {
          if (options[key] === true) {
            this.restart();
          } else {
            this.stop();
          }
        }

        Object.assign(this, {
          [key]: value,
        });
      }
    });
  }
  public getRealCurrentIndex(): number {
    const sliderCount = this.sliders.length;

      if (this.currentIndex > sliderCount - 3) {
        return 0;
      }
      if (this.currentIndex < 0) {
        return sliderCount - 3;
      }

    return this.currentIndex;
  }

  public slideTo(nextIndex: number, stopAnimate?: boolean): void {
    this.preIndex = this.currentIndex;
    this.currentIndex = nextIndex;

    this.doSlideMode(stopAnimate);

    if (this.autoplay) {
      setTimeout(() => {
        this.startAutoplay();
      }, this.delay);
    }
    this.emit('active-index-change', this.getRealCurrentIndex());
  }

  public startAutoplay(): void {
    this.clearTimer();
    this.timeId = setTimeout(() => {
      this.slideTo(this.currentIndex + 1);
    }, this.speed);
  }

  public initCarouselSize(): void {
    this.carouselSize.width = 0;
    this.carouselSize.height = 0;
  }

  public initcurrentIndex(): void {
    this.currentIndex = 0;
  }

  public setCarouselSize(carouselSize: IElementSize): void {
    this.carouselSize = carouselSize;

    this.slideTo(this.currentIndex, true);
    this.emit('carousel-size-change', carouselSize);
  }

  public cloneChild(originChild: React.ReactNode[]): void {
    const originChildCount = Children.count(originChild);
    const arr: React.ReactNode[] = new Array(originChildCount + 2);

    if (originChildCount <= 1) {
      this.sliders = originChild;
      return;
    }

    Children.forEach(originChild, (child, index) => {
      if (index === 0) {
        arr[originChildCount + 1] = child;
      }

      if (index === originChildCount - 1) {
        arr[0] = child;
      }

      arr[index + 1] = child;
    });

    this.sliders = arr;
  }

  private stop(): void {
    this.clearTimer();
  }

  private restart(): void {
    this.startAutoplay();
  }

  private clearTimer(): void {
    if (this.timeId) {
      clearInterval(this.timeId);
      this.timeId = null;
    }
  }

  private getTranslateStyle(stopAnimate?: boolean): CSSProperties {
    const { currentIndex, carouselSize, delay, sliders } = this;
    const time = stopAnimate ? 0 : delay;
    const translateSize =
      (sliders.length === 1 ? 0 : -1 - currentIndex) * carouselSize.width;
    return {
      transform: `translateX(${translateSize}px)`,
      transitionDuration: `${time}ms`,
    };
  }

  private doSlideMode(stopAnimate?: boolean): void {
    this.emit('begin-translate', this.getTranslateStyle(stopAnimate));
    if (
      this.currentIndex > this.sliders.length - 3 &&
      this.sliders.length > 1
    ) {
      this.currentIndex = 0;
      setTimeout(() => {
        this.slideTo(0, true);
      }, this.delay);
      return;
    }

    if (this.currentIndex < 0) {
      this.currentIndex = this.sliders.length - 3;
      setTimeout(() => {
        this.slideTo(this.sliders.length - 3, true);
      }, this.delay);
      return;
    }
  }

}
