//
//  RefreshFooterView.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/6.
//

import SwiftUI

struct RefreshFooterView: View {

    let state: State

    // MARK: - system
    var body: some View {
        HStack(spacing: 10) {
            switch state {
            case .idel:
                Text("Pull up to load more")
            case .loading:
                // progressview only show once when loading
                ProgressView().id(UUID())
                Text("Loading...")
            case .noMoreData:
                Text("No more data.")
            }
        }
        .foregroundColor(.gray)
        .frame(height: 39)
        .frame(maxWidth: .infinity)
    }


    // MARK: - State
    enum State {
        case idel, loading, noMoreData
    }
}

struct RefreshFooterView_Previews: PreviewProvider {
    static var previews: some View {
        RefreshFooterView(state: .idel)
    }
}
