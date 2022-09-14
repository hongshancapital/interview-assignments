import React, { FC } from 'react';
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

export default TestItem;