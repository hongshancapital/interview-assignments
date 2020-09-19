//
//  SearchViewModel.swift
//  SearchFlow
//
//  Created by evan on 2020/9/19.
//  Copyright © 2020 evan. All rights reserved.
//

import Foundation
import Combine

final class SearchViewModel : ObservableObject {
    @Published var text: String = ""
    @Published var isSearching: Bool = false
    @Published var series: [Serie] = []
    @Published var isRefreshing: Bool = false

    private var cancellable: AnyCancellable?
    private var currentPage: Int = 0

    init() {
        cancellable = $text.debounce(for: 0.5, scheduler: DispatchQueue.main).sink { [weak self] newValue in
            guard !newValue.isEmpty else { return }
            self?.search()
        }
    }

    deinit { cancellable?.cancel() }

    var isEmptyResult: Bool { isSearching && series.isEmpty && !text.isEmpty }

    // MARK: Intents
    func cancel() {
        text = ""
        series = []
        isSearching = false
        currentPage = 0
    }

    func search() {
        isSearching = true
        Server.fetchBrand { result in
            // Mock network request
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                switch result {
                case .success(let value):
                    self.currentPage += 1
                    let series = value.brands.first { $0.brand.lowercased() == self.text.lowercased() }?.series ?? []
                    // Mock page data
                    self.series = (0..<self.currentPage).reduce(series) { result, _ -> [Serie] in
                        return result + series
                    }
                    self.isRefreshing = false
                case .failure(let error):
                    self.isRefreshing = false
                    print(error.message)
                }
            }
        }
    }
}
