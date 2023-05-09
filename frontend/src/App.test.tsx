/*
 * @Description: 
 * @Author: cmh
 * @Date: 2023-03-01 14:27:46
 * @LastEditTime: 2023-03-02 15:20:27
 * @LastEditors: cmh
 */
import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
   render(<App />);
    const linkElement = screen.getAllByText(/xPhone/);
    expect(linkElement).toBeTruthy();
});
