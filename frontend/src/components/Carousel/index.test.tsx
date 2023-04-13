import { fireEvent, render } from '@testing-library/react';
import Carousel, { CarouselProps } from '.';
import { act } from 'react-dom/test-utils';

describe('Carousel', () => {
    const OLD_ENV = process.env;

    beforeEach(() => {
        jest.resetModules();
        process.env = { ...OLD_ENV };
    });

    afterAll(() => {
        process.env = OLD_ENV;
    });

    const items = [{ text: 'hello' }, { text: 'world' }];
    type Item = typeof items[0];
    const testID = 'carousel';
    const renderCarousel = (partialProps?: Partial<CarouselProps<Item>>) => {
        const props: CarouselProps<Item> = Object.assign(
            {},
            {
                testID,
                renderItem: (item: Item) => {
                    return <div>{item.text}</div>;
                },
                items,
                keyExtractor: (item: Item) => item.text,
            },
            partialProps
        );

        return render(<Carousel {...props} />);
    };
    const checkIndex = (
        index: number,
        items: any[],
        rootElement: HTMLElement
    ) => {
        if (items.length === 0) {
            return;
        }

        expect(index).toBeLessThan(items.length);
        expect(index).toBeGreaterThanOrEqual(0);

        const wrapperElement = rootElement.querySelector('.carousel__wrapper');

        expect(wrapperElement).toBeInTheDocument();
        expect(wrapperElement).toHaveStyle({
            transform: `translateX(-${100 * index}%)`,
        });
    };

    it('should render all items', () => {
        const { getByTestId, getByText } = renderCarousel();
        const rootElement = getByTestId(testID);

        for (let item of items) {
            const element = getByText(item.text);

            expect(element).toBeInTheDocument();
            expect(element.clientWidth).toEqual(rootElement.clientWidth);
        }
    });

    it('should work without any items', () => {
        const { getByTestId } = renderCarousel({ items: [] });

        expect(getByTestId(testID)).toBeInTheDocument();
    });

    it('should warn on production if interval is less than sliding_duration', () => {
        // @ts-ignore
        process.env.NODE_ENV = 'production';

        const consoleWarnMock = jest
            .spyOn(console, 'warn')
            .mockImplementation();

        renderCarousel({
            animationConfig: {
                interval: 1000,
                sliding_duration: 2000,
            },
        });

        expect(console.warn).toBeCalledTimes(1);
        expect(console.warn).toBeCalledWith(
            '[CarouselAnimationConfig]: interval should be greater than the sliding_duration'
        );

        consoleWarnMock.mockRestore();
    });

    it('should throw on non production if interval is less than sliding_duration', () => {
        // @ts-ignore
        process.env.NODE_ENV = 'development';

        const consoleErrorMock = jest
            .spyOn(console, 'error')
            .mockImplementation();

        expect(() =>
            renderCarousel({
                animationConfig: {
                    interval: 1000,
                    sliding_duration: 2000,
                },
            })
        ).toThrow(
            '[CarouselAnimationConfig]: interval should be greater than the sliding_duration'
        );

        consoleErrorMock.mockRestore();
    });

    it('should go to next index after interval', () => {
        jest.useFakeTimers();

        const interval = 1000;
        const { getByTestId } = renderCarousel({
            animationConfig: { interval },
        });
        const rootElement = getByTestId(testID);

        checkIndex(0, items, rootElement);

        act(() => {
            jest.advanceTimersByTime(interval);
        });

        checkIndex(1, items, rootElement);
    });

    it('should stop animating when hovering on indicators', () => {
        jest.useFakeTimers();

        const interval = 1000;
        const items2 = [...items, { text: 'hi' }];
        const { getByTestId } = renderCarousel({
            animationConfig: { interval },
            items: items2,
        });
        const rootElement = getByTestId(testID);

        act(() => {
            jest.advanceTimersByTime(interval);
        });

        checkIndex(1, items2, rootElement);

        const secondIndicator = rootElement.querySelector(
            '.carousel__indicators__item:nth-child(2)'
        );

        expect(secondIndicator).toBeInTheDocument();

        // hover
        act(() => {
            fireEvent.mouseEnter(secondIndicator!);
        });
        // interval
        act(() => {
            jest.advanceTimersByTime(interval);
        });

        // should stay
        checkIndex(1, items2, rootElement);

        // leave
        act(() => {
            fireEvent.mouseLeave(secondIndicator!);
        });

        // should advance
        checkIndex(2, items2, rootElement);
    });
});
