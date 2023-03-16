//
//  ListRefreshFooter.swift
//  AppListDemo
//
//  Created by Kimi on 2023/3/8.
//

import SwiftUI

struct ListRefreshFooter: View {
    var hasMoreData: Bool
    var isLoadingFaild: Bool
    
    //fix ProgressView show only once
    @State private var progressId: Int = 0
    
    var body: some View {
        HStack(alignment: .center) {
            Spacer()
            
            if isLoadingFaild {
                Text("Load failed.").foregroundColor(.secondary)
            } else {
                if hasMoreData {
                    ProgressView()
                        .progressViewStyle(.circular)
                        .frame(width: 20,height: 20)
                        .id("\(progressId)")
                        .task {
                            progressId = progressId &+ 1
                        }
                    
                    Text("Loading...").foregroundColor(.secondary)
                } else {
                    Text("No more data.").foregroundColor(.secondary)
                }
            }
            
            Spacer()
        }
    }
}

struct ListRefreshFooter_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            Divider()
            ListRefreshFooter(hasMoreData: true ,isLoadingFaild: false)
            Divider()
            ListRefreshFooter(hasMoreData: false,isLoadingFaild: false)
            Divider()
            ListRefreshFooter(hasMoreData: true,isLoadingFaild: true)
            Divider()
        }
    }
}
