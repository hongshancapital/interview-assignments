//
//  AppViewModel.swift
//  ItuneApps
//
//  Created by daicanglan on 2023/3/27.
//

import Combine
import Foundation
import SwiftUI

class AppViewModel: ObservableObject {
    @Published var error: AppError?
    @Published var apps: AppModel?
    @AppStorage("favoriteIds") var likeAppIds: Set<Int> = []

    var results: [AppItem] {
        apps?.results ?? []
    }

    var isLoadingApps = false
    var isNoMore = false // false 有更多的数据 true 没有更多的数据

    var start: Int = 10 // 初始值
    var increment: Int = 10 // 每次加载更多limit增加数
    var limitCount: Int = 100
}

// MARK: public functions

extension AppViewModel {
    /// 第一次加载数据和下拉刷新
    func loadApps() {
        guard !isLoadingApps else { return }
        loadRequest(queryLimit: start)
    }

    /// 加载更多, [loadRequest(queryLimit: start + increment)](x-source-tag://请求数据)
    func loadMore() {
        if isNoMore { return }
        loadRequest(queryLimit: start + increment) { [weak self] in
            guard let self = self else { return }
            self.start = self.start + self.increment
        }
    }

    /// 添加/删除喜欢App
    func toggleLike(trackId: Int) {
        if likeAppIds.contains(trackId) {
            likeAppIds.remove(trackId)
        } else {
            likeAppIds.insert(trackId)
        }
    }
}

// MARK: private functions

extension AppViewModel {
    private func handlerError(_ error: AppError) {
        self.error = error
    }

    /// - Tag: 请求数据
    /// - Parameters:
    ///   - queryLimit: 请求limit参数
    ///   - successCallback: 请求成功的回调
    private func loadRequest(queryLimit: Int,
                             successCallback: (() -> Void)? = nil) {
        let token = SubscriptionToken()
        LoadAppListRequest(queryLimit: queryLimit).publisher
            .sink { [weak self] complete in
                guard let self = self else { return }
                if case let .failure(error) = complete {
                    print("[Error]: \(error)")
                    self.handlerError(AppError.networkFailed(error))
                }
                token.unseal()
            } receiveValue: { [weak self] value in
                guard let self = self else { return }
                self.isLoadingApps = false
                self.apps = value
                successCallback?()
                if self.apps?.results.count ?? -100 >= self.limitCount {
                    self.isNoMore = true
                }
            }
            .seal(in: token)
    }
}
