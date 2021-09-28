/**
 * @desc 轮播组件主入口，负责状态和子组件的管理
 * @author zhangkegui
 * @version 1.0
 * @date 2021-09-27
 */
import React from 'react';
import CarouselBar from './CarouselBar';
import CarouselPanel from './CarouselPanel';
import CarouselContent from './CarouselContent';
import useLoopIndex from '../hooks/useLoopIndex';
import './index.css';

export interface CarouselProps {
	style?: React.CSSProperties,
	duration?: number,
	children: React.ReactNode
};

function Carousel({ children, duration = 3000 /*单位毫秒*/, ...restProps }: CarouselProps) {
	// 过滤掉Carousel嵌套的子组件不是CarouselPanel的情况，这里要求严格的嵌套模式
	const validChildren = React.Children.map(children, (child) => {
		if (React.isValidElement(child) && child?.type === CarouselPanel) {
			return child;
		}
	});
	const childrenLength: number = React.Children.count(validChildren);
	const activeIndex: number = useLoopIndex({
		duration,
		max: childrenLength - 1 /*索引从0开始*/
	});
	return (
		<div
			{...restProps}
			className="carousel-wrap"
		>
			<CarouselContent activeIndex={activeIndex}>
				{validChildren}
			</CarouselContent>
			<CarouselBar
				duration={duration}
				current={activeIndex}
				total={childrenLength}
			/>
		</div>
	);
}

Carousel.CarouselPanel = CarouselPanel;

export default Carousel;
