import React from 'react';
import { render} from '@testing-library/react';
import {Progress} from './index';

describe('Progress test', () => {
    it('determine active ', () => {
        const { container } = render(<Progress />);
        const el = container.querySelector('.progress-schedule');
        // 默认状态是否是激活
        expect(el).not.toHaveClass('active');
        // 默认状态动画为2000ms
        expect(el).toHaveStyle({'animation-duration': '2000ms'})
    })
    
    it('determine inactivated', () => {
        const { container } = render(<Progress active={true} duration={3000} />);
        const el = container.querySelector('.progress-schedule');
        // 有参数状态是否有active class
        expect(el).toHaveClass('progress-active');
        // 传入duration是否生效
        expect(el).toHaveStyle({'animation-duration': '3000ms'})
    })
})
