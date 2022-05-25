//
//  ListViewModel.swift
//  SwiftUIDemo
//
//  Created by banban on 2022/5/19.
//

import Foundation


enum ListViewLoadDataStatus {
    case normal
    case loading
    case failed
    case noMoreData
}

/// List 列表的ViewModel,每次获取最大长度为
class ListViewModel : ObservableObject {

    /// 初始化加载数据
    @Published var isInitLoading = true;
    
    /// 用来监听数组数据
    @Published var itemModels : [ListItemModel] = []
    
    /// 监听数据加载状态
    @Published var loadDataStatus : ListViewLoadDataStatus = .normal
    
    /// 分页数据索引
    private var currentOffset : Int = 0
    
    func onRefresh() {
        loadDataStatus = .loading
        ApiManager.shared.asyncLoadSearchDatas { [weak self] res in
            self?.isInitLoading = false
            if (res.success) {
                self?.itemModels.removeAll()
                self?.itemModels.append(contentsOf: res.items)
                self?.currentOffset = res.items.count;
            } else {
                self?.loadDataStatus = .failed
            }
        };
    }
    
    func loadMoreData() {
        if (loadDataStatus == .noMoreData) {
            return
        }
        
        /// 为展示效果，这里增加了一个限制处理
        if (itemModels.count >= 30) {
            loadDataStatus = .noMoreData
            return
        }
        
        loadDataStatus = .loading
        
        /// 因网络请求速度太快，为了增加演示效果，额外做了一个延时1秒执行请求的操作
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.0, execute: { [weak self] in
            /// 请求更多数据
            ApiManager.shared.asyncLoadSearchDatas(offset: self?.currentOffset ?? 0) {[weak self] res in
                if (res.success) {
                    self?.itemModels.append(contentsOf: res.items)
                    self?.currentOffset += res.items.count;
                } else {
                    self?.loadDataStatus = .failed;
                }
            }
        })
    }
    
    /// 重试加载初始化数据
    func retryLoadInitData() {
        isInitLoading = true;
        onRefresh()
    }
    
    /// 点击/取消点亮喜欢按钮
    func onClickItemLikeButton(_ clickModel : ListItemModel) {
        
        if let index = itemModels.firstIndex(where: { element in
            return element.id == clickModel.id
        }) {
            itemModels[index].isLike = !clickModel.isLike;
        }
    }
    
}
