/*
 * @Author: longsi
 * @Date: 2022-06-11 13:52:37
 * @LastEditors: longsi
 * @LastEditTime: 2022-06-14 14:55:36
 * @FilePath: /interview-assignments/frontend/src/constants/index.ts
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */

import ImgAirpods from '../assets/airpods.png';
import ImgIphone from '../assets/iphone.png';
import ImgTablet from '../assets/tablet.png';

const carouselSettings: {
    title: string[];
    desc: string[];
    img: string;
    backgroundColor: string;
    color: string;
    key: string
}[] = [
        {
            title: ['xPhone'],
            desc: ["Lots to love. Less to spend.", "Starting at $399"],
            img: ImgIphone,
            backgroundColor: '#111111',
            color: 'white',
            key: 'imgIphone',
        },
        {
            title: ["Tablet"],
            desc: ["Just the right amout of everything."],
            img: ImgTablet,
            backgroundColor: '#fafafa',
            color: 'black',
            key: 'imgTablet'
        },
        {
            title: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
            desc: [],
            img: ImgAirpods,
            backgroundColor: '#f1f1f1',
            color: 'black',
            key: 'imgAirpods'
        }
    ];

export default carouselSettings;