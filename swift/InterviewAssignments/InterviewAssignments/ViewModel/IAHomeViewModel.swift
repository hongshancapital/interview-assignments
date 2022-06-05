//
//  IAHomeViewModel.swift
//  InterviewAssignments
//
//  Created by ZhuChaoJun on 2022/6/2.
//

import SwiftUI

final class IAHomeViewModel: ObservableObject {


    @Published var cellViewModels = [IAHomeCellViewModel]()
    // first enter page
    @Published var isLoading: Bool
    // footer no data
    @Published var noMoreData: Bool

    //
    private var currentPage = 1
    private var pageCount = 20

    //
    //
    init() {
        isLoading = true
        noMoreData = false
    }

    func initDataSources() async {
        await self.generalDataSource(isRefresh: true)
        isLoading = false
    }

    func refresh() async {
        await self.generalDataSource(isRefresh: true)
        noMoreData = false
    }

    func loadMore() async {
        await self.generalDataSource(isRefresh: false)
    }

    @MainActor
    private func generalDataSource(isRefresh: Bool = false) async {
        if isRefresh {
            self.currentPage = 1
        } else {
            self.currentPage += 1
        }
        try? await Task.sleep(nanoseconds: 3 * 1_000_000_000)
        let bundlePath = Bundle.main.url(forResource: "data.json", withExtension: nil)
        guard let bundlePath = bundlePath,
              let data = try? Data(contentsOf: bundlePath) else {
            return
        }
        let decoder = JSONDecoder()
        guard let appInfoModels = try? decoder.decode(IAAppInfoModels.self, from: data) else {
            return
        }

        var cellViewModels: ArraySlice<IAAppInfoModel>
        let startIndex = (self.currentPage - 1) * self.pageCount
        let endIndex = self.currentPage * self.pageCount
        if endIndex >= appInfoModels.resultCount {
            noMoreData = true
            cellViewModels = appInfoModels.results[startIndex ..< appInfoModels.resultCount]
        } else {
            cellViewModels = appInfoModels.results[startIndex ..< endIndex]
        }

        if isRefresh {
            self.cellViewModels = cellViewModels.map { IAHomeCellViewModel($0) }
        } else {
            self.cellViewModels.append(contentsOf: cellViewModels.map { IAHomeCellViewModel($0) })
        }
    }
}
