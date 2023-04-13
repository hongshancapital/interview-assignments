import React, {
    Fragment,
    MouseEventHandler,
    useEffect,
    useMemo,
    useState,
} from 'react';
import './index.scss';
import classNames from 'classnames';
import deepMemo from '../../utils/deepMemo';
import useInterval from '../../hooks/useInterval';
import useCurrentIndex from '../../hooks/useCurrentIndex';

interface CarouselAnimationConfig {
    interval: number;
    sliding_duration: number;
}

export interface CarouselProps<T> {
    items: T[];
    renderItem: (data: T) => React.ReactElement | null;
    keyExtractor: (data: T) => string;
    animationConfig?: Partial<CarouselAnimationConfig>;
    testID?: string;
}

const mergeAnimationConfig = (config: Partial<CarouselAnimationConfig>) => {
    // NOTE: interval should be greater than the sliding_duration.
    const message =
        '[CarouselAnimationConfig]: interval should be greater than the sliding_duration';
    const baseConfig: CarouselAnimationConfig = {
        interval: 3000,
        sliding_duration: 300,
    };

    let mergedConfig = Object.assign({}, baseConfig, config);

    if (mergedConfig.interval < mergedConfig.sliding_duration) {
        if (process.env['NODE_ENV'] === 'production') {
            console.warn(message);
            mergedConfig.interval = mergedConfig.sliding_duration;
        } else {
            throw new Error(message);
        }
    }

    return mergedConfig;
};

type CarouselIndicatorProps = {
    isActive: boolean;
    interval: number;
    onMouseEnter?: MouseEventHandler;
    onMouseLeave?: MouseEventHandler;
};

const CarouselIndicator: React.FC<CarouselIndicatorProps> = ({
    isActive,
    interval,
    onMouseEnter,
    onMouseLeave,
}) => {
    const className = useMemo(
        () =>
            classNames({
                carousel__indicators__item: true,
                'carousel__indicators__item--active': isActive,
            }),
        [isActive]
    );
    const [isHovered, setIsHovered] = useState(false);

    return (
        <div
            className={className}
            onMouseEnter={(evt) => {
                setIsHovered(true);
                onMouseEnter?.(evt);
            }}
            onMouseLeave={(evt) => {
                setIsHovered(false);
                onMouseLeave?.(evt);
            }}
        >
            <div
                className="carousel__indicators__item__bar"
                style={{
                    transition:
                        isActive && !isHovered
                            ? `all linear ${interval / 1000}s`
                            : 'none',
                }}
            ></div>
        </div>
    );
};

const Carousel = function <ItemDataType>({
    renderItem,
    keyExtractor,
    items,
    animationConfig,
    testID,
}: CarouselProps<ItemDataType>) {
    const mergedAnimationConfig = useMemo(
        () => mergeAnimationConfig(animationConfig || {}),
        [animationConfig]
    );
    const [activeIndex, setActiveIndex] = useCurrentIndex(items);

    // status: active -> true, inactive -> false
    const indicatorStatus = useMemo(() => {
        let status = Array.from({ length: items.length }).fill(
            false
        ) as boolean[];

        if (activeIndex >= 0 && activeIndex < status.length) {
            status[activeIndex] = true;
        }

        return status;
    }, [activeIndex, items.length]);

    // initialize active index manually to trigger the animation
    useEffect(() => {
        if (activeIndex < 0 && items.length > 0) {
            setActiveIndex(0);
        }
    }, [activeIndex, items.length, setActiveIndex]);

    const [cancelInterval, resumeInterval] = useInterval(
        mergedAnimationConfig.interval,
        () => {
            setActiveIndex((i) => i + 1);
        }
    );

    return (
        <div className="carousel" data-testid={testID}>
            <div
                className="carousel__wrapper"
                style={{
                    transform:
                        activeIndex > 0
                            ? `translateX(-${100 * activeIndex}%)`
                            : 'translateX(-0%)',
                    transition: `all linear ${
                        mergedAnimationConfig.sliding_duration / 1000
                    }s`,
                }}
            >
                {/* items */}
                {items.map((item) => {
                    return (
                        <Fragment key={keyExtractor(item)}>
                            {renderItem(item)}
                        </Fragment>
                    );
                })}
            </div>

            {/* indicators */}
            <div className="carousel__indicators">
                {indicatorStatus.map((isActive, index) => (
                    <CarouselIndicator
                        key={index}
                        isActive={isActive}
                        interval={mergedAnimationConfig.interval}
                        onMouseEnter={() => {
                            setActiveIndex(index);
                            cancelInterval();
                        }}
                        onMouseLeave={() => {
                            setActiveIndex(index + 1);
                            resumeInterval();
                        }}
                    />
                ))}
            </div>
        </div>
    );
};

export default deepMemo(Carousel);
