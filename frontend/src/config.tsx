import iphoneIcon from './assets/iphone.png';
import tabletIcon from './assets/tablet.png';
import airpodsIcon from './assets/airpods.png';


// 将carousel 抽离成一个可配置化的组件
// 根据一下配置扩展 carousel

export type IConfig = {
    titleStrings: string[];
    textStrings: string[];
    style?: React.CSSProperties;
    image?: string; 
}

export const config:IConfig[] = [
    {
        titleStrings: ['xPhone'],
        textStrings: ['Lots to love.Less to spend.', 'Strating at $399.'],
        style: {
            background: '#111111',
            color: 'white',
        },
        image: iphoneIcon
    },
    {
        titleStrings: ['Tablet'],
        textStrings: ['Just the right amount of everything'],
        style: {
            background: '#fafafa',
            color: 'back'
        },
        image: tabletIcon
    },
    {
        titleStrings: ['Buy a Tablet or xPhone for college.', 'Get arPods'],
        textStrings: [],
        style: {
            background: '#f1f1f3',
        },
        image: airpodsIcon
    },
]