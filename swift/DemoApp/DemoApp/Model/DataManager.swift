//
//  DataManager.swift
//  DemoApp
//
//  Created by liang on 2022/5/19.
//

import Foundation
import UIKit

class DataManager: ObservableObject {
    @Published var appList: [AppModel] = []
    var dataProvider: DataProvider
    var hasMore: Bool = true
    
    init(dataProvider: DataProvider) {
        self.dataProvider = dataProvider
    }
    
    func fetchMore(_ completed: @escaping ()-> Void) {
        if hasMore {
            dataProvider.requestModelList(from: appList.last, count: 10) {[self] (modelList, err) in
                DispatchQueue.main.async {
                    if let modelList = modelList {
                        appList.append(contentsOf: modelList)
                        if modelList.count < 10 {
                            hasMore = false
                        }
                        completed()
                    } else {
                        // TODO: 处理异常
                    }
                }
            }
        }
    }
    
    func refresh(_ completed: @escaping ()-> Void) {
        dataProvider.requestModelList(from: nil, count: 10) {[self] (modelList, err) in
            DispatchQueue.main.async {
                if let modelList = modelList {
                    appList = modelList
                    if modelList.count < 10 {
                        hasMore = false
                    } else {
                        hasMore = true
                    }
                    completed()
                } else {
                    // TODO: 处理异常
                }
            }
        }
    }
}
