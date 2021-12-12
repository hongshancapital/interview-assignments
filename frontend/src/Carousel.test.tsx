import {render, screen} from '@testing-library/react';
import {shallow} from 'enzyme';
import React from 'react';
import Carousel from './Carousel';

describe('Carousel component test case', () => {

    test(`children could render without errors`, () => {
        render(<Carousel>
                <div style={{backgroundColor: 'red'}}>123</div>
            </Carousel>
        );
       expect(screen.getByText('123')).toBeInTheDocument();
    });

    test(`props received`, () => {
        const instance = shallow(<Carousel interval={3000}></Carousel>);
        const {interval} = instance.props();
        expect(interval).toBe(3000);
    });
});