import React, { useEffect } from 'react';

import { useAppSelector, useAppDispatch } from '../app/hooks';
import { scrollTo } from './carouselSlice';
import s from './Carousel.module.scss';

export default function Carousel() {
  const { active, imgInfo } = useAppSelector((state) => state.carousel)
  const dispatch = useAppDispatch();
  useEffect(() => {
    let timer: number;

    const scrollExe = (activeIndex: number) => {

      dispatch(scrollTo({ active: activeIndex }));

      const next = (activeIndex + 1) % imgInfo.length;
      timer = window.setTimeout(() => scrollExe(next), 5000);  
    }
    scrollExe(active);

    return () => window.clearTimeout(timer);

  }, [dispatch, active, imgInfo.length]);

  return (
    <div className={s.carousel}>
      <div className={s['carousel-inner']}>
        {imgInfo.map((info, index) => <div
          key={index}
          className={`${s['carousel-item']} ${s[info.condition]} ${s[info.name]}`}
        >
          <div className={s.title}>
            <div>
              <h1>
                {info.titles.map((title, tIndex) => <span key={tIndex}>{title}<br/></span>)}
              </h1>

              {info.subtitles.map((subtitle, subIndex) => <h2
                key={subIndex}
                className={subIndex === 0 ? s.first : ''}

              >{subtitle}</h2>)}
            </div>
          </div>

          <div className={s.picture}>
            <img src={require(`../assets/${info.imgName}`)} alt="" />
          </div>

        </div>)}
      </div>

      <div className={s.indicators}>
        {imgInfo.map((info, index) => {
          const highlight = (info.condition === 'active' ? 'highlight' : '');
          return (<div key={index} className={s['indicator-container']}>
            <div className={`${s['indicator-progress']} ${s[highlight]}`}></div>
          </div>);
        })}
      </div>

    </div>
  );
}
