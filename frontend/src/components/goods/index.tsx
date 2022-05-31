/**
 * 商品展示组件
 */
import React, { FC, ReactNode } from "react";
import './index.scss';

export interface GoodsProps {
	name: string | ReactNode;
	desc?: string | ReactNode;
	bg: string;
	theme: 'dark' | 'default';
}

const Goods: FC<GoodsProps> = (props) => {
	const { name, desc = '', bg, theme = 'default' } = props;
	return (
		<div className={`goods-item ${theme === 'dark' ? 'goods-item-theme-dark' : ''}`} style={{ backgroundImage: `url(${bg})` }}>
			<div className="name">{name}</div>
			<div className="desc">{desc}</div>
		</div>
	)
}

export default Goods;