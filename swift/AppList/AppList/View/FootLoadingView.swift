//
//  FootLoadingView.swift
//  AppList
//
//  Created by mengyun on 2022/3/12.
//

import SwiftUI

enum LoadingState: Int {
    case PreLoading = 0
    case Loading = 1
    case LoadMore = 2
    case LoadComplete = 3
}

struct FootLoadingView: View {
    
    var loadingState: LoadingState
    
    var body: some View {
        HStack(spacing: 5.0) {
            Spacer()
            if loadingState == .LoadMore {
                ProgressView()
                Text("Loading...")
                    .foregroundColor(.gray)
            } else {
                Text("No more data.")
                    .foregroundColor(.gray)
            }
            Spacer()
        }
        .padding()
        .background(Color.clear)
    }
}

struct FootLoadingView_Previews: PreviewProvider {
    static var previews: some View {
        FootLoadingView(loadingState: .Loading)
        FootLoadingView(loadingState: .LoadComplete)
    }
}
