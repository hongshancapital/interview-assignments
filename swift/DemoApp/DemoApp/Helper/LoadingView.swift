//
//  LoadingView.swift
//  DemoApp
//
//  Created by Gao on 2022/7/11.
//

import SwiftUI


struct LoadingView : View {
    var loadingState: LoadingState
    var body: some View {
        HStack(alignment: .center, spacing: 5.0) {
            Spacer()
            if loadingState == .LoadMore {
                ProgressView()
                Text("Loading...")
                    .foregroundColor(.gray)
            }else{
                Text("No more data.")
                    .foregroundColor(.gray)
            }
            Spacer()
        }
        .padding()
        .background(Color.clear)
    }
}

struct LoadingView_Previews: PreviewProvider{
    static var previews: some View {
        Group {
            LoadingView(loadingState: .LoadMore)
            LoadingView(loadingState: .NoMoreData)
        }.previewLayout(.fixed(width: 300, height: 70))
    }
}

