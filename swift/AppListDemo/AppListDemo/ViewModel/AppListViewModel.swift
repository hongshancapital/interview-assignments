//
//  AppListViewModel.swift
//  AppListDemo
//
//  联系方式：微信(willn1987)
//
//  Created by HQ on 2022/8/18.
//
//

import Foundation

class AppListViewModel: ObservableObject {
    
    /// 已读取的APP列表数据
    @Published var listData: [AppInfoModel] = []
    
    /// 是否有更多数据
    @Published var haveMore = true
    
    /// 是否显示提示视图
    @Published var showToast: Bool = false
    
    /// 显示的提示文案
    var toastMsg: String?
    
    /// 限制每次请求数据的数目
    private var limit = 10
    private var defaultLimit = 10
    
    /// 当前是否正在加载数据
    private var isLoading = false
    
    /// 加载接口类型
    enum LoadType {
        case load
        case refresh
        case loadMore
    }

    init() {
        loadData()
    }
}

// MARK: 接口逻辑
extension AppListViewModel {
    
    /// 加载数据
    func loadData() {
        Task {
            await requestAppListData(.load)
        }
    }
    
    /// 刷新数据
    func refreshData() {
        Task {
            await requestAppListData(.refresh)
        }
    }
    
    /// 加载更多
    func loadMoreData() {
        if haveMore != true {
            return
        }
        
        Task {
            await requestAppListData(.loadMore)
        }
    }
    
    /// 请求数据
    /// - Parameter type: 当前加载类型
    func requestAppListData(_ type: LoadType) async {
        guard !isLoading else { return }
        
        isLoading.toggle()
        
        let url = prepareRequest(type)
        let reuslt = await NetWorkManager.shared.request(url: url, type: ResponseModel<AppInfoModel>.self)
        await handleReuslt(reuslt, type)
        
        isLoading.toggle()
    }
    
    /// 网络请求前准备相关数据
    /// - Parameter type: 当前加载类型
    /// - Returns: 返回接口地址
    private func prepareRequest(_ type: LoadType) -> String {
        limit = type != .loadMore ? defaultLimit : 20
        let url = "https://itunes.apple.com/search?entity=software&country=cn&term=效率&limit=\(limit)"
        return url
    }
    
    /// 统一处理接口返回的数据
    /// - Parameters:
    ///   - reuslt: 返回的数据
    ///   - type: 当前加载类型
    @MainActor
    private func handleReuslt(_ reuslt: ResultModel<ResponseModel<AppInfoModel>>, _ type: LoadType) {
        haveMore = type != .loadMore ? true : false

        switch reuslt {
        case .failure(msg: let msg):
            print(msg)
            if type == .load {
                toastMsg = msg
                showToast = true
            }
        case .success(let data, msg: _):
            guard data.results.count != 0 else {
                print("返回列表为空")
                return
            }
            
            if type == .loadMore, data.results.count > defaultLimit {
                // 接口暂不支持分页，模拟分页效果
                listData.append(contentsOf: data.results.dropFirst(defaultLimit))
            } else {
                listData = data.results
            }
        }
    }
    
    /// 更新对应用的"喜欢"状态
    /// - Parameters:
    ///   - index: 点击的应用在列表中的索引
    ///   - appId: 点击应用的id
    func updateLikedState(index: Int, appId: Int) {
        Task {
            await sendUpdateLikedState(index: index, appId: appId)
        }
    }
    
    /// 发送"喜欢"状态更新
    /// - Parameters:
    ///   - index: 点击的应用在列表中的索引
    ///   - appId: 点击应用的id
    @MainActor
    func sendUpdateLikedState(index: Int, appId: Int) async {
        
        guard index < listData.count, appId == listData[index].appId else {
            return
        }
        
        // 点击时立即更新数据源中的状态
        listData[index].liked.toggle()
        
        // 将数据提交到服务器，如果服务器返回失败，则将对应数据的状态恢复并弹窗提示（暂时随便写的接口）
//        let url = "https://itunes.apple.com/search"
//        let param = try? JSONEncoder().encode(listData[index].appId)
//        let reuslt = await NetWorkManager.shared.request(url: url, type: Bool.self, method: .POST, params: param)
//        switch reuslt {
//        case .failure(msg: let msg):
//            listData[index].liked.toggle()
//            toastMsg = msg
//            showToast = true
//        case .success(_, msg: _): break
//        }
    }
}
