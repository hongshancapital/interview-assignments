import { useState, useEffect } from 'react';

export interface IgoodsDatas {
    title: string | Array<string>,
    details: Array<string> | [],
    img: string,
    backgroundColorTheme: string,
    fontColorTheme: string,
}

export function useGoodsDatas(): Array<IgoodsDatas> | [] {
    const initialGoodsDatas: Array<IgoodsDatas> | [] = []
    const [goodsData, setGoodsData] = useState(initialGoodsDatas);

    useEffect(() => {
        // setTimeoust模拟数据获取
        const goodsDatas = [
            {
              title: 'xPhone',
              details: ['lots to love.Less to spend.', 'Starting at $399.'],
              img: 'iphone.png',
              backgroundColorTheme: '#111111',
              fontColorTheme: '#fafafa',
            },
            {
              title: 'Tablet',
              details: ['Just the right amount of everything'],
              img: 'tablet.png',
              backgroundColorTheme: '#fafafa',
              fontColorTheme: '#111111',
            },
            {
              title: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
              details: [],
              img: 'airpods.png',
              backgroundColorTheme: '#fafafa',
              fontColorTheme: '#111111',
            },
          ]
        setTimeout(() => {
            setGoodsData(goodsDatas)
        }, 200)
    }, [])

    return goodsData;
}