import airpods from "../assets/airpods.png";
import iphone from "../assets/iphone.png";
import tablet from "../assets/tablet.png";
export const CarouselConfig = { // 用于配置轮播图
    indicatorIsShow: true, // 指示器是否展示
    delay: 3000, // 轮播图展示时间 （单位毫秒）
    initialIndex: 0, //初始展示轮播图
    transitionTime: 1000 // 轮播图过渡耗时 （单位毫秒）
}
export const CarouselBannerConfig = [
    {
        id: "xPhone",
        content: [
            {
                name: "xPhone",
                style: {
                    color: '#fff',
                    textAlign: 'center',
                    fontSize: '90',
                    fontWeight: 800,
                }
            },
            {
                name: "Lots to love. Less to spend.",
                style: {
                    color: '#fff',
                    textAlign: 'center',
                    fontSize: '50px',
                    fontWeight: 500,
                },
            },
            {
                name: "Starting at $399.",
                style: {
                    color: '#fff',
                    textAlign: 'center',
                    fontSize: '50px',
                    fontWeight: 500,
                }
            }
        ],
        imageUrl: iphone,
        color: "#fff",
        backgroundColor: ""
    }, {
        id: "Tablet",
        content: [
            {
                name: "Tablet",
                style: {

                }
            },
            {
                name: "Just the right amount of everything",
                style: {

                }
            }
        ],
        imageUrl: airpods,
        color: "#fff",
        backgroundColor: ""
    }, {
        id: "Tablet or xPhone",
        content: [
            {
                name: "Buy a Tablet or xPhone for college.",
                style: {

                }
            },
            {
                name: "Get arPods.",
                style: {

                }
            }
        ],
        imageUrl: tablet,
        color: "#fff",
        backgroundColor: ""
    },
]

