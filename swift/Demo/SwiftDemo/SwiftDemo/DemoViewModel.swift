//
//  DemoViewModel.swift
//  SwiftDemo
//
//  Created by 彭军涛 on 2022/7/10.
//

import Foundation
import Combine

@MainActor
class DemoViewModel: ObservableObject {
    
    @Published var model = DemoModel()
    @Published var listData = [DemoModel.ScreenshotUrls]()
    @Published var dic = [String: String]()
    @Published var currentEndIndex: Int = 0
    @Published var isLoadingMore: Bool = true
    @Published var showErrorMessage: String?
    @Published var showError: Bool = false
    var sub = Set<AnyCancellable>()

    init() {
        bindLogic()
        Task {
            await requestNetwork()
        }
    }

    func bindLogic() {
        self.$currentEndIndex
            .sink { [weak self] index in
                guard let self = self else { return }
                self.listData = Array((self.model.results ?? []).prefix(index))
            }
            .store(in: &sub)
    }

    func requestNetwork() async {
        try? await Task.sleep(nanoseconds: 3_000_000_000)
        requestData()
    }

    // 数据刷新
    func refresh() async {
        try? await Task.sleep(nanoseconds: 3_000_000_000)
        requestData()
        self.isLoadingMore = true
    }

    // 数据请求
    func requestData() {
        if let fileLoaction = Bundle.main.url(forResource: "Demo", withExtension: "json") {
            do {
                let data = try Data(contentsOf: fileLoaction)
                let jsonDeconder = JSONDecoder()
                let model = try jsonDeconder.decode(DemoModel.self, from: data)
                self.model = model
                self.currentEndIndex = 24
                self.showError = false
            } catch {
                self.showErrorMessage = error.localizedDescription
                self.showError = true
            }
        }

    }

    // 加载更多
    func loadingMore() async {
        if self.currentEndIndex == model.resultCount {
            self.isLoadingMore = false
        } else {
            try? await Task.sleep(nanoseconds: 2_000_000_000)
            self.currentEndIndex = model.resultCount ?? 0
            self.isLoadingMore = true
        }
    }

    func setListData(bundleId: String) {
        if let _ = dic[bundleId] {
            dic.removeValue(forKey: bundleId)
        } else {
            dic[bundleId] = bundleId
        }
    }
}
