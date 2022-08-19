import React from 'react';
import { ICON_NAME } from './index.d';

const Icons = {
  [ICON_NAME.AIRPODS]: require('../assets/airpods.png'),
  [ICON_NAME.IPHONE]: require('../assets/iphone.png'),
  [ICON_NAME.TABLET]: require('../assets/tablet.png'),
}


export default function Icon({name}:{name: ICON_NAME}):JSX.Element{
  return <img src={Icons[name]} alt={`${name}`} />
};