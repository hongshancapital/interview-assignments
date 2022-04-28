import React, { FC } from "react";
import Banner from "../Banner";

import imgIphone from '../../assets/iphone.png';
import imgTablet from '../../assets/tablet.png';
import imgAirpods from '../../assets/airpods.png';

export const Iphone: FC = () => {
    return (
      <Banner 
        title="xPhone" 
        subtitle={['Lots to love. Less to spend.', 'Starting at $399']}
        style={{
          color: 'white',
          backgroundImage: `url(${imgIphone})`,
          backgroundRepeat: 'no-repeat',
          backgroundPosition: 'center bottom 100px',
          backgroundSize: 'auto 600px',
          backgroundColor: '#111111',
        }}
      />
    )
  };
  
export const Tablet: FC = () => {
    return (
      <Banner 
        title="Tablet" 
        subtitle='Just the right amount of everything.'
        style={{
          backgroundImage: `url(${imgTablet})`,
          backgroundRepeat: 'no-repeat',
          backgroundPosition: 'center bottom 100px',
          backgroundSize: 'auto 600px',
          backgroundColor: '#fafafa',
        }}
      />
    )
  };
  
export const Airpods: FC = () => {
    return (
      <Banner 
        title={['Buy a Tablet or xPhone for college.', 'Get arPods.']}
        style={{
          backgroundImage: `url(${imgAirpods})`,
          backgroundRepeat: 'no-repeat',
          backgroundPosition: 'center bottom 100px',
          backgroundSize: 'auto 600px',
          backgroundColor: '#f1f1f3',
        }}
      />
    )
  };