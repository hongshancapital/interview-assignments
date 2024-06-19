import React, { useRef } from "react";
import classnames from "classnames";
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination, Autoplay } from "swiper";

import "swiper/css";
import "swiper/css/pagination";
import "./carousel.css";

import type { AutoplayOptions } from "swiper/types/modules/autoplay";
import type { PaginationOptions } from "swiper/types";

export interface ICarouselItem {
  title: string;
  subtitle?: string;
  backgroundImage: string;
  backgroundColor?: "black" | "white";
}

export interface AutoplayProps {
  delay: number;
}

export interface ICarouselProps {
  items: ICarouselItem[];
  autoplay?: AutoplayProps | boolean;
}

const DEFAULT_AUTOPLAY_DELAY = 2500;
const CUSTOM_BULLET_ACTIVE_CLASSNAME = "curousel-bullet-active";
const PROGRESS_PROPERTY_NAME = "--progress";

export const Carousel: React.FC<ICarouselProps> = (props) => {
  const paginationRef = useRef<HTMLDivElement>(null);
  const { items, autoplay } = props;

  const autoplayOption: boolean | AutoplayOptions =
    typeof autoplay === "boolean"
      ? autoplay
      : {
          delay: autoplay?.delay || DEFAULT_AUTOPLAY_DELAY,
        };

  const pagination: PaginationOptions = {
    clickable: true,
    el: ".swiper-pagination",
    bulletActiveClass: CUSTOM_BULLET_ACTIVE_CLASSNAME,
    renderBullet: function (_: number, className: string) {
      return `
				<span class="${className} carousel-pagination-bullet"></span>
			`;
    },
  };

  const handleAutoTimeLeft = (_: any, time: number, progress: number) => {
    if (paginationRef.current) {
      const activeBullet = paginationRef.current.getElementsByClassName(
        CUSTOM_BULLET_ACTIVE_CLASSNAME
      )?.[0];
      if (!activeBullet) return;

      (activeBullet as HTMLElement).style.setProperty(
        PROGRESS_PROPERTY_NAME,
        "" + progress
      );
    }
  };

  return (
    <Swiper
      className="carousel-root"
      autoplay={autoplayOption}
      pagination={pagination}
      onAutoplayTimeLeft={handleAutoTimeLeft}
      modules={[Pagination, Autoplay]}
    >
      {items.map((item) => {
        return (
          <SwiperSlide
            key={item.title}
            className={classnames(
              "carousel-content",
              item.backgroundColor === "black" ? "black" : "white"
            )}
          >
            <div className="imgae-warpper">
              <img
                className="imgae"
                src={item.backgroundImage}
                alt={item.title}
              />
            </div>
            <div className="content">
              <div className="title">{item.title}</div>
              {item.subtitle ? (
                <div className="subtitle">{item.subtitle}</div>
              ) : null}
            </div>
          </SwiperSlide>
        );
      })}

      <div ref={paginationRef} className="swiper-pagination"></div>
    </Swiper>
  );
};
