//
//  LoadMoreFooterView.swift
//  SwiftUIHW
//
//  Created by 施治昂 on 4/15/22.
//

import SwiftUI

enum LoadMoreViewState {
    case `default`
    case noMoreData
}

struct ProgressIndicatorView: View {
    var showText: Bool
    var state: LoadMoreViewState
    var body: some View {
        HStack(spacing: 0) {
            if state == .default {
                ProgressView()
                    .frame(width: 50, height: 50)
            }
            Text(showText ? state == .default ? "Loading..." : "No more data" : "")
        }
        .foregroundColor(Color(uiColor: UIColor.lightGray))
    }
}

struct ProgressIndicatorView_Previews: PreviewProvider {
    static var previews: some View {
        ProgressIndicatorView(showText: true, state: LoadMoreViewState.default)
    }
}
