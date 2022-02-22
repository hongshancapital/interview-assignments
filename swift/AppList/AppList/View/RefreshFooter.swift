//
//  RefreshFooter.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import SwiftUI

enum RefreshFooterState {
    case idle
    case loading(message: String?)
    case noMoreData(message: String?)
    case failure(message: String?)
}

struct RefreshFooter: View {
    
    var state: RefreshFooterState = .idle
    
    var body: some View {
        HStack(spacing: 10) {
            Spacer()
            switch state {
            case .idle:
                AnyView(Color.green)
            case .loading(let message):
                ProgressView()
                
                if message != nil {
                    Text(message!)
                        .foregroundColor(Color.gray)
                }
            case .noMoreData(let message):
                if message != nil {
                    Text(message!)
                        .foregroundColor(Color.gray)
                }else {
                    AnyView(Color.green)
                }
            case .failure(let message):
                if message != nil {
                    Text(message!)
                        .foregroundColor(Color.gray)
                }else {
                    AnyView(Color.green)
                }
            }
            
            Spacer()
        }
        .background(.clear)
    }
}
