//
//  HSViewModel.swift
//  HongShanDemo
//
//  Created by 木木 on 2022/5/15.
//

import Foundation
import Combine

class HSViewModel: ObservableObject {
    
    @Published var dataArr: [HSModel]?
    
    let networkObj = HSNetworkObject.shared
    
    var cancellable = Set<AnyCancellable>()
    
    init() {
        networkObj.getData().sink { completion in
            print(completion)
        } receiveValue: { [weak self] data in
            self?.dataArr = data
        }
        .store(in: &cancellable)
    }
}
