//
//  ResourceViewModel.swift
//  App
//
//  Created by xiongjin on 2022/6/29.
//

import Foundation
import Combine

class ResourceViewModel: ObservableObject {

    @Published var resourceList: [ResponseResult] = []
    
    var cancellationToken: AnyCancellable?

    init() {
        getResourceList()
    }
}

extension ResourceViewModel {

    func getResourceList() {
        cancellationToken = ApiObject.request(.resourceList)
            .mapError({ (error) -> Error in
                print("error: ", error)
                return error
            })
            .sink(receiveCompletion: { _ in },
                  receiveValue: {
                    self.resourceList = $0.results
            })
    }
}
