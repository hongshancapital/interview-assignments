export const containerStyle = `
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const backgroundStyle = `
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  width: 100%;
  height: 100%;
  max-height: 900px;
  object-fit: cover;
`;

export const titleStyle = `
  position: relative;
  font-size: 40px;
  z-index: 1;
  transform: translateY(-100px);
  font-weight: bold;
`;

export const descriptionStyle = `
  position: relative;
  font-size: 20px;
  z-index: 1;
  transform: translateY(-100px);
`;
