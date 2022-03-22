import { AppProps } from 'next/app';
import Head from 'next/head';
import { useSWRConfig } from 'swr';
import { logoApi } from '@/api/data';

import '@/styles/global.scss';

export default function App({
  Component,
  pageProps
}: AppProps) {
  const { cache } = useSWRConfig();
  const { data: logoData = [{}] } = cache.get(logoApi) || {}
  return (
    <>
    <Head>
      <link rel="icon" href={logoData[0]?.icon} />
      <meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
      {/* <script src="//at.alicdn.com/t/font_3189071_pha284yzpo9.js"></script> */}
      {/* <style type="text/css" src="//at.alicdn.com/t/font_8d5l8fzk5b87iudi.css"></style> */}
    </Head>
    {/* <MainView> */}
      <Component {...pageProps} />
    {/* </MainView> */}
    </>
  )
}
