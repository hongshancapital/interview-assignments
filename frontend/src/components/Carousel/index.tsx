/**
 * @desc 轮播组件主入口，负责状态和子组件的管理，支持options属性和嵌套CarouselPanel子组件两种用法
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

export interface CarouselOptionType {
	className?: string,
	render?: () => React.ReactNode,
	title?: string | React.ReactNode,
	content?: string | React.ReactNode,
}

export interface CarouselProps {
	duration?: number,
	children?: React.ReactNode,
	style?: React.CSSProperties,
	options?: Array<CarouselOptionType>
};

function Carousel({ children, duration = 3000 /*单位毫秒*/, options = [], ...restProps }: CarouselProps) {
	let validChildren: Array<React.ReactNode | React.ReactElement | void> | undefined | null;
	let childrenLength: number;
	if (options.length > 0) { // 优先渲染配置项数据源
		childrenLength = options.length;
		validChildren = options.map((option, index) => {
			return (
				<CarouselPanel option={option} key={index} />
			);
		});
	} else {
		// 过滤掉Carousel嵌套的子组件不是CarouselPanel的情况，这里要求严格的嵌套模式
		validChildren = React.Children.map(children, (child) => {
			if (React.isValidElement(child) && child?.type === CarouselPanel) {
				return child;
			}
		});
		childrenLength = React.Children.count(validChildren);
	}
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
