//
//  DataProvider.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/15.
//

import SwiftUI

actor DataProvider{
    private lazy var dataModel: (AppListResultModel?, DataError?) = {
        return DataLoader<AppListResultModel>.load(filename: "applist.json")
    }()
    
    //disable public constructors to keep singletons unique
    private init() {}
    
    //returns items from the given page
    //page: page number
    //pageSize: the number of items per page
    public func items(_ page: Int,_ pageSize: Int) async throws ->([AppProductModel], DataError?){
        
        try await Task.sleep(until: .now + .seconds(1), clock: .continuous)
        
        if dataModel.1 != nil{
          return ([], dataModel.1)
        }
        
        let datas: [AppProductModel] = dataModel.0?.results ?? []
        if datas.isEmpty{
            return ([], nil)
        }
        
        
        let start = (page - 1) * pageSize
        
        if start >= datas.count{
            return ([], nil)
        }
        
        if datas.count <= pageSize {
            return (datas, nil)
        }
        
        let toIndex = min(start + pageSize,datas.count)
        let result  = Array(datas[start..<toIndex])
                
        return (result,nil)
    }
}

extension DataProvider{
    public static let shared = DataProvider()
    
}
