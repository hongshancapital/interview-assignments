//
//  HomeViewModel.swift
//  SwiftDeveloper
//
//  Created by zhe wu on 2022/10/18.
//

import Foundation

class HomeViewModel: ObservableObject {
    @Published var appsModel = AppsModel(resultCount: 0, results: [])
    @Published var loading = false
    @Published var firstLoading = false
    @Published var errorMesage: String?
    
//    init() {
//        fetchNext()
//    }

    func fetchFirstTime() {
        firstLoading = true
        fetchData(isRerefeshing: true)
    }
    
//    func fetchNext() {
//        loading = true
//
//        NetworkApi.shared.fetchApps(offset: appsModel.results.count,
//                                    limit: 20) { [weak self] appsModelFetched in
//            self?.appsModel.resultCount = appsModelFetched.resultCount
//            self?.appsModel.results.append(contentsOf: appsModelFetched.results)
//
//            self?.loading = false
//            self?.errorMesage = nil
//        } onError: { [weak self] errorMessage in
//            self?.loading = false
//            self?.errorMesage = errorMessage
//        }
//    }
//
    func fetchData(isRerefeshing: Bool = false) {
        loading = true
        
        NetworkApi.shared.fetchApps(offset: isRerefeshing ? 0 : appsModel.results.count,
                                    limit: 20) { [weak self] appsModelFetched in
            self?.appsModel.resultCount = appsModelFetched.resultCount
            if isRerefeshing {
                self?.appsModel.results = appsModelFetched.results
            } else {
                self?.appsModel.results.append(contentsOf: appsModelFetched.results)
            }
            
            self?.loading = false
            self?.errorMesage = nil
            self?.firstLoading = false
        } onError: { [weak self] errorMessage in
            self?.loading = false
            self?.firstLoading = false
            self?.errorMesage = errorMessage
        }
    }
}
