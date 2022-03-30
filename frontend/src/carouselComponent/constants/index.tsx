interface type {
    iconType: 'iphone' | 'airpods' | 'tablet';
    fontColor?: string;
    backgroundColor?: string;
    title?: string[];
    text?: string[];
}
export const CAROUSEL_LIST: type[] = [
    {
      backgroundColor: '#000000',
      fontColor: '#ffffff',
      iconType: 'iphone',
      title: ['xPhone'],
      text: [
        'Lots to love. Less to spend.',
        'Starting at $399.',
      ],
    },
    {
      backgroundColor: '#ffffff',
      iconType: 'tablet',
      title: ['Tablet'],
      text: [
        'Just the right amount of everything.',
      ],
    },
    {
      backgroundColor: '#eeeeee',
      iconType: 'airpods',
      title: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
    },
  ];