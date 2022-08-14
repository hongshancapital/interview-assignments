import React, {
	FC,
	useState,
	useEffect,
	useMemo,
	FunctionComponentElement,
	ReactNode,
} from 'react';
import { CarouselIndicator } from './carouselIndicator';
import { CarouselItemProps } from './carouselItem';
import style from './carousel.module.css';

export interface CarouselProps {
	className?: string;
	children?: ReactNode | ReactNode[];
	intervalTime?: number;
}

/**
 * @param {children}
 * @param {IntervalTime} IntervalTime Interval of rotation
 * @returns Carousel container
 */
export const Carousel: FC<CarouselProps> = ({
	children = [],
	intervalTime = 3000,
}) => {
	const duration = Math.round(intervalTime / 1000);
	const [activeIndex, setActiveIndex] = useState(0);
	const [childLength, setChildLength] = useState(
		React.Children.count(children)
	);
	const indicatorRef = React.createRef<HTMLUListElement>();

	const updateIndex = (newIndex: number) => {
		if (newIndex < 0) {
			newIndex = childLength - 1;
		} else if (newIndex >= childLength) {
			newIndex = 0;
		}
		setActiveIndex(newIndex);
		resetAnimations();
	};

	const resetAnimations = () => {
		if (indicatorRef && indicatorRef.current) {
			indicatorRef.current.getAnimations()?.forEach((ani) => {
				ani.cancel();
				ani.play();
			});
		}
	};

	const handleClickIndicator = (index: number) => {
		updateIndex(index);
		resetAnimations();
	};

	useEffect(() => {
		const interval = setInterval(() => {
			updateIndex(activeIndex + 1);
			resetAnimations();
		}, intervalTime);

		return () => {
			if (interval) {
				clearInterval(interval);
			}
		};
	});

	const renderCarouselItem = () => {
		if (!childLength) return null;
		return React.Children.map(children, (child, index) => {
			const childElement = child as FunctionComponentElement<CarouselItemProps>;
			const { displayName } = childElement.type;
			if (displayName === 'CarouselItem') {
				return React.cloneElement(childElement);
			} else {
				console.error(
					'Carousel has a child which is not a CarouselItem component'
				);
			}
		});
	};

	const memoizedCarouselItems = useMemo(() => {
		const carouselItems = renderCarouselItem();
		setChildLength(carouselItems?.length ?? 0);
		return carouselItems;
	}, [children]);

	return (
		<div className={style.carousel_container} data-testid="test-carousel">
			{!!childLength && (
				<>
					<div
						className={style.inner}
						style={{ transform: `translateX(-${activeIndex * 100}%)` }}
					>
						{memoizedCarouselItems}
					</div>
					<CarouselIndicator
						indicatorRef={indicatorRef}
						length={childLength}
						duration={duration}
						handleClick={handleClickIndicator}
						activeIndex={activeIndex}
					/>
				</>
			)}
		</div>
	);
};
