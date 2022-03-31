/**
 * @version 0.0.1
 * @author curryfu
 * @date 2022-03-30
 * @description 轮播图配置
 */
import airpods from "../assets/airpods.png";
import iphone from "../assets/iphone.png";
import tablet from "../assets/tablet.png";


interface CarouselConfigInter {
    indicatorIsShow: boolean;
    delay: number;
    initialIndex: number;
    transitionTime: number;
}

export type ContentItem = { name: string } & { id: number } & { style: Record<string, string | Number> }

export interface CarouselBannerConfigInter {
    id: string;
    content: ContentItem[];
    imageUrl: string;
    color: string;
    backgroundColor: string;
}

export const CarouselConfig: CarouselConfigInter = { // 用于配置轮播图
    indicatorIsShow: true, // 指示器是否展示
    delay: 3000, // 轮播图展示时间 （单位毫秒）
    initialIndex: 0, //初始展示轮播图
    transitionTime: 1000 // 轮播图过渡耗时 （单位毫秒）
}
export const CarouselBannerConfig: CarouselBannerConfigInter[] = [
    {
        id: "xPhone",
        content: [
            {
                id: 1,
                name: "xPhone",
                style: {
                    color: '#fff',
                    textAlign: 'center',
                    fontSize: '90px',
                    fontWeight: 500,
                    marginTop: "20vh"
                }
            },
            {
                id: 2,
                name: "Lots to love. Less to spend.",
                style: {
                    color: '#fff',
                    textAlign: 'center',
                    fontSize: '40px',
                    fontWeight: 500,
                },
            },
            {
                id: 3,
                name: "Starting at $399.",
                style: {
                    color: '#fff',
                    textAlign: 'center',
                    fontSize: '40px',
                    fontWeight: 500,
                }
            }
        ],
        imageUrl: iphone,
        color: "#fff",
        backgroundColor: "#111111"
    }, {
        id: "Tablet",
        content: [
            {
                id: 1,
                name: "Tablet",
                style: {
                    color: '#000',
                    textAlign: 'center',
                    fontSize: '40px',
                    fontWeight: 500,
                    marginTop: "20vh"
                }
            },
            {
                id: 2,
                name: "Just the right amount of everything",
                style: {
                    color: '#000',
                    textAlign: 'center',
                    fontSize: '20px',
                    fontWeight: 500,
                }
            }
        ],
        imageUrl: airpods,
        color: "#fff",
        backgroundColor: "#F1F1F3"
    }, {
        id: "Tablet or xPhone",
        content: [
            {
                id: 1,
                name: "Buy a Tablet or xPhone for college.",
                style: {
                    color: '#000',
                    textAlign: 'center',
                    fontSize: '40px',
                    fontWeight: 800,
                    marginTop: "20vh"
                }
            },
            {
                id: 2,
                name: "Get arPods.",
                style: {
                    color: '#000',
                    textAlign: 'center',
                    fontSize: '40px',
                    fontWeight: 800,
                }
            }
        ],
        imageUrl: tablet,
        color: "#fff",
        backgroundColor: "#FAFAFA"
    },
]

