import React from 'react';
import { Tabs } from 'antd';
import { GetOriginLink, GetShortLink } from '@components';

import 'antd/dist/antd.min.css';
import './App.css';

const { TabPane } = Tabs;

function App() {
  return (
    <div className="App">
      <div className="title">Short Link Service</div>
      <Tabs defaultActiveKey="1">
        <TabPane tab="Make Short Link" key="1">
          <GetShortLink />
        </TabPane>
        <TabPane tab="Get Origin Link" key="2">
          <GetOriginLink />
        </TabPane>
      </Tabs>
    </div>
  );
}

export default App;
