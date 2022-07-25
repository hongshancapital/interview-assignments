import React, { FC, ReactNode } from 'react';
import style from './index.module.scss';

interface PageLayoutProps {
  backgroundColor: string,
  imageUrl: string,
  children: ReactNode,
}

const PageLayout: FC<PageLayoutProps> = ({ children, imageUrl, backgroundColor }) => (
  <div className={style.LayoutRoot} style={{ backgroundColor }}>
    <div className={style.Desc}>
      {children}
    </div>
    <img src={imageUrl} alt=""/>
  </div>
)

export default PageLayout;