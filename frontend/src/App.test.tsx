import React from 'react';
import App from './App';
import {configure, shallow} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import 'jest-enzyme';

configure({adapter: new Adapter()});


test('app exist', () => {
    const wrapper = shallow(<App/>);
    expect(wrapper.find('.App')).toExist();
});

