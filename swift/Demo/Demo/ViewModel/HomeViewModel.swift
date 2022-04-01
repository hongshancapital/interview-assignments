//
//  HomeViewModel.swift
//  Demo
//
//  Created by 杨立鹏 on 2022/3/29.
//

import SwiftUI

enum NetworkError: Error {
    case urlIncorrect(String)
    case badRequest(stateCode: Int?)
    case jsonDecodeFailed
}

final class HomeViewModel: ObservableObject {
    /// 每页条目数量
    let pageSize = 20

    /// 当前分页页码
    private(set) var pageNumber = 0

    /// 数据数组
    @Published private(set) var items: [Item] = []

    /// 数组总条目数
    @Published private(set) var resultCount: Int = 0

    /// 已点击喜欢的条目的数组
    @Published private(set) var likedItems: [Item] = []

    /// 模拟分页请求
    @MainActor
    func getData(isRefreshData: Bool = false) async {
        // 如果是下拉刷新 重置页码
        if isRefreshData {
            pageNumber = 1
        } else {
            // 如果当前页无数据（页码超过最大值） 不做任何操作
            if resultCount < pageNumber * pageSize {
                return
            }
            pageNumber += 1
        }
        // 请求数据 模拟分页
        if let result = try? await requestData() {
            resultCount = result.resultCount

            let startIndex = (pageNumber - 1) * pageSize
            var endIndex = startIndex + pageSize
            endIndex = endIndex >= resultCount ? resultCount : endIndex

            let subArray = result.results[startIndex ..< endIndex]

            if pageNumber == 1 {
                items = Array(subArray)
            } else {
                items.append(contentsOf: subArray)
            }
        } else {
            // 请求失败 重置页码
            pageNumber -= 1
        }
    }

    /// 请求网络数据
    /// - Returns: 返回的数据

    func requestData() async throws -> RootData {
        guard let url = URL(string: APIConstants.itemList) else {
            throw NetworkError.urlIncorrect("URL Incorrect: \(APIConstants.itemList)")
        }
        let request = URLRequest(url: url)

        let (data, res) = try await URLSession.shared.data(for: request)

        guard (res as? HTTPURLResponse)?.statusCode == 200 else {
            throw NetworkError.badRequest(stateCode: (res as? HTTPURLResponse)?.statusCode)
        }

        guard let rootData = try? JSONDecoder().decode(RootData.self, from: data) else {
            throw NetworkError.jsonDecodeFailed
        }

        return rootData
    }

    /// 反转爱心是否点亮的状态
    /// - Parameter item: 点击爱心所对应的条目
    func toggleLikeState(item: Item) {
        if let index = likedItems.firstIndex(of: item) {
            likedItems.remove(at: index)
        } else {
            likedItems.append(item)
        }
    }
}
