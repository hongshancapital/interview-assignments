/*
 * @Author: danjp
 * @Date: 2022/6/25 11:21
 * @LastEditTime: 2022/6/25 11:21
 * @LastEditors: danjp
 * @Description:
 */
import React from 'react';
import iphoneImg from './assets/iphone.png';
import tabletImg from './assets/tablet.png';
import airpodsImg from './assets/airpods.png';

type PageDataItem = {
  id: number;
  img: string;
  title: string;
  desc?: string;
  style?: React.CSSProperties;
};

export const PageDataList: PageDataItem[] = [
  {
    id: 1,
    img: iphoneImg,
    title: 'xPhone',
    desc: 'Lots to love.Less to speed.<br/>Starting at $39',
    style: { color: '#fff' },
  },
  {
    id: 2,
    img: tabletImg,
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
  },
  {
    id: 3,
    img: airpodsImg,
    title: 'Buy a Tablet or xPhone for college.<br/>Get arPods.',
  }
];
