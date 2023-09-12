//
//  GTMRefreshConstant.swift
//  GTMRefresh
//
//  Created by luoyang on 2016/12/7.
//  Copyright © 2016年 luoyang. All rights reserved.
//

import UIKit

class GTMRefreshConstant {
    static let slowAnimationDuration: TimeInterval = 0.4
    static let fastAnimationDuration: TimeInterval = 0.25
    
    
    static let keyPathContentOffset: String = "contentOffset"
    static let keyPathContentInset: String = "contentInset"
    static let keyPathContentSize: String = "contentSize"
    //    static let keyPathPanState: String = "state"
    
    
    static var associatedObjectGtmHeader = 0
    static var associatedObjectGtmFooter = 1
    
    static let debug = true
}

public func GTMRLocalize(_ string:String)->String{
    return NSLocalizedString(string, tableName: "Localize", bundle: Bundle(for: DefaultGTMRefreshHeader.self), value: "", comment: "")
}
public struct GTMRHeaderString{
    static public let pullDownToRefresh = GTMRLocalize("pullDownToRefresh")
    static public let releaseToRefresh = GTMRLocalize("releaseToRefresh")
    static public let refreshSuccess = GTMRLocalize("refreshSuccess")
    static public let refreshFailure = GTMRLocalize("refreshFailure")
    static public let refreshing = GTMRLocalize("refreshing")
}

public struct GTMRFooterString{
    static public let pullUpToRefresh = GTMRLocalize("pullUpToRefresh")
    static public let loadding = GTMRLocalize("loadMore")
    static public let noMoreData = GTMRLocalize("noMoreData")
    static public let releaseLoadMore = GTMRLocalize("releaseLoadMore")
    //static public let scrollAndTapToRefresh = GTMRLocalize("scrollAndTapToRefresh")
}
