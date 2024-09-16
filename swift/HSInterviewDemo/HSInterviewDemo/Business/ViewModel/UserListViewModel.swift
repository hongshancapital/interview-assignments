//
//  UserListViewModel.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/9.
//

import Foundation
import APIService
import SVProgressHUD

class UserListViewModel: ObservableObject {
    @Published var total: Int = 0
    @Published var modes: [User] = [User()]
    
    // 下拉刷新, 上拉加载
    @Published var isRefreshing: Bool = false
    @Published var isLoadingMore: Bool = false
    @Published var isReloadData: Bool = false

    init() {
        fetchData()
    }
    
    func fetchData(completion: (() -> Void)? = nil) {
        let launchAdRequest = UserRequestProvider()
        APIService.sendRequest(launchAdRequest) { response, type in
            switch type {
            case .network:
                switch response.result.validateResult {
                case let .success(info, _):
                    self.modes = info + info[0...info.endIndex-1]
//                    SVProgressHUD.showInfo(withStatus: "网络成功响应结果")
                    completion?()
                    self.isRefreshing = false
                    debugPrint(info)
                case let .failure(_, error):
                    debugPrint(error)
                }
            case .cache:
                debugPrint(response)
                if response.result.isSuccess {
//                    SVProgressHUD.showInfo(withStatus: "缓存")
                }
            }
        }
    }
    
    // 获取更多数据
    func fetchMoreData(completion: @escaping () -> Void) {
        let launchAdRequest = UserRequestProvider()
        APIService.sendRequest(launchAdRequest) { response, type in
            switch type {
            case .network:
                switch response.result.validateResult {
                case let .success(info, _):
                    self.modes = self.modes + info[0...info.endIndex-1]
                    completion()
                    debugPrint(info)
                case let .failure(_, error):
                    debugPrint(error)
                }
            case .cache:
                debugPrint(response)
                if response.result.isSuccess {

                }
            }
        }
    }
    
    lazy var networkActivityPlugin: NetworkActivityPlugin = {
        let networkActivityPlugin = NetworkActivityPlugin { change in
            switch change {
            case .began:
                SVProgressHUD.show()
            case .ended:
                SVProgressHUD.dismiss()
            }
        }
        return networkActivityPlugin
    }()
}
