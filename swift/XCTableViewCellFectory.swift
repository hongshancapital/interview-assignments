//
//  XCTableViewCellFectory.swift
//  XCWorks
//
//  Created by 张兴程 on 2022/12/26.
//

import UIKit

let XC_HOMEPAGE_FEED_IDENTIFIER = "homePageFeedIdentifier"
let XC_FIRST_FLOOR_IDENTIFIER = "firstFloorIdentifier"

class XCTableViewCellFectory: NSObject, UITableViewDelegate, UITableViewDataSource {
    
    /// 定义block
    typealias xcScrollDidScrollBlock = (_ scrollView : UIScrollView) -> Void
    /// ScrollView滚动
    var xcScrollViewDidScrollBlock : xcScrollDidScrollBlock?
    
    /// 定义block
    typealias refreshBlock = () -> Void
    /// 刷新TableView
    var refreshTableViewBlock : refreshBlock?
    
    var xcDataSourceManager : XCDataSourceManager?
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if xcScrollViewDidScrollBlock != nil {
            self.xcScrollViewDidScrollBlock!(scrollView)
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if xcDataSourceManager?.dataSourceArray == nil {
            return 0
        } else {
            return (xcDataSourceManager?.dataSourceArray.count)!
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let cellModel = xcDataSourceManager?.dataSourceArray[indexPath.row]
        if cellModel is XCHomePageFeedModel {
            let model = cellModel as?XCHomePageFeedModel
            return (model?.xcCellHeigh)!
        } else if cellModel is XCFirstFloorModel {
            let model = cellModel as?XCFirstFloorModel
            return (model?.xcCellHeigh)!
        } else {
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cellModel = xcDataSourceManager?.dataSourceArray[indexPath.row]
        
        if cellModel is XCHomePageFeedModel {
            /// (首页楼层)
            let model = cellModel as?XCHomePageFeedModel
            
            var xchomePageCell = tableView.dequeueReusableCell(withIdentifier: XC_HOMEPAGE_FEED_IDENTIFIER) as! XCHomePageFeedCell
            if xchomePageCell == nil {
                let cell = XCHomePageFeedCell.init(style: .default, reuseIdentifier: XC_HOMEPAGE_FEED_IDENTIFIER)
                xchomePageCell = cell
            }
            xchomePageCell.xcHomePageFeedModel = model
            xchomePageCell.refreshCellUI()
            weak var weakSelf = self
            xchomePageCell.heartRefreshBlock = { () -> Void in
                if weakSelf?.refreshTableViewBlock != nil {
                    weakSelf?.refreshTableViewBlock!()
                }
            }
            return xchomePageCell
        } else if cellModel is XCFirstFloorModel {
            let model = cellModel as?XCFirstFloorModel
            
            var xcFirstFloorCell = tableView.dequeueReusableCell(withIdentifier: XC_FIRST_FLOOR_IDENTIFIER) as! XCFirstFloorCell
            if xcFirstFloorCell == nil {
                let cell = XCFirstFloorCell.init(style: .default, reuseIdentifier: XC_FIRST_FLOOR_IDENTIFIER)
                xcFirstFloorCell = cell
            }
            
            xcFirstFloorCell.xcFirstFloorModel = model
            xcFirstFloorCell.refreshCellUI()
            return xcFirstFloorCell
        } else {
            var deaultCell = tableView.dequeueReusableCell(withIdentifier: "deaultCell")
            if deaultCell == nil {
                deaultCell = UITableViewCell.init(style: .default, reuseIdentifier: "deaultCell")
            }
            return deaultCell!
        }
    }
    
    
}
