//

//
//  AppDataRowViewModel.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/3.
//  
//
    

import Foundation
import Combine

class AppDataRowViewModel: ObservableObject, Identifiable {
    // 数据
    @Published var appData: AppData
    // 取消令牌
    var cancellationToken: AnyCancellable?
    
    init(appData: AppData) {
        self.appData = appData
    }
    
    // 模拟请求 id: 数据id，isLike: 是否喜欢，completion: 完成回调
    func changeIsLike(completion: (() -> ())? = nil) {
        let isLike = !appData.isLike
        cancellationToken = AppDataAPI.requestUpdateLike(id: appData.id, isLike: isLike).sink(receiveCompletion: { _ in }) { _ in
            self.appData.isLike = isLike
            completion?()
        }
    }
}
