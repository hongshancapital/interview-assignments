import React from 'react';
import { render,screen } from '@testing-library/react';
import App from './App';

test('app是否渲染Carousel组件', () => {
    render(<App role='app' />);
    expect(screen.getByRole('app').className).toBe('Carousel');
});
