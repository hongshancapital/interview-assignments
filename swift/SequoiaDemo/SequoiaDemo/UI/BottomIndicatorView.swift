//
//  BottomIndicatorView.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/5/7.
//

import Foundation
import SwiftUI

/*
 底部视图，用于刷新下一页和展示"no more data"
 */

struct BottomIndicatorView: View {
    @EnvironmentObject var store: Store
    
    var body: some View {
        switch store.appState.appDataLoadingState {
        case .initial:
            ProgressView()
        case .refreshing:
            ProgressView()
        case .loadingNextPage:
            HStack {
                Spacer()
                ProgressView()
                    .progressViewStyle(.circular)
                    .padding(.trailing, 2)
                Text("Loading...")
                    .foregroundColor(.gray)
                Spacer()
            }
            
        case .loadFinish:
            Text("")
        case .endOfData:
            HStack {
                Spacer()
                Text("No more data.")
                    .foregroundColor(.gray)
                Spacer()
            }
        }
        
    }
}
