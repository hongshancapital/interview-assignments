import Big from 'big.js';

export const getPercent = (a: number, b: number) => Big(a).div(Big(b)).times(100).toFixed(2).toString();
