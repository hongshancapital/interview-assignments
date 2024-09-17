//
//  PullUpDownloadView.swift
//  FavoriteApps
//
//  Created by 刘明星 on 2022/5/6.
//

import SwiftUI

struct PullUpDownloadView: View {
    var isHaveMoreData: Bool
    var body: some View {
        VStack{
            if isHaveMoreData {
                HStack {
                    ProgressView()
                        .padding(.trailing, 5)
                    Text("Loading...")
                        .foregroundColor(.gray)
                }
            } else {
                Text("No more data.")
            }
        }

    }
}

struct PullUpDownloadView_Previews: PreviewProvider {
    static var previews: some View {
        PullUpDownloadView(isHaveMoreData: false)
    }
}
