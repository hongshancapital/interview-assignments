import React, { FC, useState, useRef } from 'react';
import { createShortLink, getLongLink, getLongRedirect } from '@/api/lambda/short-link';

const ShortLink: FC = () => {

    const [short, setShort] = useState('');
    const [long, setLong] = useState('');

    const shortV = useRef();
    const longV = useRef();

    const changeLong = (e) => {
        longV.current = e.target.value;
    };
    
    // 生成短链
    const handleCreateShortLink = async () => {
        const res = await createShortLink('ppya', 'ppya@test.com', longV.current);
        setShort(res);
    };

    const changeShort = (e) => {
        shortV.current = e.target.value;
    };

    // 获取长链接
    const handleGetLongLink = async () => {
        const res = await getLongLink({
            query: {
                shortLink: shortV.current,
            }
        })
        setLong(res[0].longLink);
    };

    // 重定向：getLongRedirect
    const handRedirect = async () => {
        await getLongRedirect({
            query: {
                shortLink: shortV.current,
            }
        })
    };

    return (
        <div className="home">
            <div>
                <input placeholder="请输入长链接" onChange={changeLong} />
                <span onClick={handleCreateShortLink}>生成短链接</span>
                <div>结果: {short}</div>
            </div>
            <div>
                <input placeholder="请输入短链接" onChange={changeShort} />
                <span onClick={handleGetLongLink}>查询长链接</span>
                <div>结果: {long} </div>
            </div>
            <div onClick={handRedirect}>>访问短链，重定向到长链</div>
        </div>
    )
};

export default ShortLink;
