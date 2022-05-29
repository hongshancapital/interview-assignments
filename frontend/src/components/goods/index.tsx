/**
 * 商品展示组件
 */
import React, { FC, ReactNode } from "react";
import './index.scss';

interface Props {
	name: string | ReactNode;
	desc?: string | ReactNode;
	imgUrl: string;
	theme?: 'dark' | 'white';
}

const Goods: FC<Props> = (props) => {
	const { name, desc = '', imgUrl, theme = '' } = props;
	return (
		<div className={`goods-item ${theme === 'dark' ? 'goods-item-theme-dark' : ''}`}>
			<div className="name">{name}</div>
			<div className="desc">{desc}</div>
			<div className="imgWrap">
				<img src={imgUrl} alt='' />
			</div>
		</div>
	)
}

export default Goods;