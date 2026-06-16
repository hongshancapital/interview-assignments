//
//  XXRefreshFooterView.swift
//  RedwoodTestProject
//
//  Created by 徐栋 on 2023/2/8.
//

import SwiftUI
@available(iOS 13.0, macOS 10.15, *)
struct XXRefreshFooterView: View {
    @Binding var noMoreData: Bool
    var footHandler = XXRefreshHandler()
    
    public func getMoreAction(action: @escaping () async -> Void) -> XXRefreshFooterView {
        self.footHandler.moreAction = action
        return self
    }
    
    var body: some View {
        return HStack(alignment: .center) {
            Spacer()
            if noMoreData {
                Text("No More Data")
            } else {
                XXActivityIndicator(isAnimating: .constant(true), style: .medium)
                Text("Loading..")
            }
            Spacer()
        }
        .background(Color.clear)
        .onAppear {
            Task {
                // 模拟网络请求
                try? await Task.sleep(nanoseconds: 3_500_000_000)
                await footHandler.moreAction?()
            }
        }
        .listRowSeparator(.hidden)

    }
    
}

struct XXRefreshFooterView_Previews: PreviewProvider {
    static var previews: some View {
        XXRefreshFooterView(noMoreData: .constant(false))
    }
}
