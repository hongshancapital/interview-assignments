import { FC, ReactNode } from 'react';
import styles from './index.module.scss';

interface LayoutProps {
  theme: 'dark' | 'light' | 'gray';
  title: ReactNode;
  desc?: ReactNode;
  bg: string;
}

const Layout: FC<LayoutProps> = ({ theme, title, desc, bg }) => {
  return (
    <div
      className={styles.layout + ' ' + styles[theme]}
      style={{ backgroundImage: `url(${bg})` }}
    >
      <div className={styles.title}>{title}</div>
      <div className={styles.desc}>{desc}</div>
    </div>
  );
};

export default Layout;
