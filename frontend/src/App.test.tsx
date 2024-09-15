import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('dom test', () => {
  setTimeout(() => {
    const contentAniEL: HTMLElement | null = document.querySelector('.contentAni');
    expect(contentAniEL?.dataset['index']).toBe('1');
  }, 5000);
});