import React, { useState, useEffect, useMemo, CSSProperties } from 'react';
import { switchTime } from './config';
import './index.scss';

interface IDataInfo {
  imgUrl: string;
  fontColor: string;
  title: string | string[];
  subTitle?: string | string[];
}
interface IProps {
  startIndex?: number;
  dataInfo: IDataInfo[];
}

function Carousel(props: IProps) {
  const { dataInfo, startIndex = 0 } = props;

  const lengthArr = useMemo(() => {
    return Array.from(Array(dataInfo.length).keys());
  }, [dataInfo.length]);

  const [curIndex, setIndex] = useState(startIndex);

  useEffect(() => {
    setIndex(startIndex);
  }, [startIndex, lengthArr]);

  useEffect(() => {
    const timer = setInterval(() => {
      setIndex((curIndex) => (curIndex + 1) % lengthArr.length);
    }, switchTime);
    return () => {
      clearInterval(timer);
    };
  }, [curIndex, lengthArr]);

  return (
    <div
      className='carousel'
      onClick={() => setIndex((curIndex) => (curIndex + 1) % dataInfo.length)}
    >
      {dataInfo.map(({ imgUrl, title, subTitle, fontColor }, index) => (
        <div
          className='carousel-item'
          key={index}
          style={
            {
              // eslint-disable-next-line no-useless-computed-key
              ['--carousel-item-left']:
                index - curIndex === 0 ? 0 : `${index - curIndex}00%`,
              // eslint-disable-next-line no-useless-computed-key
              ['--carousel-bg-img']: `url(${imgUrl})`,
              color: fontColor,
            } as CSSProperties
          }
        >
          <h1 key={'h1-' + index}>
            {transformArr(title)
              .map((item) => item + '\n')
              .join('')}
          </h1>

          <h3 key={'h3' + index}>
            {transformArr(subTitle)
              .map((item) => item + '\n')
              .join('')}
          </h3>
        </div>
      ))}
      <div className='slider'>
        {lengthArr.map((index) => (
          <div
            key={index}
            style={{
              animationDuration: `${switchTime}ms`,
            }}
            className={index === curIndex ? 'active-slider' : ''}
          ></div>
        ))}
      </div>
    </div>
  );
}

export { Carousel };

function transformArr(item?: string | string[]): string[] {
  return item ? (Array.isArray(item) ? item : [item]) : [];
}
