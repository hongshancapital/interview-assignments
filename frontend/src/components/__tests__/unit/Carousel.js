import React from 'react';
import {Carousel} from '../../carousel';
import {configure, shallow,} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import 'jest-enzyme';

configure({adapter: new Adapter()});


it('轮播图有一个容器swipe', () => {
    const wrapper = shallow(<Carousel/>);
    expect(wrapper.find('.swipe')).toExist();
});

