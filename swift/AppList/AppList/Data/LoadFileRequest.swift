//
//  LoadFileRequest.swift
//  AppList
//
//  Created by 大洋 on 2022/8/25.
//

import SwiftUI
import Combine

struct LoadFileRequest {
    let index: Int
    
    var publisher: AnyPublisher<AppModel, LoadError> {
        Future { promise in
            DispatchQueue.global().asyncAfter(deadline: .now() + 1.5) {
                Task {
                    do {
                        let model: AppModel =  try await AppListFile.loadJSON(filename: "appList-\(index).json")
                        promise(.success(model))
                    } catch let error {
                        promise(.failure(error as! LoadError))
                    }
                }
            }
        }
        .receive(on: DispatchQueue.main)
        .eraseToAnyPublisher()
    }
}
