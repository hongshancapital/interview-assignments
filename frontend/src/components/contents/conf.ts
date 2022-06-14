import iphoneIcon from '../../assets/iphonex.png';
import ipadIcon from '../../assets/ipad.png';
import airpodIcon from '../../assets/airpod.png';


interface ContentItemType {
  title: string;
  describetion: string;
  imgUrl: string;
  style?: {
    backgroundColor?: string;
    color?:string
  };
}

interface ContentConfType {
  [x: string]: any;
  [index: number]: ContentItemType;
}

const contentConf: ContentConfType = [
  {
    title: 'xPhone',
    describetion: 'Lots to love.Less to spend.<br/> Starting at &399.',
    imgUrl: iphoneIcon,
    style: {
      backgroundColor: 'black',
      color: 'white',
    },
  },
  {
    title: 'Tablet',
    describetion: 'Just the right amount of everything',
    imgUrl: ipadIcon,
  },
  {
    title: 'Buy a Tablet or xPhone for college.<br/>Get arpods',
    describetion: '',
    imgUrl: airpodIcon,
    style: {
      backgroundColor: '#f7f1f1',
    },
  },
];
export type { ContentItemType };
export { contentConf };