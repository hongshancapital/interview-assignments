import React from 'react';
import { render, screen } from '@testing-library/react';
import Adapter from 'enzyme-adapter-react-16';
import { shallow, configure } from 'enzyme';
configure({adapter: new Adapter()});

import Carousel from '..';

describe('Carousel component test case', () => {

    test(`children could render without errors`, () => {
        render(<Carousel>
                <Carousel.Slider >
                    <div>single</div>
                </Carousel.Slider>
            </Carousel>
        );
       expect(screen.getByText('single')).toBeInTheDocument();
    });

    test(`props received`, () => {
        const instance = shallow(<Carousel autoplay speed={3000}>
                <Carousel.Slider >
                    <div>1</div>
                </Carousel.Slider>
                <Carousel.Slider >
                    <div>2</div>
                </Carousel.Slider>
            </Carousel>
        );
        const { autoplay, speed} = instance.props();
        expect(autoplay).toBe(true);
        expect(speed).toBe(3000);

    });

    test('should has prev, next and go function', () => {
        const ref = React.createRef();
        render(
          <Carousel ref={ref}>
            <Carousel.Slider >
                <div>1</div>
            </Carousel.Slider>
          </Carousel>,
        );
        const { prev, next, goTo } = ref.current;
        expect(typeof prev).toBe('function');
        expect(typeof next).toBe('function');
        expect(typeof goTo).toBe('function');
    });

    test('switch page', () => {
        const ref = React.createRef();
        screen.debug()

        render(
          <Carousel ref={ref}>
            <Carousel.Slider >
                <div>1</div>
            </Carousel.Slider>
            <Carousel.Slider >
                <div>2</div>
            </Carousel.Slider>
            <Carousel.Slider >
                <div>3</div>
            </Carousel.Slider>
            <Carousel.Slider >
                <div>4</div>
            </Carousel.Slider>
          </Carousel>,
        );
        const { prev, next, goTo } = ref.current;
        goTo(3);
        jest.useFakeTimers();
        expect(screen.getByText('3')).toBeInTheDocument();
        prev();
        jest.useFakeTimers();
        expect(screen.getByText('2')).toBeInTheDocument();
        next();
        jest.useFakeTimers();
        expect(screen.getByText('3')).toBeInTheDocument();

        
    });


})