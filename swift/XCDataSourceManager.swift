//
//  XCDataSourceManager.swift
//  XCWorks
//
//  Created by 张兴程 on 2022/12/26.
//

import UIKit

class XCDataSourceManager: NSObject {
    
    // 页面数据源
    var dataSourceArray : NSMutableArray = []
    
    typealias processDataBlock = () -> Void
    
    var processFinishBlock : processDataBlock?
    
    override init() {
        super.init()
        let xcFirstFloorModel = XCFirstFloorModel.init()
        self.dataSourceArray.add(xcFirstFloorModel)
    }
    
    /// 处理数据（第一次进入、上拉加载更多）
    func processData(result : NSDictionary){
        let results = result["results"] as Any
        for dic in (results as! Array<Any>) {
            let nsDic = dic as! [NSObject : AnyObject]
            let model = XCHomePageFeedModel.mj_object(withKeyValues: nsDic)
            self.dataSourceArray.add(model as Any)
        }
        if self.processFinishBlock != nil {
            self.processFinishBlock?()
        }
    }
    
    /// 下拉刷新
    func refreshData(result : NSDictionary){
        // 下拉刷新第一次移除全部的数据
        self.dataSourceArray.removeAllObjects()
        
        // 一楼App文案楼层占坑位
        let xcFirstFloorModel = XCFirstFloorModel.init()
        self.dataSourceArray.add(xcFirstFloorModel)
        
        // 后续支持分页楼层数据处理
        let results = result["results"] as Any
        for dic in (results as! Array<Any>) {
            let nsDic = dic as! [NSObject : AnyObject]
            let model = XCHomePageFeedModel.mj_object(withKeyValues: nsDic)
            self.dataSourceArray.add(model as Any)
        }
        if self.processFinishBlock != nil {
            self.processFinishBlock?()
        }
    }
}
