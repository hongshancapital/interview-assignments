//
//  PublicData.swift
//  SwiftUIHomeWork
//
//  Created by Yu jun on 2022/6/25.
//

import Foundation
import Combine
final class PublicData: ObservableObject {
    @Published var reflesh: Bool = false {
        didSet {
            if self.reflesh {
                self.page = 1
                APIClient.shareClient.getAppMessage(page: "\(self.page)", pageNumber: "10") { modelList, error in
                    if modelList != nil {
                        self.reflesh = false
                        self.count = modelList!["count"] as! Int
                        self.showAppList = modelList!["showAppList"] as! [AppMessageModel]
                    }
                }
            }
        }
    }
    @Published var page = 1
    @Published var count = 100
    @Published var showAppList: [AppMessageModel] = []
    init() {
        APIClient.shareClient.getAppMessage(page: "1", pageNumber: "10") { modelList, error in
            if modelList != nil {
                self.count = modelList!["count"] as! Int
                self.showAppList = modelList!["showAppList"] as! [AppMessageModel]
            }
        }
    }
}
