//
//  WebAppListResource.swift
//  
//
//  Created by 黄磊 on 2022/4/10.
//

import Combine
import MJWebInterface

struct WebAppListResource: WebRequestable {
    
    typealias Response = AppListResponse<AppInfo>
    
    /// 请求接口
    static var action : String = "App.loadByPage"
    
    /// 请求方式，暂时只支持 GET 、 POST
    static var method : String = "GET"
    
    var startIndex : Int = 0
    var pageSize : Int? = nil
}


final class AppListResponse<T: Codable> : Codable, ObservableObject {
    
    var totalCount: Int = 0
    var startIndex: Int = 0
    var dataList: [T] = []
}
