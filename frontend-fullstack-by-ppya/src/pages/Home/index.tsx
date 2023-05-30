import React, { FC } from 'react';
import Carousel from '@/components/Carousel';
import './index.less';

export const dataSource = [
    {
        title: '11111',
        subTitle: 'subTitle11111',
        imgUrl: 'https://avatars.githubusercontent.com/u/10842824?v=4'
    },
    {
        title: '22222',
        subTitle: 'subTitle22222',
        imgUrl: 'https://avatars.githubusercontent.com/u/10842824?v=4'
    },
    {
        title: '33333',
        subTitle: 'subTitle33333',
        imgUrl: 'https://avatars.githubusercontent.com/u/10842824?v=4'
    }
];

const Home: FC = () => {
    return (
        <div className="home">
            <Carousel dataSource={dataSource} />
        </div>
    )
};

export default Home;
