const base = {
    duration: '1s',
    timingFunction: 'linear',
    fillMode: 'forwards',
};
export const animations = {
    entry: {
        ...base,
        keyframes: `{
            from{
                transform: translateX(100%);
                }
            to {
                transform: translateX(0);
            }
        }`
    },
    exit: {
        ...base,
        keyframes: `{
            from{
                transform: translateX(0);
                }
            to {
                transform: translateX(-100%);
            }
        }`
    }
};