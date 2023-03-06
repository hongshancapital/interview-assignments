/* eslint-disable import/prefer-default-export */
import type { CSSObject } from '../../utils/cssinjs';
import type { DerivativeToken } from '../theme/internal';



export const resetComponent = (token: DerivativeToken): CSSObject => ({
  boxSizing: 'border-box',
  margin: 0,
  padding: 0,
  color: token.colorText,
  fontSize: token.fontSize,
  // font-variant: @font-variant-base;
  lineHeight: token.lineHeight,
  listStyle: 'none',
  // font-feature-settings: @font-feature-settings-base;
  fontFamily: token.fontFamily,
});

