import React from 'react';
import { GoodsProps } from '../components/goods';

import imgAirpods from '../assets/airpods.png';
import imgIphone from '../assets/iphone.png';
import imgTablet from '../assets/tablet.png';

export const GOODS_LIST: GoodsProps[] = [
	{
		name: 'xPhone',
		desc: <div>Lots to love. Less to spend.<br />Starting at $399.</div>,
		bg: imgIphone,
		theme: 'dark',
	},
	{
		name: 'Tablet',
		desc: <div>Just the right amount of everything.</div>,
		bg: imgTablet,
		theme: 'default',
	},
	{
		name: <div>Buy a Tablet or xPhone for college.<br /> Get arPods.</div>,
		bg: imgAirpods,
		theme: 'default',
	},
]