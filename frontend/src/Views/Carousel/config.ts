export interface params {
    title: string;
    text?: string;
    imgSrc?: string;
}


export const config: params[] = [
    {
        title: "xPhone",
        text: "Lots to love. Less to spend.\nStarting at $399.",
        imgSrc: require("../../assets/img-0.png")
    },
    {
        title: "Tablet",
        text: "Just the right amount of everything.",
        imgSrc: require("../../assets/img-1.png")
    },
    {
        title: "Buy a tablet or xPhone for college.\nGet airPods",
        text: "",
        imgSrc: require("../../assets/img-2.png")
    }
];