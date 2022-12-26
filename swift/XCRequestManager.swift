//
//  XCRequestManager.swift
//  XCWorks
//
//  Created by 张兴程 on 2022/12/26.
//

import UIKit

/// 翻页增量
let XC_PAGE_INCREMENT = 10

class XCRequestManager: NSObject {
    
    /// 定义block
    typealias requestCallBackBlock = (_ result : Any) -> Void
    
    /// reslutBlock变量
    var reslutBlock : requestCallBackBlock?
    
    /// 分页当前页数（默认是0）每次+10
    var pageNumber : NSInteger = 0;
    
    /// （0）feed页第一次获取、上拉加载更多
    func feedListRequest (){
        let xcRequest = XCNetWork.shareInstance
        // 接口不支持分页，本地假分页
        let urlString = "https://itunes.apple.com/search?entity=software&limit=20&term=chat"
//        let urlString = "https://itunes.apple.com/search?"
        let parameters = ["entity" : "software", "client_secret" : 2, "grant_type" : "chat"] as [String : Any]
        xcRequest.request(methodType: .POST, urlString: urlString, parameters: parameters as [NSString : Any]) { result, error in
            if self.reslutBlock != nil {
                self.reslutBlock?(result as Any)
            }
        }
    }
    
    /// （1）下拉刷新
    func feedListLoadRefreshRequest (){
        let xcRequest = XCNetWork.shareInstance
        // 接口不支持分页，本地假分页
        let urlString = "https://itunes.apple.com/search?entity=software&limit=20&term=chat"
//        let urlString = "https://itunes.apple.com/search?"
        let parameters = ["entity" : "software", "client_secret" : 2, "grant_type" : "chat"] as [String : Any]
        xcRequest.request(methodType: .POST, urlString: urlString, parameters: parameters as [NSString : Any]) { result, error in
            if self.reslutBlock != nil {
                self.reslutBlock?(result as Any)
            }
        }
    }
}
