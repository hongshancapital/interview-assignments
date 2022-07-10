import React from 'react';
import iphoneIcon from '../../assets/iphonex.png';
import ipadIcon from '../../assets/ipad.png';
import airpodIcon from '../../assets/airpod.png';

interface ContentItemType {
  Title: Function;
  Describetion: Function;
  imgUrl: string;
  style?: {
    backgroundColor?: string;
    color?: string;
  };
}

interface ContentConfType {
  [x: string]: any;
  [index: number]: ContentItemType;
}

const contentConf: ContentConfType = [
  {
    Title: () => {
      return <h1>xPhone</h1>;
    },
    Describetion: () => {
      return (
        <p>
          Lots to love.Less to spend.
          <br /> Starting at &399.
        </p>
      );
    },
    imgUrl: iphoneIcon,
    style: {
      backgroundColor: 'black',
      color: 'white',
    },
  },
  {
    Title: () => {
      return <h1>Tablet</h1>;
    },
    Describetion: () => {
      return <p>Just the right amount of everything</p>;
    },
    imgUrl: ipadIcon,
  },
  {
    Title: () => {
      return (
        <h1>
          Buy a Tablet or xPhone for college.
          <br />
          Get arpods
        </h1>
      );
    },
    Describetion:()=>{return null},
    imgUrl: airpodIcon,
    style: {
      backgroundColor: '#f7f1f1',
    },
  },
];
export type { ContentItemType };
export { contentConf };
