import React, { FC } from 'react';
import img1 from '../../assets/iphone.png';
import img2 from '../../assets/tablet.png';
import img3 from '../../assets/airpods.png';
import css from './index.module.scss';

interface ITestItem {
  title: string;
  describes?: string[];
  textColor?: string;
  img: string;
};

const TestItem: FC<ITestItem> = ({
  title,
  describes,
  textColor,
  img
}) => {

  return (
    <div className={css.itemWrapper}>
      <div
        className={css.textWrapper}
        style={{ color: textColor }}
      >
        <div className={css.title}>
          {title}
        </div>
        {describes?.map((el, idx) =>
          <div
            className={css.desc}
            key={idx}
          >
            {el}
          </div>
        )}
      </div>
      <div className={css.imgWrapper}>
        <img src={img} alt="" />
      </div>
    </div>
  )
};

export const demoItems = [
  <TestItem
    title="xPhone"
    describes={["Lots to love. Less to spend.", "Starting at $399"]}
    textColor="white"
    img={img1}
  />,
  <TestItem
    title="Tablet"
    describes={["Just the right amount of everything"]}
    img={img2}
  />,
  <TestItem
    title="Buy a Tablet or xPhone for college. Get airPods"
    img={img3}
  />,
];
