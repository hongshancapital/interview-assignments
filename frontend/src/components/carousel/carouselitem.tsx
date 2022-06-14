/*
 * @Author: longsi
 * @Date: 2022-06-11 14:07:38
 * @LastEditors: longsi
 * @LastEditTime: 2022-06-14 16:09:39
 * @FilePath: /interview-assignments/frontend/src/components/carouselitem/carouselitem.tsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React from 'react';
interface ICarouselitemProps {
    title: string[];
    desc: string[];
    img?: string;
    backgroundColor?: string;
    color?: string;
    key: string;
}

export const Carouselitem: React.FC<ICarouselitemProps> = ({
    title, desc, img, backgroundColor, color, key
}) => {
    return (
        <div
            key={key}
            style={{
                width: '100%',
                height: "100vh",
                overflow: "hidden",
                position: "relative",
                paddingTop: "120px",
                boxSizing: "border-box",
                backgroundColor,
                color,
            }}
        >
            <div>
                {title.map((text) => {
                    return (<p
                        key={text}
                        style={{
                            fontSize: "50px"
                        }}
                    >{text}</p>)
                })}
            </div>
            <div>
                {desc.map((text) => {
                    return (<p
                        key={text}
                        style={{
                            fontSize: "30px"
                        }}
                    >{text}</p>)
                })}
            </div>

            <div>
                <img
                    style={{
                        bottom: 0,
                        width: "100%",
                    }}
                    src={img}
                    alt=""
                />
            </div>
        </div>
    )
}