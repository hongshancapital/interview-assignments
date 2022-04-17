//
//  AppListView.swift
//  SwiftUIHW
//
//  Created by 施治昂 on 4/16/22.
//

import SwiftUI

struct AppListView: View {
    var apps: [AppItem]
    var refresh: (() -> Void)
    var loadMore: (() -> Void)
    var noMoreData: Bool
    
    var body: some View {
        if apps.isEmpty {
            ProgressIndicatorView(showText: false, state: .default)
        }
        else {
            List {
                ForEach(apps, id: \.trackId) { app in
                    AppItemView(item: app)
                        .padding(10)
                        .background(Color.white)
                        .cornerRadius(10)
                        .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 10, trailing: 0))
                }
                HStack {
                    Spacer()
                    ProgressIndicatorView(showText: true, state: noMoreData ? .noMoreData : .default)
                    Spacer()
                }
                .listRowBackground(Color.clear)
                .onAppear {
                    Task {
                        self.loadMore()
                    }
                }
            }
            .padding(0)
            .refreshable {
                self.refresh()
            }
        }
    }
}
