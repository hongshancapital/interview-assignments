//
//  EmptyView.swift
//  HongShanDemo
//
//  Created by 林纪涛 on 2023/4/14.
//

import Foundation
import SwiftUI

public protocol EmptyViewDelegate{
    func retryAction(_ emptyView: EmptyView) -> Void;
}

public struct EmptyView: View{
    enum ErrorState {
        case none  // 无错误
        case netWorkError // 请求失败
        case noData // 接口无数据
    }
    let delegate: EmptyViewDelegate?
    let errorState: ErrorState
    private var message: String = ""
    
    init(delegate: EmptyViewDelegate, errorState: ErrorState) {
        self.delegate = delegate
        self.errorState = errorState
        switch errorState {
        case .noData:
            message = "No Data"
        default:
            message = "Network Error"
        }
    }

    public var body: some View{
        VStack(spacing: 10){
            Text(message)
                .font(.title)
            if(errorState == .netWorkError){
                Button("重试") {
                    delegate?.retryAction(self)
                }
            }
        }
       
    }
}
