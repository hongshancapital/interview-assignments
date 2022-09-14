import React, { useState, useEffect } from 'react';

import { useAppSelector, useAppDispatch } from '../app/hooks';
import { scrollTo } from './carouselSlice';
import s from './Carousel.module.scss';

export default function Carousel() {
  const { active, imgInfo } = useAppSelector((state) => state.carousel)
  const dispatch = useAppDispatch();
  let timer: number;
  useEffect(() => {
    const scrollExe = (activeIndex: number) => {

      dispatch(scrollTo({ active: activeIndex }));

      const next = (activeIndex + 1) % imgInfo.length;
      timer = window.setTimeout(() => scrollExe(next), 10000);  
    }
    scrollExe(active);

    return () => window.clearTimeout(timer);

  }, []);

  return (
    <div className={s.carousel}>
      <div className={s['carousel-inner']}>
        {imgInfo.map((info, index) => <div
          key={index}
          className={`${s['carousel-item']} ${s[info.position]}`}
        >

          {info.titles.map((title, tIndex) => <h1 key={tIndex}>{title}</h1>)}

          {info.subtitles.map((subtitle, subIndex) => <h2 key={subIndex}>{subtitle}</h2>)}

          <img src={require(`../assets/${info.imgName}`)} alt="" />

        </div>)}
      </div>
    </div>
  );
}
